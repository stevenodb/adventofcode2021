fun main() {
    val inputLines = readInput("Day11")
    val flashCount = countFlashes(inputLines)
    println("Part 1: $flashCount")
}

fun countFlashes(inputLines: List<String>): Int {
    val octoField = parseInput(inputLines)
    var flashCount = 0
    repeat(100) {
        val toHandleFlashes = mutableListOf<Point>()

        toHandleFlashes.addAll(traverseField(octoField) { octo, _ ->
            return@traverseField octo.increaseEnergy()
        })
        flashCount += toHandleFlashes.size

        while (toHandleFlashes.isNotEmpty()) {
            val flashPoint = toHandleFlashes.removeFirst()
            val secondaryFlashes = handleFlash(octoField, flashPoint)
            flashCount += secondaryFlashes.size
            secondaryFlashes.forEach { toHandleFlashes.add(it) }
        }
    }
    println(octoField.joinToString("") { it.joinToString("", postfix = "\n") })
    return flashCount
}

private fun parseInput(inputLines: List<String>): List<List<Octo>> {
    val octoField = inputLines.map { line ->
        line.map { char -> char.digitToInt() }.map { Octo(it) }
    }
    return octoField
}

private fun traverseField(octoField: List<List<Octo>>, operation: (Octo, Point) -> Boolean): List<Point> {
    val points = mutableListOf<Point>()
    octoField.forEachIndexed { y, row ->
        row.forEachIndexed { x, octo ->
            val point = Point(x, y)
            val b = operation(octo, point)
            if (b) points.add(point)
        }
    }
    return points
}

private fun handleFlash(octoField: List<List<Octo>>, point: Point): List<Point> {
    val flashPoints = mutableListOf<Point>()
    with(point) {
        (-1..1).map { it + y }.forEach { ny ->
            (-1..1).map { it + x }.forEach { nx ->
                val didFlash = when {
                    nx == x && ny == y -> false
                    ny !in octoField.indices -> false
                    nx !in octoField[0].indices -> false
                    octoField[ny][nx].hasFlashed() -> false
                    else -> octoField[ny][nx].increaseEnergy()
                }
                if (didFlash) flashPoints.add(Point(nx, ny))
            }
        }
    }
    return flashPoints
}

internal data class Point(val x: Int, val y: Int)

internal data class Octo(private var energy: Int) {

    fun increaseEnergy(): Boolean {
        if (++energy > 9) {
            return flash()
        }
        return false
    }

    fun hasFlashed(): Boolean = energy == 0

    private fun flash(): Boolean {
        energy = 0
        return true
    }

    override fun toString(): String {
        return energy.toString()
    }
}