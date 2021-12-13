import Paper.Fold.Axis.*

fun main() {
    val lines = readInput("Day13")
    val (dots, folds) = parseFoldInput(lines)
    println("Part 1: ${foldPaper(dots, folds.subList(0,1)).dots.count()}")
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
            f_dots: List<Point>,
            f_point: Point,
            width: Int,
            height: Int,
            f_getAxisValue: (Point) -> Int,
            f_createPoint: (Point, width: Int, height: Int) -> Point
        ): Point? =
            if (f_getAxisValue(f_point) > fold.value) {
                val newPoint = f_createPoint(f_point, width, height)
                if (f_dots.contains(newPoint)) null else newPoint
            } else f_point

        val foldedDots = dots.asSequence()
            .map { point ->
                when (fold.axis) {
                    X -> foldFunction(dots, point, width, height, { it.x }, { pnt, width, _ -> Point(width - pnt.x, pnt.y) })
                    Y -> foldFunction(dots, point, width, height, { it.y }, { pnt, _, height -> Point(pnt.x, height - pnt.y) })
                }
            }.filterNotNull()
            .toList()

//        print("$width, $height --> ")

        val (newWidth, newHeight) = when (fold.axis) {
            X -> fold.value to height
            Y -> width to fold.value
        }

//        println("$newWidth, $newHeight")

        return Paper(foldedDots, newWidth, newHeight)
    }

    fun toVisualString(): String {
        println(dots.filter { it.x < 0 || it.y < 0 })
//        val paper = MutableList(height+1) { MutableList(width+1) { "." } }
//        dots.forEach { (x, y) -> paper[y][x] = "#" }
//        return paper.joinToString("") { it.joinToString("", postfix = "\n") }

        return List(height+1) { y ->
            List(width+1) { x ->
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