fun main() {
    val input = parseInput(readInput("Day12"))
    val allPaths = findAllPaths(input, "start")
//    printPaths(allPaths)
    println("Part 1: ${allPaths.size}")
}

internal fun printPaths(paths: List<List<String>>) {
    println(paths.map { path -> path.joinToString(",", postfix = "\n") }.sortedBy { it.length }.joinToString(""))
}

internal fun parseInput(strings: List<String>) =
    strings.map { line -> line.split('-') }
        .flatMap { listOf(it.toFirstLastPair(), it.toFirstLastPair().reversed()) }
        .groupBy({ it.first }, { it.second })

fun findAllPaths(
    connections: Map<String, List<String>>, node: String, path: List<String> = listOf()
): MutableList<List<String>> {
    if (node == "end") return mutableListOf(path.plus(node))
    val nextNodes = connections[node] ?: return mutableListOf()

    val newPath = path.plus(node)
    val foundPaths = mutableListOf<List<String>>()
    nextNodes.forEach { nextNode ->
        if ((nextNode.isLowerCase() && nextNode in path)) return@forEach

        foundPaths.addAll(findAllPaths(connections, nextNode, newPath))

    }
    return foundPaths
}

private fun String.isLowerCase() = all { char -> char.isLowerCase() }

private fun <T> Iterable<T>.toFirstLastPair(): Pair<T, T> = Pair(first(), last())

private fun <A, B> Pair<A, B>.reversed(): Pair<B, A> = Pair(second, first)