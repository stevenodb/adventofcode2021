import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isEqualTo

internal class Day10ChunkKtTest {
    @Test
    fun parseChunks() {
        val input = listOf(
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

        val invalidBracket = validateChunks(input)
        expectThat(invalidBracket).containsExactly('}', ')', ']', ')', '>')
    }

    @Test
    fun scoreInvalidChars() {
        val actualScore = scoreInvalidChars(listOf('}', ')', ']', ')', '>'))
        expectThat(actualScore).isEqualTo(26397)
    }
}