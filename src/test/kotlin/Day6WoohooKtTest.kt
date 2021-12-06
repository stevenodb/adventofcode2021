import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

internal class Day6WoohooKtTest {

    @Test
    internal fun fishWoohoo_scenario() {
        val actual = fishWoohoo(80, hashMapOf(4 to 1, 3 to 2, 1 to 1, 2 to 1))
        expectThat(actual).isEqualTo(5934uL)
    }
}