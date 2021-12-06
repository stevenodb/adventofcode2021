fun main() {
    val fishCounts = readInput("Day06")
        .flatMap { it.split(",") }
        .map { it.toInt() }
        .groupingBy { it }
        .aggregate { _, accumulator: Long?, _, first -> if (first) 1 else accumulator!! + 1 }.toMutableMap()

    val count80 = fishWoohoo(80, fishCounts)
    println("Part 1: Fish after 80: $count80")
    val count256 = fishWoohoo(256, fishCounts)
    println("Part 2: Fish after 256: $count256")
}

fun fishWoohoo(numDays: Int, fishCountsInput: MutableMap<Int, Long>): Long {
    val fishCounts = HashMap(fishCountsInput)
    for (day in 1..numDays) {
        val zeroFishCount = fishCounts[0] ?: 0
        for (key in 1..8) {
            fishCounts[key - 1] = fishCounts[key] ?: 0
        }
        fishCounts.remove(8)
        fishCounts[6] = (fishCounts[6] ?: 0) + zeroFishCount
        fishCounts[8] = (fishCounts[8] ?: 0) + zeroFishCount
    }
    return fishCounts.values.sum()
}