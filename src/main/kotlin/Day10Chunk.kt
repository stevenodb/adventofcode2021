import java.util.*

fun main() {
    val input = readInput("Day10")
    val invalidChars = validateChunks(input)
    println("Part 1: ${scoreInvalidChars(invalidChars)}")
}

internal fun scoreInvalidChars(invalidChars: List<Char>): Int {
    val scoreCard = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137
    )
    return invalidChars.sumOf { scoreCard[it] ?: 0 }
}

internal fun validateChunks(input: List<String>): List<Char> {
    return input.mapNotNull { str -> validateChunk(str.map { char -> Bracket(char) })?.toChar() }
}

internal fun validateChunk(brackets: List<Bracket>): Bracket? {
    val openingStack = Stack<Bracket>()
    val iterator = brackets.iterator()
    while (iterator.hasNext()) {
        val nextBracket = iterator.next()
        when {
            nextBracket.isClosingBracket() -> {
                val openingBracket = openingStack.pop()
                if (!openingBracket.matchesClosing(nextBracket)) {
                    return nextBracket
                }
            }
            else -> {
                openingStack.push(nextBracket)
            }
        }
    }
    return null
}


internal data class Bracket(private val bracket: Char) {
    private val pairs = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')

    fun isClosingBracket(): Boolean = pairs.containsValue(bracket)

    fun matchesClosing(closing: Bracket): Boolean {
        return pairs[bracket] == closing.bracket
    }

    fun toChar() = bracket
}