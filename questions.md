# Congestion Tax Calculator

##### Running the application
README.md file was added with the instruction to run the application

Application can be access via Rest API calls, and authentication is not introduce because of the time restrictions for the development.

##### Tax rule content handle outside of the application
Simple H2 DataBase was introduce to keep the data in the database, so we can change the data any time we want. Since all tax rules are depend on the city we can introduce rules per city.
H2 DB is written to a file as all data will be there for each application startup. 
H2 DB : jdbc:h2:./data/db