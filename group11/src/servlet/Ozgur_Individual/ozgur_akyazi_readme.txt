To be able to use the project, one should first run Ozgur_DB.createDatabase()    and Ozgur_DB.createTable()  functions respectively, just once.
It initializes the database by adding new db and table in it.

Example SPARQL query is 


SELECT ?itemLabel WHERE { 
	?item wdt:P31 wd:Q11424. 
	?actor wdt:P106 wd:Q33999 . 
	?item wdt:P161 ?actor. 
	?actor rdfs:label  "Christian Bale"@en. 
	SERVICE wikibase:label { bd:serviceParam wikibase:language "en" }
}


Q11424 represents the film item. and the ?item is an instance of(P31) film item. And the wdt:161 meaning the cast member.
With the query the ?actor variable is a cast member of the film ?item and, the actor's label is Christian Bale.
So it finds the films that the Christian Bale has played in.


Other example query is 

SELECT ?itemLabel WHERE { 
	?film wdt:P31 wd:Q11424. 
	?film rdfs:label  "The Machine"@en. 
	?film wdt:P161 ?item. 
	?item wdt:P106 wd:Q33999 .  
	SERVICE wikibase:label { bd:serviceParam wikibase:language "en" } 
}

This is just like the previous example. This time the label of film is given and the actors who are cast members of the specified film
is returned as result in ?item variable.


For the last query this is the json response,


{
  "head" : {
    "vars" : [ "itemLabel" ]
  },
  "results" : {
    "bindings" : [ {
      "itemLabel" : {
        "xml:lang" : "en",
        "type" : "literal",
        "value" : "Toby Stephens"
      }
    }, {
      "itemLabel" : {
        "xml:lang" : "en",
        "type" : "literal",
        "value" : "Natalia Wörner"
      }
    }, {
      "itemLabel" : {
        "xml:lang" : "en",
        "type" : "literal",
        "value" : "Gérard Depardieu"
      }
    }, {
      "itemLabel" : {
        "xml:lang" : "en",
        "type" : "literal",
        "value" : "Nathalie Baye"
      }
    }, {
      "itemLabel" : {
        "xml:lang" : "en",
        "type" : "literal",
        "value" : "Siwan Morris"
      }
    }, {
      "itemLabel" : {
        "xml:lang" : "en",
        "type" : "literal",
        "value" : "Julie Depardieu"
      }
    }, {
      "itemLabel" : {
        "xml:lang" : "en",
        "type" : "literal",
        "value" : "Claude Berri"
      }
    }, {
      "itemLabel" : {
        "xml:lang" : "en",
        "type" : "literal",
        "value" : "Denis Lawson"
      }
    }, {
      "itemLabel" : {
        "xml:lang" : "en",
        "type" : "literal",
        "value" : "Didier Bourdon"
      }
    }, {
      "itemLabel" : {
        "xml:lang" : "en",
        "type" : "literal",
        "value" : "Arsène Jiroyan"
      }
    }, {
      "itemLabel" : {
        "xml:lang" : "en",
        "type" : "literal",
        "value" : "Aude Thirion"
      }
    }, {
      "itemLabel" : {
        "xml:lang" : "en",
        "type" : "literal",
        "value" : "Caity Lotz"
      }
    }, {
      "itemLabel" : {
        "xml:lang" : "en",
        "type" : "literal",
        "value" : "Christian Bujeau"
      }
    }, {
      "itemLabel" : {
        "xml:lang" : "en",
        "type" : "literal",
        "value" : "Erwan Baynaud"
      }
    }, {
      "itemLabel" : {
        "xml:lang" : "en",
        "type" : "literal",
        "value" : "Marc Andréoni"
      }
    }, {
      "itemLabel" : {
        "xml:lang" : "en",
        "type" : "literal",
        "value" : "Sam Hazeldine"
      }
    } ]
  }
}

The results are stored in the JSON array named "bindings"  which resides in "results" json object.
