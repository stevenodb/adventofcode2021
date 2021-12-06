fun main() {
    val fishTimers = readInput("Day06").flatMap { it.split(",") }.map { it.toInt() }.toMutableList()

    val numDays = 80
    for (day in 1..numDays) {
        var idx = 0
        val numberThisDay = fishTimers.size
        while (idx < numberThisDay) {
            if (fishTimers[idx] == 0) {
                    fishTimers[idx] = 6
                    fishTimers.add(8)
                } else {
                fishTimers[idx]--
            }
            idx++
        }
    }
    println("Part 1: Fish after $numDays: ${fishTimers.size}")
}