package net.codejva;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import com.hp.hpl.jena.rdf.model.Model;
import java.sql.*;

/**
 * Servlet implementation class Melih
 */@WebServlet("/Melih")


public class Melih extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Melih() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Integer menu = 0;
		PrintWriter out = response.getWriter();
		String my_query = request.getParameter("my_query");
		out.println("<html>");
		out.println("<body>");
		if (my_query == null){out.println("<form action='Melih' method='get'> ASdasdffDAFFPasflease enter a query:<br><input type='text' name='my_query'><br><br><input type='submit' value='Submit'></form>");}
		else{out.println("<p>" + my_query + "</p>");}
		out.println("<br>");
		out.println("<form action='https://query.wikidata.org/sparql?query=SELECT ?countryLabel ?capitalLabel WHERE {?country wdt:P31 wd:Q3624078. ?capital wdt:P1376 ?country. SERVICE wikibase:label{bd:serviceParam wikibase:language 'en'. }}' method='get'> Click below to send your query:<input type='submit' value='Submit'></form>");
		out.println("<br>");
		out.println("<form action='Melih' method='get'> Please click here to initialize the database: <input type='submit' value='Click'></form>");
		out.println("</body>");
		out.println("</html>");
	}	
}
