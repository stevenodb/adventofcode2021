import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

internal class Day14PolymerKtTest {
    @Test
    fun polymer_scenario() {
        val input = """
            NNCB
            
            CH -> B
            HH -> N
            CB -> H
            NH -> C
            HB -> C
            HC -> B
            HN -> C
            NN -> C
            BH -> H
            NC -> B
            NB -> B
            BN -> B
            BB -> N
            BC -> B
            CC -> N
            CN -> C
            """.trimIndent().split("\n")

        val (polymer, insertPatterns) = parsePolymerInput(input)
        expectThat(polymerInsertion(polymer, insertPatterns, 10).lowHigh().let { it.second - it.first }).isEqualTo(1588uL)
    }
}