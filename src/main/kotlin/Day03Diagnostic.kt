fun main() {
    val input = readInput("Day03")

    val bitResult = reduceToCommonBits(input)
    val gamma = bitResult.joinToString("").toInt(2)
    val epsilon = bitResult.map { if (it == 0) 1 else 0 }.joinToString("").toInt(2)

    println("Part 1: $gamma * $epsilon = ${gamma * epsilon}")

    val oxygen = reduceToValueByBitCommonality(input).toInt(2)
    val co2 = reduceToValueByBitCommonality(input, false).toInt(2)
    println("Part 2: $oxygen * $co2 = ${oxygen * co2}")

}

fun reduceToValueByBitCommonality(input: List<String>, mostCommon: Boolean = true): String {
    val bitCount = input.first().length
    var bitNumbers = input
    for (bit in 0 until bitCount) {
        val count = bitNumbers.map { it[bit] }.count { it == '1' }
        val selectedBit = if (!mostCommon xor (count >= bitNumbers.size / 2.0)) '1' else '0'
        bitNumbers = bitNumbers.filter { it[bit] == selectedBit }
        if (bitNumbers.size == 1) break
    }
    return bitNumbers.single()
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