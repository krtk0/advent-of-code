package year2016.day1

import util.getPathToNextOrthogonal
import java.util.Collections.rotate
import kotlin.math.abs

val startPosition = Pair(0, 0)
val directions = listOf("N", "E", "S", "W")
val turns = mapOf("L" to -1, "R" to 1)
val positionChanges =
    mapOf("N" to Pair(0, 1), "E" to Pair(1, 0), "S" to Pair(0, -1), "W" to Pair(-1, 0))

fun Pair<Int, Int>.positionAfterMove(change: Pair<Int, Int>, distance: Int): Pair<Int, Int> =
    Pair(this.first + distance * change.first, this.second + distance * change.second)

fun Pair<Int, Int>.calculateManhattanDistance(): Int {
    return abs(this.first) + abs(this.second)
}

fun chooseDirection(leftOrRight: String): String {
    rotate(directions, turns[leftOrRight]!!)
    return directions[0]
}

fun calculateEndPosition(): Pair<Int, Int> {
    val navigation = TaskInput.data
    return navigation.fold(startPosition) { pos: Pair<Int, Int>, move: String ->
        val leftOrRight = move[0].toString()
        val distance = move.substring(1).toInt()
        val direction = chooseDirection(leftOrRight)
        val positionChange = positionChanges[direction]!!
        pos.positionAfterMove(positionChange, distance)
    }
}

fun calculateFirstDoubleVisitPosition(): Pair<Int, Int> {
    val navigation = TaskInput.data
    val visited = mutableMapOf(startPosition to true)
    var currentPosition = startPosition
    loop@ for (move in navigation) {
        val leftOrRight = move[0].toString()
        val distance = move.substring(1).toInt()
        val direction = chooseDirection(leftOrRight)
        val positionChange = positionChanges[direction]!!
        val nextPosition = currentPosition.positionAfterMove(positionChange, distance)
        val pathToNextPosition = currentPosition.getPathToNextOrthogonal(nextPosition)
        for (pathPoint in pathToNextPosition) {
            if (visited.getOrDefault(pathPoint, false)) {
                currentPosition = pathPoint
                break@loop
            } else {
                visited[pathPoint] = true
            }
        }
        currentPosition = nextPosition
    }
    return currentPosition
}

fun main() {
    println("task1: %d".format(calculateEndPosition().calculateManhattanDistance()))
    println("task2: %d".format(calculateFirstDoubleVisitPosition().calculateManhattanDistance()))
}