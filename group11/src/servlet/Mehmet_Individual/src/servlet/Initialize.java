package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import others.DatabaseConnection;
import others.Item;

/**
 * Servlet implementation class Initialize
 * gets data from wikidata, parses and adds to database.
 * I benefitted from "http://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm"
 */
@WebServlet("/Initialize")
public class Initialize extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String url = "https://query.wikidata.org/sparql?query=SELECT%20DISTINCT%20%3Fitem%20%3FitemLabel%20%3FawardLabel%20%3Ftime%0AWHERE%20%7B%0A%20%20%20%20%3Fitem%20wdt%3AP106%2Fwdt%3AP279*%20wd%3AQ3455803%20.%20%23%20Items%20with%20the%20Occupation(P106)%20of%20Director(Q3455803)%20or%20a%20subclass(P279)%0A%20%20%20%20%3Fitem%20p%3AP166%20%3FawardStat%20.%20%20%20%20%20%20%20%20%20%20%20%20%20%20%23%20...%20with%20an%20awarded(P166)%20statement%0A%20%20%20%20%3FawardStat%20ps%3AP166%20wd%3AQ103360%20.%20%20%20%20%20%20%23%20...%20that%20has%20the%20value%20Academy%20Award%20for%20Best%20Director(Q103360)%0A%20%20%20%20SERVICE%20wikibase%3Alabel%20%7B%20%20%20%20%20%20%20%20%20%20%20%20%23%20...%20include%20the%20labels%0A%20%20%20%20%20%20%20%20bd%3AserviceParam%20wikibase%3Alanguage%20%22en%22%20.%0A%20%20%20%20%7D%0A%20%20%20%20%3FawardStat%20pq%3AP805%20%3Faward%20.%20%23%20Get%20the%20award%20(which%20is%20%22subject%20of%22%20XXth%20Academy%20Awards)%0A%20%20%09%3Faward%20rdfs%3Alabel%20%3FawardLabel%20filter%20(lang(%3FawardLabel)%20%3D%20%22en%22)%20.%20%23%20...%20and%20its%20label%0A%20%20%09%3Faward%20wdt%3AP585%20%3Ftime%20.%20%23%20the%20%22point%20of%20time%22%20of%20the%20Academy%20Award%0A%7D%0AORDER%20BY%20DESC(%3Ftime)";
	private Boolean b = false;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		
		for(int i=2;i<89;i++)
			if(request.getParameter("checkbox"+i) != null){
				
				System.out.println(request.getParameter("checkbox"+i) + i);
				DatabaseConnection.update(String.valueOf(i));
				b = true;
			}
		out.println("<html><body>");
			
		if(b) { out.println("<a href=\"Saved\"> click to see your saved entries</a></body></html>");return;}
		
		DatabaseConnection.deleteDatabase();
		try {
			String xml = getHtml();
			//For parsing operation.
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			Document doc = db.parse(is);
			doc.getDocumentElement().normalize();
			
			NodeList results = doc.getElementsByTagName("result");
			out.println("Click checkboxes to save them to your saved entries");
			out.println("<form action=\"Initialize\" method=\"get\">");
			out.print(results.getLength() + "<br/>");
			// For each result to add
			for(int i=0;i<results.getLength();i++){
				Element result = (Element) results.item(i);
				NodeList bindings = result.getElementsByTagName("binding");
				
				// item -> a row that will be add
				Item item = new Item(bindings.item(2).getTextContent().trim(),
						bindings.item(1).getTextContent().trim(),false);
				// a checkbox to save for user.
				out.print("<input type=\"checkbox\" name = \"checkbox"+ (88-i) + "\"value = \"true\" >"
						+ item.toString() + "<br/>");
				DatabaseConnection.addItem(item);
			}
			out.println("<br/><input type=\"submit\" value=\"Save\">");
			out.println("</form>");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("catchchchc");
			e.printStackTrace();
		}
		out.println("<br/><br/><a href=\"Mehmet\"> Click to turn back </a> </body> </html>");
	}
	/**
	 * 
	 * @return a string which is in xml format that represents data to add database.
	 * @throws Exception
	 * I benefitted from "https://docs.oracle.com/javase/tutorial/networking/urls/readingURL.html"
	 */
	private String getHtml() throws Exception {
        URL sparql = new URL(url);
        BufferedReader in = new BufferedReader(
        new InputStreamReader(sparql.openStream()));

        String result = "";
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            result += inputLine + "\n";
        in.close();
		return result;
	}
	
}
