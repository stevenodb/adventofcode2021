import java.lang.StrictMath.abs

fun main() {
    val positions = readInput("Day07").flatMap { it.split(",") }.map { it.toInt() }

    println("Part 1: ${optimalFuelPart1(positions)}")
    println("Part 2: ${optimalFuelPart2(positions)}")
}

internal fun optimalFuelPart1(positions: List<Int>): Map.Entry<Int, Int>? =
    optimalFuel(positions) { it }

internal fun optimalFuelPart2(positions: List<Int>): Map.Entry<Int, Int>? =
    optimalFuel(positions) { d -> (1 + d) * d / 2 } // sum of an arithmetic sequence: (a1 + an) * n/2

private fun optimalFuel(positions: List<Int>, fuelFunction: (Int) -> Int): Map.Entry<Int, Int>? {
    val min = positions.minOrNull() ?: 0
    val max = positions.maxOrNull() ?: 0

    val result = (min..max)
        .associateWith { p_target ->
            positions.sumOf { p_start: Int ->
                val d = abs(p_start - p_target)
                fuelFunction.invoke(d)
            }
        }
        .entries.minByOrNull { it.value }
    return result
}