fun main() {
    val fishCounts = readInput("Day06")
        .flatMap { it.split(",") }
        .map { it.toInt() }
        .groupingBy { it }
        .eachCount()
    val count80 = fishWoohoo(80, fishCounts)
    println("Part 1: Fish after 80: $count80")
    val count256 = fishWoohoo(256, fishCounts)
    println("Part 2: Fish after 256: $count256")
}

fun fishWoohoo(numDays: Int, fishCountsInput: Map<Int, Int>): ULong {
    val fishCounts = mutableMapOf<Int, ULong>()
    fishCountsInput.entries.associateTo(fishCounts) { (key, value) -> Pair(key, value.toULong()) }

    for (day in 1..numDays) {
        val zeroFishCount = fishCounts[0] ?: 0uL
        for (key in 1..8) {
            fishCounts[key - 1] = fishCounts[key] ?: 0uL
        }
        fishCounts.remove(8)
        fishCounts[6] = (fishCounts[6] ?: 0uL) + zeroFishCount
        fishCounts[8] = (fishCounts[8] ?: 0uL) + zeroFishCount
    }
    return fishCounts.values.sum()
}