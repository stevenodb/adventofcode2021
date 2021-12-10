import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

internal class Day09SmokeKtTest {
    @Test
    fun determineRiskLevels_scenario() {
        val heightMap = listOf(
            listOf(2, 1, 9, 9, 9, 4, 3, 2, 1, 0),
            listOf(3, 9, 8, 7, 8, 9, 4, 9, 2, 1),
            listOf(9, 8, 5, 6, 7, 8, 9, 8, 9, 2),
            listOf(8, 7, 6, 7, 8, 9, 6, 7, 8, 9),
            listOf(9, 8, 9, 9, 9, 6, 5, 6, 7, 8)
        )
        val lowPoints = findLowPoints(heightMap)

        val actual = determineRiskLevels(lowPoints.map { (_, height) -> height })
        expectThat(actual).isEqualTo(15)
    }

    @Test
    fun findBasins_scenario() {
        val heightMap = listOf(
            listOf(2, 1, 9, 9, 9, 4, 3, 2, 1, 0),
            listOf(3, 9, 8, 7, 8, 9, 4, 9, 2, 1),
            listOf(9, 8, 5, 6, 7, 8, 9, 8, 9, 2),
            listOf(8, 7, 6, 7, 8, 9, 6, 7, 8, 9),
            listOf(9, 8, 9, 9, 9, 6, 5, 6, 7, 8)
        )
        val lowPoints = findLowPoints(heightMap)

        val actual = determineBasinsProduct(heightMap, lowPoints.map { (point, _) -> point })
        expectThat(actual).isEqualTo(1134)
    }
}