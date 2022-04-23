A5 Repo for CS6310 Fall 2021
# Build Project With Maven:
Build jar file with Maven, Please install Maven `3.8.5` ([How To Install Maven](https://phoenixnap.com/kb/install-maven-on-ubuntu))  then run command under the project root folder:
```
mvn package
```

# Run Application With Docker Compose:
After you build the jar file, you can run below command to start the application with database setup properly.
```
docker-compose run app java -jar /drone_delivery-0.0.1.jar
```

# Reset MySql Database:
If you mess up the database for some reason, you can run below command to remove the database.
```
docker-compose down && docker volume rm database_db
```

# Default User:
The default user when you load the new database: 
```
username: admin
password: password
```
