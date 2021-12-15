fun main() {
    val inputLines = readInput("Day11")
    println("Part 1: ${flashCountAfter100Steps(inputLines)}")
    println("Part 2: ${numberOfStepsToSynchroFlash(inputLines)}")
}

fun flashCountAfter100Steps(inputLines: List<String>): Int {
    val octoField = OctoField(inputLines)
    var flashCount = 0
    repeat(100) {
        val (flashes, _) = step(octoField)
        flashCount += flashes
    }
    return flashCount
}

fun numberOfStepsToSynchroFlash(inputLines: List<String>): Int {
    val octoField = OctoField(inputLines)
    repeat(10000) {
        val (_, didSynchroFlash) = step(octoField)
        if (didSynchroFlash) return it + 1
    }
    return -1
}

private fun step(octoField: OctoField): Pair<Int, Boolean> {
    var flashCount = 0

    val toHandleFlashes = mutableListOf<Point>()
    toHandleFlashes.addAll(octoField.traverseField { octo, _ ->
        return@traverseField octo.increaseEnergy()
    })

    flashCount += toHandleFlashes.size

    while (toHandleFlashes.isNotEmpty()) {
        val flashPoint = toHandleFlashes.removeFirst()
        val secondaryFlashes = octoField.handleFlash(flashPoint)
        flashCount += secondaryFlashes.size
        secondaryFlashes.forEach {
            toHandleFlashes.add(it)
        }
    }

    return Pair(flashCount, octoField.isSynchroFlash())
}

private class OctoField(lines: List<String>) {
    private val octoField: List<List<Octo>> = parseInput(lines)

    fun isSynchroFlash(): Boolean = octoField.all { row -> row.all { octo -> octo.hasFlashed() } }

    fun traverseField(operation: (Octo, Point) -> Boolean): List<Point> {
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

    fun handleFlash(point: Point): List<Point> {
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

    override fun toString(): String {
        return octoField.joinToString("") { it.joinToString("", postfix = "\n") }
    }

    private fun parseInput(inputLines: List<String>): List<List<Octo>> {
        return inputLines.map { line ->
            line.map { char -> char.digitToInt() }.map { Octo(it) }
        }
    }

    data class Octo(private var energy: Int) {

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
}