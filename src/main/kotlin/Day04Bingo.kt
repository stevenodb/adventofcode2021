import kotlin.math.sqrt

fun main() {
    val input = readInput("Day04")
    val iterator = input.iterator()
    val numbers = parseDrawNumbers(iterator)
    val boards = parseBoards(iterator)

    val winingBoards = bingo(numbers, boards)
    val (sumFirst, numFirst) = winingBoards.first()
    println("Part 1: [sum=]$sumFirst * [number=]$numFirst = ${sumFirst * numFirst}")
    val (sumLast, numLast) = winingBoards.last()
    println("Part 2: [sum=]$sumLast * [number=]$numLast = ${sumLast * numLast}")
}

private fun parseBoards(iterator: Iterator<String>): MutableList<Board> {
    val boards = mutableListOf<Board>()
    var boardLines: MutableList<Int> = mutableListOf()

    while (iterator.hasNext()) {
        val line = iterator.next()
        if (line.isEmpty()) continue

        val boardLine = line.trim().split(Regex("\\s+")).map { it.toInt() }.toMutableList()
        boardLines.addAll(boardLine)

        if (boardLines.size == 5 * 5) {
            boards.add(Board(boardLines))
            boardLines = mutableListOf()
        }
    }
    return boards
}

private fun parseDrawNumbers(iterator: Iterator<String>): List<Int> {
    return iterator.next().split(',').map { it.toInt() }
}

private fun bingo(numbers: List<Int>, boards: MutableList<Board>): MutableList<Pair<Int, Int>> {
    val winingBoards = mutableListOf<Pair<Int, Int>>()
    for (number in numbers) {
        for (board in boards) {
            board.mark(number)
            if (board.hasBingo()) {
                winingBoards.add(Pair(board.sum(), number))
            }
        }
    }
    return winingBoards
}

class Board(private val board: MutableList<Int>, private var alreadyWon: Boolean = false) {

    fun mark(number: Int) = board.replaceAll { if (it == number) -1 else it }

    fun hasBingo(): Boolean {
        if (alreadyWon) return false
        alreadyWon = hasRowBingo() || hasColumnBingo()
        return alreadyWon
    }

    private fun hasRowBingo(): Boolean {
        return board.windowed(5, 5).any { row -> row.all { elem -> elem == -1 } }
    }

    private fun hasColumnBingo(): Boolean {
        return board.windowEvery(5).any { col -> col.all { elem -> elem == -1 } }
    }

    fun sum(): Int = board.filter { it != -1 }.sum()

    override fun toString(): String {
        val side = sqrt(board.size.toDouble()).toInt()
        return board.windowed(side, side, partialWindows = true)
            .joinToString(separator = "") { it.joinToString("\t", postfix = "\n") }
    }
}

private fun <E> List<E>.windowEvery(every: Int): List<List<E>> {
    require(every >= 0)
    val result = ArrayList<List<E>>()
    val windowSize = size / every
    for (index in 0 until size / windowSize) {
        result.add(List(windowSize) { this[it * every + index] })
    }
    return result
}