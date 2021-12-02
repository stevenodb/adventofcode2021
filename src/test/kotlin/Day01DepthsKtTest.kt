import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

internal class Day01DepthsKtTest {
    @Test
    internal fun countIncreases_increasingValues() {
        val actual = countIncreases(listOf(1, 2, 3, 4))
        expectThat(actual)
            .isEqualTo(3)
    }

    @Test
    internal fun countIncreases_equalValue() {
        val actual = countIncreases(listOf(1, 2, 2, 4))
        expectThat(actual)
            .isEqualTo(2)
    }

    @Test
    internal fun countIncreases_decreasingValue() {
        val actual = countIncreases(listOf(1, 2, 1, 4))
        expectThat(actual)
            .isEqualTo(2)
    }

    @Test
    internal fun countIncreasesWindowed_increasingWindows() {
        val actual = countIncreasesWindowed(listOf(1, 2, 3, 4))
        expectThat(actual).isEqualTo(1)
    }

    @Test
    internal fun countIncreasesWindowed_decreasingWindows() {
        val actual = countIncreasesWindowed(listOf(4, 3, 2, 1))
        expectThat(actual).isEqualTo(0)
    }

    @Test
    internal fun countIncreasesWindowed_decreasingValues() {
        val actual = countIncreasesWindowed(listOf(1, 2, 1, 3))
        expectThat(actual).isEqualTo(1)
    }
}