import Command.Keyword.valueOf

open class Position(val horizontal: Int = 0, val depth: Int = 0) {
    open fun forward(units: Int): Position {
        return Position(horizontal + units, depth)
    }

    open fun down(units: Int): Position {
        return Position(horizontal, depth + units)
    }

    open fun up(units: Int): Position {
        return down(-units)
    }


    override fun toString(): String {
        return "Position(horizontal=$horizontal, depth=$depth)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Position) return false

        if (horizontal != other.horizontal) return false
        if (depth != other.depth) return false

        return true
    }

    override fun hashCode(): Int {
        var result = horizontal
        result = 31 * result + depth
        return result
    }
}

class AimPosition(horizontal: Int = 0, depth: Int = 0, val aim: Int = 0) : Position(horizontal, depth) {

    override fun forward(units: Int): AimPosition {
        return AimPosition(horizontal + units, depth + (aim * units), aim)
    }

    override fun down(units: Int): AimPosition {
        return AimPosition(horizontal, depth, aim + units)
    }

    override fun up(units: Int): AimPosition {
        return down(-units)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as AimPosition

        if (aim != other.aim) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + aim
        return result
    }

    override fun toString(): String {
        return "AimPosition(horizontal=$horizontal, depth=$depth, aim=$aim)"
    }
}


class Command(private val keyword: Keyword, private val units: Int) {

    enum class Keyword(val operation: (Position, Int) -> Position) {
        FORWARD(Position::forward), UP(Position::up), DOWN(Position::down);
    }

    fun move(position: Position): Position {
        return this.keyword.operation.invoke(position, this.units)
    }

    override fun toString(): String {
        return "Command(keyword=$keyword, units=$units)"
    }
}

private fun partOne(input: List<Command>) {
    var position = Position()
    input.forEach { position = it.move(position) }
    println("Part1: $position == ${position.depth * position.horizontal}")
}

private fun partTwo(input: List<Command>) {
    var position: Position = AimPosition()
    input.forEach { position = it.move(position) }
    println("Part2: $position == ${position.depth * position.horizontal}")
}

fun main() {
    val input = readInput("Day02").map {
        val split = it.split(' ') //^(\w+)\s(\d+)$
        Command(valueOf(split[0].uppercase()), split[1].toInt())
    }

    partOne(input)
    partTwo(input)
}