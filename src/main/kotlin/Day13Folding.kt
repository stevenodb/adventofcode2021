import Paper.Fold.Axis.*

fun main() {
    val lines = readInput("Day13")
    val (dots, folds) = parseFoldInput(lines)
    println("Part 1: ${foldPaper(dots, folds.subList(0, 1)).dots.count()}")
    println("Part 2: \n${foldPaper(dots, folds).toVisualString()}")
}

fun foldPaper(dots: List<Point>, folds: List<Paper.Fold>): Paper {
    val width = dots.maxOfOrNull { it.x } ?: 0
    val height = dots.maxOfOrNull { it.y } ?: 0

    var paper = Paper(dots, width, height)
    folds.forEach { fold ->
        paper = paper.fold(fold)
    }
    return paper
}

internal fun parseFoldInput(lines: List<String>): Pair<List<Point>, List<Paper.Fold>> {
    val separatorIndex = lines.indexOf("")
    val dots = lines.subList(0, separatorIndex).map { it.split(",") }.map { Point(it[0].toInt(), it[1].toInt()) }
    val folds = lines.subList(separatorIndex, lines.size)
        .map { line ->
            line.split("fold along ", "=")
                .filterNot { it.isEmpty() }
        }
        .filterNot { it.isEmpty() }
        .map {
            Paper.Fold(valueOf(it[0].trim().uppercase()), it[1].toInt())
        }
    println("Parsed ${dots.size} dots, ${folds.size} folds")
    return Pair(dots, folds)
}

class Paper(val dots: List<Point>, private val width: Int, private val height: Int) {

    fun fold(fold: Fold): Paper {

        fun foldFunction(
            f_point: Point,
            getAxisValue: (Point) -> Int,
            createPoint: (Point) -> Point
        ): Point? =
            when {
                getAxisValue(f_point) > fold.value -> {
                    val newPoint = createPoint(f_point)
                    if (dots.contains(newPoint)) null else newPoint
                }
                else -> f_point
            }

        val foldedDots = dots.asSequence()
            .map { point ->
                when (fold.axis) {
                    X -> foldFunction(point, { it.x }, { pnt -> Point(fold.value - (pnt.x - fold.value), pnt.y) })
                    Y -> foldFunction(point, { it.y }, { pnt -> Point(pnt.x, fold.value - (pnt.y - fold.value)) })
                }
            }.filterNotNull()
            .toList()

        val (newWidth, newHeight) = when (fold.axis) {
            X -> fold.value to height
            Y -> width to fold.value
        }

        return Paper(foldedDots, newWidth, newHeight)
    }

    fun toVisualString(): String {
        return List(height) { y ->
            List(width) { x ->
                if (dots.contains(Point(x, y))) "#" else "."
            }
        }
            .joinToString("") { it.joinToString("", postfix = "\n") }
    }

    data class Fold(val axis: Axis, val value: Int) {
        enum class Axis {
            X, Y;
        }
    }
}