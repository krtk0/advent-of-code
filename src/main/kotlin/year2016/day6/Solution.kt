package year2016.day6

import year2016.util.CharFrequencyComparator

fun prepareInput(rawInput: List<String>): List<String> {
    val vertical = mutableListOf<String>()
    for (i in 0 until rawInput[0].length) {
        val column = mutableListOf<Char>()
        for (line in rawInput) {
            column.add(line[i])
        }
        vertical.add(column.joinToString(""))
    }
    return vertical
}

fun solve(input: List<String>, pickFunction: (List<Pair<Char, Int>>) -> Pair<Char, Int>): String {
    val result = mutableListOf<Char>()
    for (line in input) {
        result.add(
            pickFunction(CharFrequencyComparator.getCharFrequencyPairsSorted(line))
                .first
        )
    }
    return result.joinToString("")
}

fun solve1(input: List<String>): String {
    return solve(input) { a: List<Pair<Char, Int>> -> a.first() }
}

fun solve2(input: List<String>): String {
    return solve(input) { a: List<Pair<Char, Int>> -> a.last() }
}

fun main() {
    val input = prepareInput(TaskInput.data)
    println(solve1(input))
    println(solve2(input))
}