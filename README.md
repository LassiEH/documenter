# documenter

Documenter is an application that closely connects your repository with your documentation.
You can write your documentation while adding context with images like architecture digrams but also code snippets.

## Features

This project when completed should have the following capabilities:

| Feature                 | Description                        |
|-------------------------|------------------------------------|
| Fetch GitHub repository | User can fetch their repository    |
| Create a document       | User can add text, code and images |
| Save document           | User can save the document         |

## Building & Running

To build or run the project, use one of the following tasks:

| Task                                    | Description                                                          |
| -----------------------------------------|---------------------------------------------------------------------- |
| `./gradlew test`                        | Run the tests                                                        |
| `./gradlew build`                       | Build everything                                                     |
| `./gradlew buildFatJar`                 | Build an executable JAR of the server with all dependencies included |
| `./gradlew buildImage`                  | Build the docker image to use with the fat JAR                       |
| `./gradlew publishImageToLocalRegistry` | Publish the docker image locally                                     |
| `./gradlew run`                         | Run the server                                                       |
| `./gradlew runDocker`                   | Run using the local docker image                                     |


