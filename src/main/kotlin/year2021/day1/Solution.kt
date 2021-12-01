package year2021.day1

fun countDepthIncrease(input: List<Int>): Int {
    var numberOfIncreases = 0
    for (i in 1 until input.size) {
        if (input[i] > input[i - 1]) {
            numberOfIncreases++
        }
    }
    return numberOfIncreases
}

fun List<Int>.toSlidingWindow3Sum(): List<Int> {
    val window3Sums = mutableListOf<Int>()
    for (i in 2 until this.size) {
        window3Sums.add(this[i] + this[i - 1] + this[i - 2])
    }
    return window3Sums
}

fun main() {
    val input = TaskInput.data.map { it.toInt() }
    println(countDepthIncrease(input))
    println(countDepthIncrease(input.toSlidingWindow3Sum()))
}