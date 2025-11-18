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
                    name = "src",
                    path = "src",
                    children = mutableListOf(
                        FileNode(
                            name = "Main.kt",
                            path = "src/Main.kt",
                            content = "ZnVuIG1haW4oKSB7CnByaW50bG4oIkhlbGxvIHRlc3QhIikKfQ=="
                        )
                    )
                )
            )
        )
}

