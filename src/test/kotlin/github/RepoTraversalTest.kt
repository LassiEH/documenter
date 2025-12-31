package github

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

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

    @Test
    fun `file does not exist`() {
        val handler = RepoHandler()
        val root = MockRepoNode.test()

        val result = handler.findFile(root, "Nonexisting.kt")
        assertNull(result)
    }

}