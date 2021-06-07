## Requirements
* Java 1.8
* Maven 3.8.1
* Tomcat 8.5.66
* PostgreSQL

## Deployment

1. Clone git repository:
```
https://github.com/Sloydeath/pets.git
```
2. Build project:
```
mvn clean install
```
3. Install PostgreSQL and run the db script from folder:
```
src/main/resources/db/pets.sql
```
4. Establish a connection to the database.
5. Configure Tomcat for build.
6. If you want to test app, you should to install postman and import file from folder:
```
src/main/resources/postman/Pets.postman_collection.json
```
