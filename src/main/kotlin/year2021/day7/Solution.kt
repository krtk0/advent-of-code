package year2021.day7

import kotlin.math.abs

fun calculateFuelIncremental(steps: Int): Int {
    return (steps * (1 + steps)) / 2
}

fun findMinFuelLinear(input: List<Int>): Int {
    val positionCandidates = (input.minOrNull()!!..input.maxOrNull()!!).toList()
    return positionCandidates.minOf { position -> input.sumOf { abs(it - position) } }
}

fun findMidFuelIncremental(input: List<Int>): Int {
    val positionCandidates = (input.minOrNull()!!..input.maxOrNull()!!).toList()
    return positionCandidates.minOf { position ->
        input.sumOf { calculateFuelIncremental(abs(it - position)) }
    }
}

fun main() {
    println("part1: ${findMinFuelLinear(TaskInput.data)}")
    println("part2: ${findMidFuelIncremental(TaskInput.data)}")
}