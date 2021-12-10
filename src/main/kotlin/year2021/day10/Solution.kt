package year2021.day10

val openToClose = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')

fun part1(input: List<String>): Int {
    val closeToScore = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
    var errorScore = 0
    for (line in input) {
        val closingStack = mutableListOf<Char>()
        line@ for (char in line) {
            if (char in openToClose) {
                closingStack.add(openToClose[char]!!)
            } else {
                val expectedClosing = closingStack.removeLastOrNull()
                if (char != expectedClosing) {
                    errorScore += closeToScore[char]!!
                    break@line
                }
            }
        }
    }
    return errorScore
}

fun part2(input: List<String>): Long {
    val completeToScore = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)
    val incompleteInput = getIncomplete(input)
    val scores = mutableListOf<Long>()
    for (line in incompleteInput) {
        var completionScore = 0L
        val closingStack = mutableListOf<Char>()
        line@ for (char in line) {
            if (char in openToClose) {
                closingStack.add(openToClose[char]!!)
            } else {
                closingStack.removeLastOrNull()
            }
        }
        closingStack.reversed().forEach {
            completionScore = (completionScore * 5) + completeToScore[it]!!
        }
        scores.add(completionScore)
    }
    return scores.sorted()[scores.size / 2]
}

private fun getIncomplete(input: List<String>): List<String> {
    val corruptedIdxs = mutableListOf<Int>()
    for (lineIdx in input.indices) {
        val closingStack = mutableListOf<Char>()
        line@ for (char in input[lineIdx]) {
            if (char in openToClose) {
                closingStack.add(openToClose[char]!!)
            } else {
                val expectedClosing = closingStack.removeLastOrNull()
                if (char != expectedClosing) {
                    corruptedIdxs.add(lineIdx)
                    break@line
                }
            }
        }
    }
    return input.filterIndexed { index, _ -> index !in corruptedIdxs }
}


fun main() {
    println("part1: ${part1(TaskInput.data)}")
    println("part2: ${part2(TaskInput.data)}")
}