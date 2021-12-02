import Command.Keyword.*
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

internal class Day02PilotKtTest {
    @Test
    internal fun move_forward() {
        val actual = Command(FORWARD, 3).move(Position(1,1))
        expectThat(actual).isEqualTo(Position(4, 1))
    }

    @Test
    internal fun move_up() {
        val actual = Command(UP, 5).move(Position(1,1))
        expectThat(actual).isEqualTo(Position(1, -4))
    }

    @Test
    internal fun move_down() {
        val actual = Command(DOWN, 2).move(Position(1,1))
        expectThat(actual).isEqualTo(Position(1, 3))
    }

    @Test
    internal fun move_scenario() {
        var position = Position()
        position = Command(FORWARD, 5).move(position)
        position = Command(DOWN, 5).move(position)
        position = Command(FORWARD, 8).move(position)
        position = Command(UP, 3).move(position)
        position = Command(DOWN, 8).move(position)
        position = Command(FORWARD, 2).move(position)
        expectThat(position).isEqualTo(Position(15, 10))
    }

    @Test
    internal fun move_aimposition_horizontal() {
        val actual = Command(FORWARD, 5).move(AimPosition(1,1, 1))
        expectThat(actual).isEqualTo(AimPosition(6, 6, 1))
    }

    @Test
    internal fun move_aimposition_scenario() {
        var position: Position = AimPosition()
        position = Command(FORWARD, 5).move(position)
        position = Command(DOWN, 5).move(position)
        position = Command(FORWARD, 8).move(position)
        position = Command(UP, 3).move(position)
        position = Command(DOWN, 8).move(position)
        position = Command(FORWARD, 2).move(position)
        expectThat(position.horizontal).isEqualTo(15)
        expectThat(position.depth).isEqualTo(60)
    }
}