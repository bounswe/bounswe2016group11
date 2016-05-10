Name of the Project: Roman Emperors and their Year of Ascension to the Throne
Author of the Project: Melih Barsbey
Student No.: 2015800003
Course: CMPE 352 - Spring 2016
Group: Group 11

The project allows the user to enter a year and see Roman emperors who became the ruler of the Roman Empire close to that year. In order to do this, data from the semantic database Wikidata has been used. The application uses the query below to obtain the necessary information:

SELECT ?emperorLabel ?dateLabel
WHERE {
	?emperor wdt:P31 wd:Q5.
	?emperor p:P39 ?position_held_statement.
	?position_held_statement ps:P39 wd:Q842606.
	?position_held_statement pq:P580 ?date.

	SERVICE wikibase:label {
		bd:serviceParam wikibase:language "en".
	}
}

ORDER BY ?date

The query was conducted through HTML GET method. The server was asked for a JSON file, which included the array of results that the query asked for. Then, the application parses the input and stores the query in a relational database. When the user makes a query with a year, the algorithm calculates the 10 emperors who are closest to that date in terms of the year they assumed the Roman Empire's throne. For example, assuming that the user entered "250" as query, the database returns 