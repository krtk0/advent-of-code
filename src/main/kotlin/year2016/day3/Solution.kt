package year2016.day3

fun convertFromVerticalInput(input: List<List<Int>>): List<List<Int>> {
    val col0 = mutableListOf<Int>()
    val col1 = mutableListOf<Int>()
    val col2 = mutableListOf<Int>()
    for (line in input) {
        col0.add(line[0])
        col1.add(line[1])
        col2.add(line[2])
    }
    return listOf(col0, col1, col2).flatten().chunked(3)
}

fun isTriangle(sides: List<Int>): Boolean =
    sides[0] + sides[1] > sides[2] && sides[0] + sides[2] > sides[1] && sides[2] + sides[1] > sides[0]

fun countTriangles(candidates: List<List<Int>>): Int {
    return candidates
        .map { isTriangle(it) }
        .count { it }
}

fun main() {
    val input = TaskInput.data
    println("task1: %d".format(countTriangles(input)))
    println("task2: %d".format(countTriangles(convertFromVerticalInput(input))))
}