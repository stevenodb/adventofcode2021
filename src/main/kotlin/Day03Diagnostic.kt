import kotlin.math.pow

fun main() {
    val input = readInput("Day03")
    val numberOfBits = input.first().length

    val bitResult = reduceToCommonBits(numberOfBits, input.map { it.toInt(2) })
    val result = bitResult.bitwiseToInteger()
    val invResult = bitResult.map { if (it == 0) 1 else 0 }.bitwiseToInteger()

    println("$result * $invResult = ${result * invResult}")
}

internal fun reduceToCommonBits(numberOfBits: Int, ints: List<Int>): List<Int> {
    val bitCount = Array(numberOfBits) { 0 }
    for (i in 0 until numberOfBits) {
        val pow = 2 `**` i
        for (int in ints) {
            bitCount[i] += (int and pow) / pow
        }
    }
    return bitCount.map { if (it >= ints.size / 2) 1 else 0 }.reversed()
}

private fun List<Int>.bitwiseToInteger() =
    reduceRightIndexed { index, int, acc -> acc or (int * (2 `**` (this.size-1)-index)) }

private infix fun Int.`**`(i: Int) = this.toDouble().pow(i).toInt()