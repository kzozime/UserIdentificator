# UserIdentificator

Spring Boot API for create and registering users.

## Requirements

For building and running the application you need:

- [JDK 17 or +](https://www.oracle.com/java/technologies/downloads/#java17)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. 

One way is to execute the `main` method in the `com.otsa.useridentificator.UserIdentificatorApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

Finally, you can run the application by executing the .jar file in the `out` directory:
```shell
java -jar out/artifacts/userIdentificator_jar/userIdentificator.jar
```

## API Requests

| Method  | Path           | Description             |
|---------|----------------|-------------------------|
| GET     | /users         | List all the users      |
| GET     | /users/{id}    | retrieve a user by id   |
| POST    | /users         | Register a new user     |

You can use the api with the `postman collection` in the `postman` directory
