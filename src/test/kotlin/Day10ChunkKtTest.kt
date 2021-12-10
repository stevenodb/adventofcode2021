import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isEqualTo

internal class Day10ChunkKtTest {
    private val input = listOf(
        "[({(<(())[]>[[{[]{<()<>>",
        "[(()[<>])]({[<{<<[]>>(",
        "{([(<{}[<>[]}>{[]{[(<()>",
        "(((({<>}<{<{<>}{[]{[]{}",
        "[[<[([]))<([[{}[[()]]]",
        "[{[{({}]{}}([{[{{{}}([]",
        "{<[[]]>}<{[{[{[]{()[[[]",
        "[<(<(<(<{}))><([]([]()",
        "<{([([[(<>()){}]>(<<{{",
        "<{([{{}}[<[[[<>{}]]]>[]]"
    )

    @Test
    fun parseChunks() {
        expectThat(validateChunks(input))
            .containsExactly('}', ')', ']', ')', '>')
    }

    @Test
    fun scoreInvalidChars() {
        expectThat(scoreInvalidChars(listOf('}', ')', ']', ')', '>')))
            .isEqualTo(26397)
    }

    @Test
    fun scoreCompletionChunks() {
        expectThat(scoreCompletionChunkPieces(input))
            .isEqualTo(288957)
    }
}