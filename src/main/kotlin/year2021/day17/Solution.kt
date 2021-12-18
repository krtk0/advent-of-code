package year2021.day17

data class Velocity(val x: Int, val y: Int)

fun solve(targetArea: TaskInput) {
    val maxHeightVelocityPairs = mutableListOf<Pair<Int, Velocity>>()
    for (x in 1..targetArea.xRange.last) {
        for (y in targetArea.yRange.first..200) {
            var i = x
            var j = y
            var maxY = y
            var decrement = 1
            launch@ while (i <= targetArea.xRange.last && j >= targetArea.yRange.first) {
                if (j > maxY) {
                    maxY = j
                }
                if (i in targetArea.xRange && j in targetArea.yRange) {
                    maxHeightVelocityPairs.add(Pair(maxY, Velocity(x, y)))
                    break@launch
                }
                if ((x - decrement) > 0) {
                    i += x - decrement
                }
                j += y - decrement
                decrement++
            }
        }
    }
    println("part1: ${maxHeightVelocityPairs.maxOf { it.first }}")
    println("part2: ${maxHeightVelocityPairs.map { it.second }.toSet().size}")
}

fun main() {
    solve(TaskInput)
}