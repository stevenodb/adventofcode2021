import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

internal class Day08SegmentKtTest {

    /*
     dddd
    e    a
    e    a
     ffff
    g    b
    g    b
     cccc
     */
    @Test
    fun solve() {
        val input = listOf(
            "acedgfb",
            "cdfbe",
            "gcdfa",
            "fbcad",
            "dab",
            "cefabd",
            "cdfgeb",
            "eafb",
            "cagedb",
            "ab",
            "|",
            "cdfeb",
            "fcadb",
            "cdfeb",
            "cdbaf"
        )
        val actual = solveDigits(input.subList(0, 10).toSet())
        expectThat(actual).isEqualTo(
            mapOf('d' to 'a', 'e' to 'b', 'a' to 'c', 'f' to 'd', 'g' to 'e', 'b' to 'f', 'c' to 'g'))
    }

    @Test
    fun transform() {
        TODO("Not yet implemented")
    }
}