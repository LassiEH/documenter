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

## How it works

Documenter should be used to compile all needed documentation for easy browsing.
The applications user interface is divided into three compartments: Document viewer, 
repository tree viewer and file content viewer. See the picture below for visualisation.

![Example how all the UI functionalities can be used.](/images/all_ui_functionalities.png)

#### Document viewer

Here you create the document with header and paragraph text inputs, images and code snippets you can pick
from the file content viewer after opening the file with the repository viewer.

You can also save and load your document. See below for details:

![Example how you can load and add stuff.](/images/gif_load_json.gif)

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


