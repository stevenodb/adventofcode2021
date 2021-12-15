fun main() {
    val input = readInput("Day14")
    val (polymer, inserts) = parsePolymerInput(input)

    val lowHigh_10 = polymerInsertion(polymer, inserts, 10).lowHigh()
    val lowHigh_40 = polymerInsertion(polymer, inserts, 40).lowHigh()
    println("Part1: ${lowHigh_10.second - lowHigh_10.first}")
    println("Part2: ${lowHigh_40.second - lowHigh_40.first}")
}

fun polymerInsertion(polymer: String, insertPatterns: Map<PolymerPair, Char>, iterations: Int): FrequencyMap {
    val pairs = polymer.windowed(2, step = 1).map { PolymerPair(it[0], it[1]) }
    val cache = PolymerCache()
    val frequencyMap = pairs
        .map { frequencies(it, iterations, insertPatterns, cache) }
        .reduce { acc, frequencyMap -> acc + frequencyMap }
    val inserts = pairs.subList(0, pairs.size - 1).map { it.second }
    return inserts.fold(frequencyMap) { acc, c -> acc.minus(c) }
}

fun frequencies(
    pair: PolymerPair,
    iterationsLeft: Int,
    insertPatterns: Map<PolymerPair, Char>,
    cache: MutableMap<Pair<PolymerPair, Int>, FrequencyMap>
): FrequencyMap {
    println(iterationsLeft)
    if (iterationsLeft == 0) return FrequencyMap(pair)
    val insert = insertPatterns[pair] ?: return FrequencyMap(pair)
    val (first, second) = pair.split(insert)

    val firstResult = cache[first to iterationsLeft] ?: frequencies(first, iterationsLeft - 1, insertPatterns, cache)
    val secondResult = cache[second to iterationsLeft] ?: frequencies(second, iterationsLeft - 1, insertPatterns, cache)

    val result = firstResult + secondResult - insert

    cache[first to iterationsLeft] = firstResult
    cache[second to iterationsLeft] = secondResult
    return result
}

data class PolymerPair(val first: Char, val second: Char) {
    fun split(insert: Char): Pair<PolymerPair, PolymerPair> {
        return PolymerPair(first, insert) to PolymerPair(insert, second)
    }
}

class PolymerCache : LinkedHashMap<Pair<PolymerPair, Int>, FrequencyMap>()

class FrequencyMap(initMap: Map<Char, ULong>) : /*MutableMap<Char, ULong>,*/ HashMap<Char, ULong>(initMap) {

    constructor(polymerPair: PolymerPair) : this(mutableMapOf()) {
        listOf(polymerPair.first to 1uL, polymerPair.second to 1uL).forEach {
            this.merge(it.first, it.second) { v1, v2 -> v1 + v2 }
        }
    }

    operator fun plus(otherMap: FrequencyMap): FrequencyMap {
        return FrequencyMap(this.toMutableMap().apply {
            otherMap.forEach { other ->
                merge(other.key, other.value) { val1, val2 -> val1 + val2 }
            }
        })
    }

    operator fun minus(insert: Char): FrequencyMap {
        return FrequencyMap(
            toMutableMap().apply {
                get(insert)?.minus(1uL)?.let {
                    put(insert, it)
                }
            }
        )
    }

    fun lowHigh(): Pair<ULong, ULong> {
        return entries.minByOrNull { it.value }!!.value to entries.maxByOrNull { it.value }!!.value
    }
}

internal fun parsePolymerInput(lines: List<String>): Pair<String, Map<PolymerPair, Char>> {
    val polymer = lines[0]
    val insertPatterns = lines.subList(lines.indexOf("") + 1, lines.size)
        .map { line -> line.replace(" ", "").split("->") }
        .associate { Pair(PolymerPair(it[0][0], it[0][1]), it[1].first()) }
    return polymer to insertPatterns
}