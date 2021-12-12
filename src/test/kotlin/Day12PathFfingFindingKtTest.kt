import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

internal class Day12PathFfingFindingKtTest {
    @Test
    fun findAllPaths_scenario19() {
        val input = listOf(
            "dc-end",
            "HN-start",
            "start-kj",
            "dc-start",
            "dc-HN",
            "LN-dc",
            "HN-end",
            "kj-sa",
            "kj-HN",
            "kj-dc"
        )

        val connectionMap = parseInput(input)
        val allPaths = findAllPaths(connectionMap, "start")
        printPaths(allPaths)
        expectThat(allPaths.size).isEqualTo(19)
    }

    @Test
    fun findAllPaths_scenario226() {
        val input = listOf(
            "fs-end",
            "he-DX",
            "fs-he",
            "start-DX",
            "pj-DX",
            "end-zg",
            "zg-sl",
            "zg-pj",
            "pj-he",
            "RW-he",
            "fs-DX",
            "pj-RW",
            "zg-RW",
            "start-pj",
            "he-WI",
            "zg-he",
            "pj-fs",
            "start-RW",
        )

        val connectionMap = parseInput(input)
        val allPaths = findAllPaths(connectionMap, "start")
        expectThat(allPaths.size).isEqualTo(226)
        printPaths(allPaths)
    }
}