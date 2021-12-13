import org.junit.jupiter.api.Test

internal class Day13FoldingKtTest {
    @Test
    fun fold_scenario() {
        val inputLines = """
        6,10
        0,14
        9,10
        0,3
        10,4
        4,11
        6,0
        6,12
        4,1
        0,13
        10,12
        3,4
        3,0
        8,4
        1,10
        2,14
        8,10
        9,0

        fold along y=7
        fold along x=5
        """.trimIndent().split("\n")

        val (points, folds) = parseFoldInput(inputLines)
        val paper = foldPaper(points, folds.subList(0,2))
        println(paper.toVisualString())
    }
}