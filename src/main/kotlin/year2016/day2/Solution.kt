package year2016.day2

//var currentPosition = Pair(1, 1)
//val keyboard = listOf(
//    listOf("7", "8", "9"),
//    listOf("4", "5", "6"),
//    listOf("1", "2", "3")
//)
var currentPosition = Pair(2, 0)
val keyboard = listOf(
    listOf("", "", "D", "", ""),
    listOf("", "A", "B", "C", ""),
    listOf("5", "6", "7", "8", "9"),
    listOf("", "2", "3", "4", ""),
    listOf("", "", "1", "", "")
)
val positionChanges =
    mapOf("U" to Pair(0, 1), "R" to Pair(1, 0), "D" to Pair(0, -1), "L" to Pair(-1, 0))

fun Pair<Int, Int>.positionAfterMove(change: Pair<Int, Int>, distance: Int = 1): Pair<Int, Int> =
    Pair(this.first + distance * change.first, this.second + distance * change.second)

fun Pair<Int,Int>.isValidPosition(): Boolean {
    return try {
        keyboard[this.second][this.first] != ""
    } catch (e: IndexOutOfBoundsException) {
        false
    }
}

fun getNextDigit(instructions: String): String {
    for (instruction in instructions) {
        val potentialNextPosition =
            currentPosition.positionAfterMove(positionChanges[instruction.toString()]!!)
        if (potentialNextPosition.isValidPosition()) {
            currentPosition = potentialNextPosition
        }
    }
    return keyboard[currentPosition.second][currentPosition.first]
}

fun getCode(input: List<String>): String {
    return input.joinToString("") { getNextDigit(it) }
}

fun main() {
    val taskInput = TaskInput.data
    println(getCode(taskInput))
}