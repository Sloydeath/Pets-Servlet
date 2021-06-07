## Requirements
* Java 1.8
* Maven 3.8.1
* Tomcat 8.5.66
* PostgreSQL 11.12

## Deployment

1. Clone git repository:
```
https://github.com/Sloydeath/pets.git
```
2. Install PostgreSQL and run the db script from file:
```
src/main/resources/db/pets.sql
```
3. Change user and password in file:
```
src/main/resources/META-INF/persistence.xml
```
4. Establish a connection to the database.
5. Build project:
```
mvn clean install
```
6. Configure Tomcat for build.
7. If you want to test app, you should to install postman and import file from file:
```
src/main/resources/postman/Pets.postman_collection.json
```
