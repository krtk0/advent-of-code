package year2021.day2

import util.fetchNumber

fun driveSubmarine1(input: List<String>): Int {
    var depth = 0
    var distance = 0
    input.forEach {
        val amount = it.fetchNumber()
        when(it.substring(0, 2)) {
            "up" -> depth -= amount
            "do" -> depth += amount
            "fo" -> distance += amount
        }
    }
    return depth * distance
}

fun driveSubmarine2(input: List<String>): Int {
    var depth = 0
    var distance = 0
    var aim = 0
    input.forEach {
        val amount = it.fetchNumber()
        when(it.substring(0, 2)) {
            "up" -> aim -= amount
            "do" -> aim += amount
            "fo" -> {
                distance += amount
                depth += aim * amount
            }
        }
    }
    return depth * distance
}

fun main() {
    val input = TaskInput.data
    println("part1: ${driveSubmarine1(input)}")
    println("part2: ${driveSubmarine2(input)}")
}