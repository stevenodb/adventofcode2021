import Command.Keyword.valueOf

fun main() {
    val input = readInput("Day02").map {
        val split = it.split(' ') //^(\w+)\s(\d+)$
        Command(valueOf(split[0].uppercase()), split[1].toInt())
    }

    val pos1 = commandSub(input, Position())
    println("Part1: $pos1 == ${pos1.depth * pos1.horizontal}")

    val pos2 = commandSub(input, AimPosition())
    println("Part2: $pos2 == ${pos2.depth * pos2.horizontal}")
}

private fun commandSub(input: List<Command>, start: Position): Position {
    var position = start
    input.forEach { position = it.move(position) }
    return position
}

open class Position(val horizontal: Int = 0, val depth: Int = 0) {
    open fun forward(units: Int): Position = Position(horizontal + units, depth)
    open fun down(units: Int): Position = Position(horizontal, depth + units)
    open fun up(units: Int): Position = down(-units)
    override fun toString(): String = "Position(horizontal=$horizontal, depth=$depth)"
}

class AimPosition(horizontal: Int = 0, depth: Int = 0, val aim: Int = 0) : Position(horizontal, depth) {
    override fun forward(units: Int): AimPosition = AimPosition(horizontal + units, depth + (aim * units), aim)
    override fun down(units: Int): AimPosition = AimPosition(horizontal, depth, aim + units)
    override fun up(units: Int): AimPosition = down(-units)
}

class Command(private val keyword: Keyword, private val units: Int) {

    enum class Keyword(val operation: (Position, Int) -> Position) {
        FORWARD(Position::forward), UP(Position::up), DOWN(Position::down);
    }

    fun move(position: Position): Position = this.keyword.operation.invoke(position, this.units)
}