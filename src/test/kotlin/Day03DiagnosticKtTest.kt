import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

internal class Day03DiagnosticKtTest {

    private var input = listOf(
        "00100",
        "11110",
        "10110",
        "10111",
        "10101",
        "01111",
        "00111",
        "11100",
        "10000",
        "11001",
        "00010",
        "01010"
    )

    @Test
    internal fun reduceToCommonBits_scenario() {
        val actual = reduceToCommonBits(input)
        expectThat(actual).isEqualTo(listOf(1, 0, 1, 1, 0))
    }

    @Test
    internal fun reduceToValueByBitCommonality_scenario_mostCommon() {
        val actual = reduceToValueByBitCommonality(input, mostCommon = true)
        expectThat(actual).isEqualTo("10111")
    }

    @Test
    internal fun reduceToValueByBitCommonality_scenario_leastCommon() {
        val actual = reduceToValueByBitCommonality(input, mostCommon = false)
        expectThat(actual).isEqualTo("01010")
    }
}