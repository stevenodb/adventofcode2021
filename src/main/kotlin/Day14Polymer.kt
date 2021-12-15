fun main() {
    val input = readInput("Day14")
    val (polymer, inserts) = parsePolymerInput(input)

    var wipPolymer = polymer
    repeat(10) {
        wipPolymer = polymerInsertion(wipPolymer, inserts)
//        println("Step $it: ${wipPolymer.length}")
    }

    val sortedFreq = wipPolymer.frequency().entries.sortedByDescending { it.value }
    println("Part 1: ${sortedFreq.first().value - sortedFreq.last().value}")
}

private fun String.frequency(): Map<Char, ULong> {
    val frequencyMap = mutableMapOf<Char, ULong>()
    this.forEach { frequencyMap[it] = (frequencyMap[it] ?: 0uL) + 1uL }
    return frequencyMap
}

internal fun parsePolymerInput(lines: List<String>): Pair<String, List<InsertPattern>> {
    val polymer = lines[0]
    val insertPatterns = lines.subList(lines.indexOf("") + 1, lines.size)
        .map { line -> line.replace(" ", "").split("->") }
        .map { InsertPattern(it[0], it[1]) }
    return polymer to insertPatterns
}

internal fun polymerInsertion(polymer: String, insertPatterns: List<InsertPattern>): String {
    val insertPairs =
        insertPatterns.flatMap { insert ->
            polymer
                .indexesOf(insert.pattern)
                .map { Insert(it, insert.value) }
        }
    var newPolymer = polymer
    insertPairs
        .sortedByDescending { it.afterIndex }
        .forEach { insert ->
            newPolymer =
                newPolymer.substring(0..insert.afterIndex) + insert.value + newPolymer.substring(insert.afterIndex + 1..newPolymer.lastIndex)
        }
    return newPolymer
}

private fun String?.indexesOf(str: String): List<Int> {
    return this?.mapIndexed { startIndex, _ ->
        val endIndex = startIndex + str.length
        when {
            endIndex > this.length -> null
            this.substring(startIndex, endIndex) == str -> startIndex
            else -> null
        }
    }?.filterNotNull()?.toList() ?: listOf()
}

data class InsertPattern(val pattern: String, val value: String)

data class Insert(val afterIndex: Int, val value: String) {
}