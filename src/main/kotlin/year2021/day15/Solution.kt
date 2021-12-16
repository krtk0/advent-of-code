package year2021.day15

import java.util.*
import kotlin.math.sqrt

data class Point(val x: Int, val y: Int)

data class Node(val location: Point, val cost: Int)

/**
 * A-star search.
 */
fun part1(input: List<List<Int>>): Int {
    val cameFrom = mutableMapOf<Node, Node>()
    val start = Node(Point(0, 0), input[0][0])
    val gScore = mutableMapOf<Node, Int>().withDefault { 100000 }
    gScore[start] = 0
    val fScore = mutableMapOf<Node, Int>().withDefault { 100000 }
    fScore[start] = start.heuristic(input)
    val priorityQueue = PriorityQueue { n1: Node, n2: Node -> fScore[n1]!! - fScore[n2]!! }
    priorityQueue.add(start)
    while (priorityQueue.isNotEmpty()) {
        val current = priorityQueue.poll()
        if (current.location == Point(input.first().lastIndex, input.lastIndex)) {
            return gScore[current]!!
        }
        getNeighbors(current, input).forEach {
            val tentativeG = gScore.getValue(current) + it.cost
            if (tentativeG < gScore.getValue(it)) {
                cameFrom[it] = current
                gScore[it] = tentativeG
                fScore[it] = tentativeG + it.heuristic(input)
                if (it !in priorityQueue) {
                    priorityQueue.add(it)
                }
            }
        }
    }
    return -1
}

/**
 * Heuristic function for A-star as
 * a sum of integer diagonal distance to the target and node visit cost.
 */
private fun Node.heuristic(input: List<List<Int>>): Int {
    val yDistance = input.lastIndex - location.y
    val xDistance = input.first().lastIndex - location.x
    return sqrt(((xDistance * xDistance) + (yDistance * yDistance)).toFloat()).toInt() + cost
}

private fun getNeighbors(current: Node, input: List<List<Int>>): List<Node> {
    val neighbors = mutableListOf<Node>()
    addNeighbor(neighbors, current.location.x + 1, current.location.y, input)
    addNeighbor(neighbors, current.location.x - 1, current.location.y, input)
    addNeighbor(neighbors, current.location.x, current.location.y + 1, input)
    addNeighbor(neighbors, current.location.x, current.location.y - 1, input)
    return neighbors
}

private fun addNeighbor(neighbors: MutableList<Node>, x: Int, y: Int, input: List<List<Int>>) {
    if (x in 0..input.first().lastIndex && y in 0..input.lastIndex) {
        neighbors.add(
            Node(
                Point(x, y),
                input[y][x]
            )
        )
    }
}

fun part2(input: List<List<Int>>): Int {
    return part1(getFullMap(input))
}

private fun getFullMap(input: List<List<Int>>): List<List<Int>> {
    val fullChitonMap = input.map { getFullLine(it) }.toMutableList() // build the full top block
    var nextDownBlock = fullChitonMap.map { it.toMutableList() }
    repeat(4) { // expand the top block downward
        nextDownBlock = nextDownBlock.map { row ->
            row.map {
                when (it + 1) {
                    10 -> 1
                    else -> it + 1
                }
            }.toMutableList()
        }
        fullChitonMap += nextDownBlock
    }
    return fullChitonMap
}

private fun getFullLine(line: List<Int>): List<Int> {
    val fullLine = line.toMutableList()
    val nextLine = line.toMutableList()
    repeat(4) {
        for (x in nextLine.indices) {
            nextLine[x] += 1
            if (nextLine[x] == 10) {
                nextLine[x] = 1
            }
        }
        fullLine += nextLine
    }
    return fullLine
}

fun main() {
    println("part1: ${part1(TaskInput.data)}")
    println("part2: ${part2(TaskInput.data)}")
}