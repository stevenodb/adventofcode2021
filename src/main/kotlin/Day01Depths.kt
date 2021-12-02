fun main() {
    val numbers = readInput("Day01").map { it.toInt() }
    println(countIncreases(numbers))
    println(countIncreasesWindowed(numbers))
}

internal fun countIncreases(numbers: List<Int>) =
    numbers.windowed(2).count { it[0] < it[1] }

internal fun countIncreasesWindowed(numbers: List<Int>) =
    countIncreases(numbers.windowed(3).map { it.sum() })