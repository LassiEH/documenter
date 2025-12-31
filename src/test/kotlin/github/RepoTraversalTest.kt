package github

import dataClasses.*
import kotlin.test.Test
import github.MockRepoNode
import github.RepoHandler
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class RepoTraversalTest {
    @Test
    fun `find file in tree`() {
        val handler = RepoHandler()
        val root = MockRepoNode.test()

        val result = handler.findFile(root, "Main.kt")
        assertNotNull(result)
        assertEquals("Main.kt", result.name)
        assertEquals("src/main/Main.kt", result.path)
    }

}