My wikidata query is:
SELECT ?populationLabel ?countryLabel 
WHERE{ 
  ?country wdt:P1082 ?population. 
  ?country wdt:P31 wd:Q3624078. 
  SERVICE wikibase:label {
    bd:serviceParam wikibase:language "en" . 
  } 
}

Example Results:
35702707	Canada
127110047	Japan
5109056	Norway
4588252	Ireland
9893899	Hungary
46617825	Spain

Mustafa.java is the main page.
In Mustafa.java there are 3 options that user can choose. Initialize data, make query and show saved entries.

Mustafa_Init.java is the initialization page.
It drops database if exists, then creates new database and get wikidata.

Mustafa_Input.java is the query and saving page.
Users enter their entry then it shows related results. If users check some checkbox, those entries are saved.

Mustafa_Saved.java is the page that shows entries saved by user.
It shows saved entries.

Mustafa_Data.java, Mustafa_DatabaseConnection.java, Mustafa_User.java, Mustafa_Wikidata.java are classes that we use common.