A5 Repo for CS6310 Fall 2021
# Build Project With Maven:
Build jar file with Maven, recommend to build in Intellij IDE. It will build the jar file in `target` folder.

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