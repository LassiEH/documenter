package github

import dataClasses.FileNode
import dataClasses.FolderNode

object MockRepoNode {

    fun test(): FolderNode =
        FolderNode(
            name = "test",
            path = "",
            children = mutableListOf(
                FolderNode(
                    name = "gradle",
                    path = "gradle",
                    children = mutableListOf(
                        FolderNode(
                            name = "wrapper",
                            path = "gradle/wrapper",
                            children = mutableListOf(
                                FileNode(
                                    name = "gradle-wrapper.jar",
                                    path = "gradle/wrapper/gradle-wrapper.jar",
                                    content = "ZnVuIG1haW4oKSB7CnByaW50bG4oIkhlbGxvIHRlc3QhIikKfQ=="
                                ),
                            )
                        ),
                    )
                ),
                FolderNode(
                    name = "src",
                    path = "src",
                    children = mutableListOf(
                        FolderNode(
                            name = "main",
                            path = "src/main",
                            children = mutableListOf(
                                FileNode(
                                    name = ".gitignore",
                                    path = "src/main/.gitignore",
                                    content = "ZnVuIG1haW4oKSB7CnByaW50bG4oIkhlbGxvIHRlc3QhIikKfQ=="
                                ),
                                FileNode(
                                    name = "Main.kt",
                                    path = "src/main/Main.kt",
                                    content = "ZnVuIG1haW4oKSB7CnByaW50bG4oIkhlbGxvIHRlc3QhIikKfQ=="
                                )
                            )
                        ),
                        FileNode(
                            name = "app.py",
                            path = "src/app.py",
                            content = "ZnVuIG1haW4oKSB7CnByaW50bG4oIkhlbGxvIHRlc3QhIikKfQ=="
                        )
                    )
                )
            )
        )
}

