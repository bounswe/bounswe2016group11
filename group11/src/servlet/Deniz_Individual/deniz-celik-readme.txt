This project deals with providing users access to American (United States) Soccer Team and their inception dates.

It was created by Deniz Celik, a member of Group 11, for the Spring 2016 iteration of CMPE 352 course at Bogazici University. 

The servlet created provides a few different options for accessing, acquiring and playing with the data. They are summed up below:


Refresh Database: Drops the database before creating it again and filling a table with information pulled from the WikiData SPARQL API.

Make a Query: Queries both the WikiData table and the User generated data table by year of inception. Removes duplicates before showing.
	      If a year is queried that does not exist in either data table, it will return the teams whose inception years are closest 
to that of the requested year. These closest teams will be drawn only from the WikiData table.
	      
Add Data: Adds custom user data to a User Data table. Duplicate additions are ignored.

Delete Data: Removes selected user data from the User Data table. Deleting a saved entry does not delete it from the main WikiData table.

Erase User Data: Removes all user data from the table.

Save Data: Adds selected WikiData data to the User Data table. Does not remove from WikiData upon saving.


	
	