import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

internal class Day11OctopussyKtTest {
    private val input = listOf(
        "5483143223",
        "2745854711",
        "5264556173",
        "6141336146",
        "6357385478",
        "4167524645",
        "2176841721",
        "6882881134",
        "4846848554",
        "5283751526"
    )

    @Test
    fun flashCount() {
        expectThat(flashCountAfter100Steps(input)).isEqualTo(1656)
    }

    @Test
    fun numberOfStepsToSynchroFlash() {
        expectThat(numberOfStepsToSynchroFlash(input)).isEqualTo(195)
    }
}