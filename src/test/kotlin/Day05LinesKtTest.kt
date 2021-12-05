import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

internal class Day05LinesKtTest {

    @Test
    internal fun field_countOverlapping_scenario() {
        val input = listOf(
            "0,9 -> 5,9",
            "8,0 -> 0,8",
            "9,4 -> 3,4",
            "2,2 -> 2,1",
            "7,0 -> 7,4",
            "6,4 -> 2,0",
            "0,9 -> 2,9",
            "3,4 -> 1,4",
            "0,0 -> 8,8",
            "5,5 -> 8,2"
        )

        val field = parseField(input, 10, includeDiagonal = false)
        val actual = field.countOverlapping()
        expectThat(actual).isEqualTo(5)
        print(field)
    }
}