fun main() {
    val heightMap = readInput("Day09").map { line -> line.toList().map { char -> char.digitToInt() } }
    val lowPoints = findLowPoints(heightMap)

    println("Part 1: ${determineRiskLevels(lowPoints.map { (_, height) -> height })}")
    println("Part 2: ${determineBasinsProduct(heightMap, lowPoints.map { (point, _) -> point })}")
}

fun determineBasinsProduct(heightMap: List<List<Int>>, lowPoints: List<Pair<Int, Int>>): Int {
    val visitMap = heightMap.map { it.toMutableList() }.toList()
    val result = lowPoints.map { (x, y) -> exploreBasin(visitMap, x, y) }
//    println(visitMap.joinToString("") { it.joinToString("", postfix = "\n") })
    return result.sortedDescending().take(3).reduce { acc, int -> acc * int }
}

private fun exploreBasin(visitMap: List<MutableList<Int>>, x: Int, y: Int): Int {
    var sum = 1
    visitMap[y][x] = 0
    (-1..1).map { it + y }.forEach { ny ->
        (-1..1).map { it + x }.forEach { nx ->
            sum += when {
                isDiagonal(x to y, nx to ny) -> 0
                ny !in visitMap.indices -> 0
                nx !in visitMap[0].indices -> 0
                0 == visitMap[ny][nx] -> 0
                9 == visitMap[ny][nx] -> 0
                else -> exploreBasin(visitMap, nx, ny)
            }
        }
    }
    return sum
}

private fun isDiagonal(reference: Pair<Int, Int>, other: Pair<Int, Int>) =
    (reference.first != other.first) && (reference.second != other.second)

internal fun determineRiskLevels(lowPoints: List<Int>): Int {
    return lowPoints.sumOf { it + 1 }
}

internal fun findLowPoints(heightMap: List<List<Int>>): List<Pair<Pair<Int, Int>, Int>> {
    return heightMap
        .flatMapIndexed { y, row ->
            row.mapIndexed { x, height -> (x to y) to isLowPoint(heightMap, x, y, height) }
                .filter { (_, isLowPoint) -> isLowPoint }
                .map { (point, _) -> point to heightMap[point.second][point.first] }
        }
}

private fun isLowPoint(heightMap: List<List<Int>>, x: Int, y: Int, referenceHeight: Int): Boolean {
    var maxAdjacent = 0
    val adjacentHeights = ArrayList<Int>()

    (-1..1).map { it + y }.forEach { ny ->
        (-1..1).map { it + x }.forEach { nx ->
            val reference = x to y
            val current = nx to ny
            when {
                reference == current -> {}
                isDiagonal(reference, current) -> {}
                ny !in heightMap.indices -> {}
                nx !in heightMap[0].indices -> {}
                else -> {
                    val currentHeight = heightMap[ny][nx]
                    if (referenceHeight < currentHeight) {
                        adjacentHeights.add(currentHeight)
                    }
                    maxAdjacent++
                }
            }
        }
    }
    return adjacentHeights.size == maxAdjacent
}