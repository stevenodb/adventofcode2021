fun main() {
    val input = parseInput(readInput("Day12"))
    println("Part 1: ${findAllPaths(input, "start").size}")
    println("Part 2: ${findAllPaths(input, "start", allowRepeatedLowerCase = true).size}")
}

internal fun printPaths(paths: List<List<String>>) {
    println(paths.map { path -> path.joinToString(",", postfix = "\n") }.sortedBy { it.length }.joinToString(""))
}

internal fun parseInput(strings: List<String>) =
    strings.map { line -> line.split('-') }.flatMap { listOf(it.toFirstLastPair(), it.toFirstLastPair().reversed()) }
        .groupBy({ it.first }, { it.second })

fun findAllPaths(
    connections: Map<String, List<String>>,
    node: String,
    path: List<String> = listOf(),
    allowRepeatedLowerCase: Boolean = false
): MutableList<List<String>> {
    if (node == "end") return mutableListOf(path.plus(node))
    val nextNodes = connections[node] ?: return mutableListOf()

    val newPath = path.plus(node)
    val foundPaths = mutableListOf<List<String>>()
    nextNodes.forEach { nextNode ->
        when {
            moreThanOneStartGiven(nextNode, newPath) -> return@forEach
            moreThanOneRepeatedLowerCaseNodeGiven(nextNode, newPath, allowRepeatedLowerCase) -> return@forEach
            else -> foundPaths.addAll(findAllPaths(connections, nextNode, newPath, allowRepeatedLowerCase))
        }
    }
    return foundPaths
}

private fun moreThanOneStartGiven(nextNode: String, path: List<String>) = nextNode == "start" && nextNode in path

private fun moreThanOneRepeatedLowerCaseNodeGiven(
    nextNode: String, nextPath: List<String>, allowRepeatedLowerCase: Boolean
): Boolean {
    if (!nextNode.isLowerCase()) return false
    if (nextNode !in nextPath) return false
    if (allowRepeatedLowerCase) {
        return nextPath.count { it == nextNode } in 1..2 && nextPath.filter { it.isLowerCase() }
            .any { node -> nextPath.count { it == node } > 1 }
    }
    return nextNode.isLowerCase() && nextNode in nextPath
}

private fun String.isLowerCase() = all { char -> char.isLowerCase() }

private fun <T> Iterable<T>.toFirstLastPair(): Pair<T, T> = Pair(first(), last())

private fun <A, B> Pair<A, B>.reversed(): Pair<B, A> = Pair(second, first)