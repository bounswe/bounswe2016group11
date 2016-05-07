
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class Ozgur_deneme
 */
@WebServlet("/Ozgur")
public class Ozgur extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Ozgur() {
        super();
        // TODO Auto-generated constructor stub
    
        System.out.println();
    
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter theo = response.getWriter();
		theo.println("My name is Özgür.");
		JSONArray theaArr = Ozgur_HtmlToJson.getJSONResponseForQuery(Ozgur_HtmlToJson.basicQuery);
		//String thefour = theaArr.getJSONObject(4).getJSONObject("countryLabel").getString("value");
		
		theo.append(indexPage());
		theo.append(theaArr.toString());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	protected String indexPage(){
		String result = " <form>\nEnter to search:<br>\n"+
				"<input type=\"text\" name=\"firstname\"><br>\n"+
				"<input type=\"submit\" name=\"submit <br>\">"+
				"</form> ";
		return result;
	}

}
