import kotlin.math.abs

fun main() {
    val input = readInput("Day05")
    println("Part 1: ${parseField(input, 1000, false).countOverlapping()}")
    println("Part 2: ${parseField(input, 1000).countOverlapping()}")
}

fun parseField(input: List<String>, dimension: Int, includeDiagonal: Boolean = true): Field {
    val field = Field(dimension, dimension)
    input.map { line -> line.split(",", " -> ").map { it.toInt() } }
        .filter { (x1, y1, x2, y2) -> includeDiagonal || x1 == x2 || y1 == y2 }
        .forEach { (x1, y1, x2, y2) -> field.addLine(x1, y1, x2, y2) }
    return field
}

class Field(width: Int, height: Int) {
    private val field = MutableList(height) { MutableList(width) { 0 } }

    fun addLine(x1: Int, y1: Int, x2: Int, y2: Int) {
        val x21 = x2 - x1
        val dx = if (x21 != 0) x21 / abs(x21) else 0
        val y21 = y2 - y1
        val dy = if (y21 != 0) y21 / abs(y21) else 0

        var ix = x1
        var iy = y1
        while (iy != y2 + dy || ix != x2 + dx) {
            field[iy][ix] += 1
            ix += dx; iy += dy
        }
    }

    override fun toString(): String {
        return field.joinToString(separator = "", limit = 25) { it.joinToString(separator = " ", prefix = "|", postfix = "|\n", limit = 80) }
    }

    fun countOverlapping(): Int {
        @Suppress("USELESS_CAST")
        return field.sumOf { row -> row.sumOf { item -> if (item > 1) 1 as Int else 0 } }
    }
}