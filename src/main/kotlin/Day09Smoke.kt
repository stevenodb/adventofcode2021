fun main() {
    val heightMap = readInput("Day09").map { line -> line.toList().map { char -> char.digitToInt() } }
    println(determineRiskLevels(heightMap))
}

fun determineRiskLevels(heightMap: List<List<Int>>): Int {
    return findLowPoints(heightMap).sumOf { it + 1 }
}

private fun findLowPoints(heightMap: List<List<Int>>) =
    heightMap.flatMapIndexed { y, row -> row.filterIndexed { x, height -> heightMap.isLowPoint(x, y, height) } }

private fun <T : Comparable<T>> List<List<T>>.isLowPoint(x: Int, y: Int, currentHeight: T): Boolean {
    var adjacentCount = 0
    val adjacentHeights = ArrayList<T>()
    (-1..1).forEach { iy ->
        (-1..1)
            .filterNot { ix -> ((ix != 0) && (iy != 0)) }
            .filterNot { ix -> ((ix == 0) && (iy == 0)) }
            .forEach { ix ->
                getOrNull(y + iy)?.getOrNull(x + ix)?.let {
                    if (currentHeight < it) {
                        adjacentHeights.add(it)
                    }
                    adjacentCount++
                }
            }
    }
    return adjacentHeights.size == adjacentCount
}