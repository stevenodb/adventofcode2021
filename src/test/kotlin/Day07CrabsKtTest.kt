import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

internal class Day07CrabsKtTest {

    @Test
    internal fun optimalFuelPart1_scenario() {
        val input = listOf(16, 1, 2, 0, 4, 2, 7, 1, 2, 14)
        val (_, fuel) = optimalFuelPart1(input)!!
        expectThat(fuel).isEqualTo(37)
    }

    @Test
    internal fun optimalFuelPart2_scenario() {
        val input = listOf(16, 1, 2, 0, 4, 2, 7, 1, 2, 14)
        val (_, fuel) = optimalFuelPart2(input)!!
        expectThat(fuel).isEqualTo(168)
    }
}