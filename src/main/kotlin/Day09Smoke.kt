fun main() {
    val heightMap = readInput("Day09").map { line -> line.toList().map { char -> char.digitToInt() } }
    val lowPoints = findLowPoints(heightMap)

    println("Part 1: ${determineRiskLevels(lowPoints.map { (_, height) -> height })}")
    val largestBasins = determineBasinsProduct(heightMap, lowPoints.map { (point, _) -> point })
    println("Part 2: ${largestBasins}")
}

fun determineBasinsProduct(heightMap: List<List<Int>>, lowPoints: List<Pair<Int, Int>>): Int {
    val visitMap = heightMap.map { it.toMutableList() }.toList()
    val result = lowPoints.map { (x, y) -> exploreBasin(visitMap, x, y) + 1 }
    println(visitMap.joinToString("") { it.joinToString("", postfix = "\n") })
    return result.sortedDescending().take(3).reduce { acc, int -> acc * int }
}

private fun exploreBasin(visitMap: List<MutableList<Int>>, x: Int, y: Int): Int {
    visitMap[y][x] = 0
    return (-1..1).flatMap { iy ->
        (-1..1)
            .asSequence()
            .filterNot { ix -> (ix != 0) && (iy != 0) }
            .filterNot { ix -> (ix == 0) && (iy == 0) }
            .map { ix -> (x + ix) to (y + iy) }
            .filter { (nx, ny) ->
                visitMap.getOrNull(ny)?.getOrNull(nx)?.let { height ->
                    when (height) {
                        0 -> false
                        9 -> false
                        else -> true
                    }
                } == true
            }
            .map { (nx, ny) ->
                exploreBasin(visitMap, nx, ny) + 1
            }
    }.sum()
}

internal fun determineRiskLevels(lowPoints: List<Int>): Int {
    return lowPoints.sumOf { it + 1 }
}

internal fun findLowPoints(heightMap: List<List<Int>>) =
    heightMap.flatMapIndexed { y, row ->
        row.mapIndexed { x, height -> (x to y) to heightMap.isLowPoint(x, y, height) }
            .filter { (_, isLowPoint) -> isLowPoint }
            .map { (point, _) -> point to heightMap[point.second][point.first] }
    }

private fun <T : Comparable<T>> List<List<T>>.isLowPoint(x: Int, y: Int, currentHeight: T): Boolean {
    var maxAdjacent = 0
    val adjacentHeights = ArrayList<T>()
    (-1..1).forEach { iy ->
        (-1..1)
            .filterNot { ix -> ((ix != 0) && (iy != 0)) }
            .filterNot { ix -> ((ix == 0) && (iy == 0)) }
            .forEach { ix ->
                getOrNull(y + iy)?.getOrNull(x + ix)
                    ?.let { adjacentHeight ->
                        if (currentHeight < adjacentHeight) {
                            adjacentHeights.add(adjacentHeight)
                        }
                        maxAdjacent++
                    }
            }
    }
    return adjacentHeights.size == maxAdjacent
}