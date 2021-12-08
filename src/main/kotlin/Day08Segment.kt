/*
  0:      1:      2:      3:      4:
 aaaa    ....    aaaa    aaaa    ....
b    c  .    c  .    c  .    c  b    c
b    c  .    c  .    c  .    c  b    c
 ....    ....    dddd    dddd    dddd
e    f  .    f  e    .  .    f  .    f
e    f  .    f  e    .  .    f  .    f
 gggg    ....    gggg    gggg    ....

  5:      6:      7:      8:      9:
 aaaa    aaaa    aaaa    aaaa    aaaa
b    .  b    .  .    c  b    c  b    c
b    .  b    .  .    c  b    c  b    c
 dddd    dddd    ....    dddd    dddd
.    f  e    f  .    f  e    f  .    f
.    f  e    f  .    f  e    f  .    f
 gggg    gggg    ....    gggg    gggg

 1: 2 *
 4: 4 *
 7: 3 *
 8: 7 *
 2: 5
 3: 5
 5: 5
 6: 6
 9: 6
 0: 6
 */
fun main() {
    val input = readInput("Day08").map { it.split(" ") }

    val count = countUniqueDigits(input)
    println("Part1 : $count")

    val sum = input.map { inputSet ->
        val digitMap = solveDigits(inputSet.subList(0, 10).toSet())
        inputSet.subList(11, 15)
            .map { value -> value.map { segment -> digitMap[segment]!! } }
            .map { value -> toDigit(value) }
            .joinToString("").toInt()
    }.sum()
    println(sum)
}

internal fun countUniqueDigits(input: List<List<String>>): Int {
    return input.flatMap { it.subList(11, 15) }
        .count { value -> listOf(2, 3, 4, 7).any { uniqueLength -> uniqueLength == value.length } }
}

internal fun toDigit(value: List<Char>): String =
    when (value.sorted().joinToString(separator = "")) {
        "abcefg" -> "0"
        "cf" -> "1"
        "acdeg" -> "2"
        "acdfg" -> "3"
        "bcdf" -> "4"
        "abdfg" -> "5"
        "abdefg" -> "6"
        "acf" -> "7"
        "abcdefg" -> "8"
        "abcdfg" -> "9"
        else -> throw IllegalArgumentException("Digit doesn't exist (${value.sorted().joinToString(separator = "")}).")
    }

internal fun solveDigits(patterns: Set<String>): Map<Char, Char> {
    val resultMapping = HashMap<Char, Char>()

    val one = patterns.findUniqueDigit(1)

    val seven = patterns.findUniqueDigit(7)
    resultMapping['a'] = seven.subtract(one).single()

    val intersectionSixes =
        patterns.reduceToIntersection(6)
    val intersectionFives =
        patterns.reduceToIntersection(5)
    resultMapping['g'] = intersectionSixes.intersect(intersectionFives).minusElement(resultMapping['a']).single()!!

    val four = patterns.findUniqueDigit(4)
    resultMapping['d'] = intersectionFives.intersect(four).single()

    resultMapping['f'] = intersectionSixes.intersect(one).single()

    resultMapping['c'] = one.minusElement(resultMapping['f']).single()!!

    val eight = patterns.findUniqueDigit(8)
    resultMapping['b'] =
        eight.minus(listOf('a', 'c', 'd', 'f', 'g', 'e').map { resultMapping[it] }.toSet()).intersect(four).first()!!

    resultMapping['e'] =
        eight.minus(listOf('a', 'c', 'd', 'f', 'g', 'e', 'b').map { resultMapping[it] }.toSet()).first()!!

    return resultMapping.entries.associate { (key, value) -> Pair(value, key) }
}

private fun Set<String>.reduceToIntersection(length: Int) =
    filter { it.length == length }.map { it.toSet() }.reduce { intersection, d -> intersection.intersect(d) }

private fun Set<String>.findUniqueDigit(digit: Int): Set<Char> {
    val length = when (digit) {
        1 -> 2
        4 -> 4
        7 -> 3
        8 -> 7
        else -> throw IllegalArgumentException("Not a unique digit.")
    }
    return first { it.length == length }.toSet()
}