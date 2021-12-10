import java.util.*
import kotlin.math.ceil

fun main() {
    val input = readInput("Day10")
    println("Part 1: ${scoreInvalidChars(validateChunks(input))}")
    println("Part 2: ${scoreCompletionChunkPieces(input)}")
}

fun scoreCompletionChunkPieces(input: List<String>): Long {
    val scores = input
        .asSequence()
        .filter { errorInChunk(it) == null }
        .toList()
        .map { determineMissingChunkPiece(it) }
        .map { scoreCompletion(it) }
        .sorted()
        .toList()
    return scores[ceil(scores.size / 2.0).toInt() - 1]
}

internal fun determineMissingChunkPiece(brackets: String): List<Char> {
    val openingStack = Stack<Bracket>()
    brackets.map { char -> Bracket(char) }
        .forEach { currentBracket ->
            when {
                currentBracket.isClosing() -> {
                    val opening = openingStack.pop()
                    assert(opening.matchesClosing(currentBracket))
                }
                else -> {
                    openingStack.push(currentBracket)
                }
            }
        }
    return openingStack
        .reversed()
        .map { it.toClosing() }
        .map { it.toChar() }
}

private fun scoreCompletion(completingChars: List<Char>): Long {
    val scoreCard = mapOf(
        ')' to 1,
        ']' to 2,
        '}' to 3,
        '>' to 4
    )
    return completingChars.scan(0L) { acc, char -> acc * 5 + scoreCard[char]!! }.last()
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
    return input.mapNotNull { str -> errorInChunk(str) }
}

private fun errorInChunk(brackets: String): Char? {
    val openingStack = Stack<Bracket>()
    brackets.map { char -> Bracket(char) }
        .forEach { currentBracket ->
            when {
                currentBracket.isClosing() -> {
                    val openingBracket = openingStack.pop()
                    if (!openingBracket.matchesClosing(currentBracket)) {
                        return currentBracket.toChar()
                    }
                }
                else -> {
                    openingStack.push(currentBracket)
                }
            }
        }
    return null
}

private data class Bracket(private val bracket: Char) {
    private val pairs = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')

    fun isClosing(): Boolean = pairs.containsValue(bracket)

    fun matchesClosing(closing: Bracket): Boolean {
        return pairs[bracket] == closing.bracket
    }

    fun toChar() = bracket

    fun toClosing() = Bracket(pairs[bracket]!!)
}