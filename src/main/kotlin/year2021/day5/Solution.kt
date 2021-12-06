package year2021.day5

import util.getPathToNextDiagonal
import util.getPathToNextOrthogonal

data class ThermalLine(val start: Pair<Int, Int>, val end: Pair<Int, Int>)

fun ThermalLine.isOrthogonal(): Boolean =
    this.start.first == this.end.first || this.start.second == this.end.second

fun countDangerousPoints(thermalLinesMap: Map<Pair<Int, Int>, Int>) =
    thermalLinesMap.filter { it.value > 1 }.keys.size

fun getThermalLinePoints(line: ThermalLine, onlyOrthogonal: Boolean): List<Pair<Int, Int>> {
    return when (line.isOrthogonal()) {
        true -> {
            val points = line.start.getPathToNextOrthogonal(line.end).toMutableList()
            points.add(line.start)
            points
        }
        false -> {
            when (onlyOrthogonal) {
                false -> {
                    line.start.getPathToNextDiagonal(line.end)
                }
                true -> {
                    emptyList()
                }
            }
        }
    }
}

fun countOverlapPoints(input: List<ThermalLine>, onlyOrthogonal: Boolean = false): Int {
    val visited = mutableMapOf<Pair<Int, Int>, Int>()
    for (line in input) {
        val linePoints = getThermalLinePoints(line, onlyOrthogonal)
        for (linePoint in linePoints) {
            if (linePoint in visited) {
                visited[linePoint] = visited[linePoint]!! + 1
            } else {
                visited[linePoint] = 1
            }
        }
    }
    return countDangerousPoints(visited)
}

fun main() {
    println("part1: ${countOverlapPoints(TaskInput.data, onlyOrthogonal = true)}")
    println("part2: ${countOverlapPoints(TaskInput.data, onlyOrthogonal = false)}")
}
