fun main() {
    val input = readInput("Day03")

    val bitResult = reduceToCommonBits(input)
    val result = bitResult.joinToString("").toInt(2)
    val invResult = bitResult.map { if (it == 0) 1 else 0 }.joinToString("").toInt(2)

    println("$result * $invResult = ${result * invResult}")
}

fun reduceToCommonBits(bitNumbers: List<String>): List<Int> {
    val bitCount = bitNumbers.first().length

    val countOnes = Array(bitCount) { 0 }
    for (number in bitNumbers) {
        for (bit in 0 until bitCount) {
            countOnes[bit] += if (number[bit] == '1') 1 else 0
        }
    }
    return countOnes.map { if (it >= bitNumbers.size / 2) 1 else 0 }
}