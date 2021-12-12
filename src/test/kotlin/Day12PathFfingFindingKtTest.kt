import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

internal class Day12PathFfingFindingKtTest {
    private val smallInput = parseInput(
        listOf(
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
    )

    private val biggerInput = parseInput(
        listOf(
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
    )

    @Test
    fun findAllPaths_scenarioSmall() {
        val allPaths = findAllPaths(smallInput, "start")
        expectThat(allPaths.size).isEqualTo(19)
    }

    @Test
    fun findAllPaths_scenarioBigger() {
        val allPaths = findAllPaths(biggerInput, "start")
        expectThat(allPaths.size).isEqualTo(226)
    }

    @Test
    internal fun findAllPaths_withRepeated_small() {
        val allPaths = findAllPaths(smallInput, "start", true)
        expectThat(allPaths.size).isEqualTo(103)
    }

    @Test
    internal fun findAllPaths_withRepeated_bigger() {
        val allPaths = findAllPaths(biggerInput, "start", true)
        expectThat(allPaths.size).isEqualTo(3509)
    }
}