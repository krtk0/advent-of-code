package year2021.day8

fun part1(input: List<String>): Int {
    return input.sumOf { line ->
        line.split(" | ")[1]
            .split(" ")
            .count { it.length in listOf(2, 4, 3, 7) }
    }
}

fun part2(input: List<String>): Int {
    return input
        .map { line ->
            line.split(" | ").map { it.split(" ") }
        }
        .sumOf { decodeNums(it) }
}

/**
 * 3 ~ X_of_len_5 - (8 - 1) = 1
 *
 * 9 ~ 3 + 4
 *
 * 0 ~ (8 - 3) + (9 - 4) + 1
 *
 * 6 ~ X_of_len_6 that is not 9
 *
 * 5 ~ 6 - (8 - 9)
 *
 * 2 ~ X_of_len_5 that is not 5
 */
fun decodeNums(mixedSignals: List<List<String>>): Int {
    val signals = mixedSignals[0].sortedByDescending { it.length }
    val numberToSignal = List(10) { "" }.toMutableList()
    val signalToNumber = mutableMapOf<String, Int>()
    signals.forEach {
        signalToNumber[sortSignal(it)] = when (it.length) {
            7 -> 8
            3 -> 7
            4 -> 4
            2 -> 1
            else -> -1
        }
    }
    signalToNumber.forEach { (signal, number) ->
        if (number != -1) {
            numberToSignal[number] = signal
        }
    }
    //find 3
    val candidates3 = signalToNumber.keys.filter { it.length == 5 }
    loop3@ for (potential3 in candidates3) {
        if (potential3 - (numberToSignal[8] - numberToSignal[1]) == numberToSignal[1]) {
            signalToNumber[potential3] = 3
            numberToSignal[3] = potential3
            break@loop3
        }
    }
    //find 9
    val signal9 = numberToSignal[3].plusNew(numberToSignal[4])
    signalToNumber[signal9] = 9
    numberToSignal[9] = signal9
    //find 0
    val signal0 =
        ((numberToSignal[8] - numberToSignal[3]).plusNew(signal9 - numberToSignal[4])).plusNew(
            numberToSignal[1]
        )
    signalToNumber[signal0] = 0
    numberToSignal[0] = signal0
    //find 6
    val signal6 =
        signalToNumber.keys.first { it.length == 6 && signalToNumber[it] !in listOf(0, 9) }
    signalToNumber[signal6] = 6
    numberToSignal[6] = signal6
    //find 5
    val signal5 = signal6 - (numberToSignal[8] - numberToSignal[9])
    signalToNumber[signal5] = 5
    numberToSignal[5] = signal5
    //find 2
    val signal2 = signalToNumber.keys.first { signalToNumber[it] == -1 }
    signalToNumber[signal2] = 2
    numberToSignal[2] = signal2
    return mixedSignals[1].map { signalToNumber[sortSignal(it)]!! }.joinToString("").toInt()
}

operator fun String.minus(subtractor: String): String =
    this.toCharArray().filter { it !in subtractor.toCharArray() }.joinToString("")

fun String.plusNew(addition: String): String =
    (this.toCharArray() + addition.toCharArray()).toSet().sorted().joinToString("")

fun sortSignal(signal: String): String {
    return signal.toCharArray().sorted().joinToString("")
}

fun main() {
    println("part1: ${part1(TaskInput.data)}")
    println("part2: ${part2(TaskInput.data)}")
}