package year2021.day9


fun part1(input: List<String>): Int {
    val lowPoints = mutableListOf<Int>()
    for (j in 0 until 100) {
        for (i in 0 until 100) {
            if (isPointLow(i, j, input)) {
                lowPoints.add(input[j][i].code - 48)
            }
        }
    }
    return lowPoints.sumOf { it + 1 }
}

fun isPointLow(i: Int, j: Int, input: List<String>): Boolean {
    return isNeighborHigher(i, j - 1, input, input[j][i].code - 48)
            && isNeighborHigher(i, j + 1, input, input[j][i].code - 48)
            && isNeighborHigher(i - 1, j, input, input[j][i].code - 48)
            && isNeighborHigher(i + 1, j, input, input[j][i].code - 48)
}

fun isNeighborHigher(i: Int, j: Int, input: List<String>, lowCandidate: Int): Boolean {
    return i !in 0 until 100 || j !in 0 until 100 || input[j][i].code - 48 > lowCandidate
}

fun part2(input: List<String>): Int {
    val basins = mutableListOf<Int>()
    for (j in 0 until 100) {
        for (i in 0 until 100) {
            if (isPointLow(i, j, input)) {
                val stack = mutableListOf(Pair(i, j))
                val visited = mutableMapOf(Pair(i, j) to true)
                var basinSize = 1
                while (stack.isNotEmpty()) {
                    var right = 1
                    val point = stack.removeFirst()
                    while ((point.first + right < 100) && (Pair(
                            point.first + right,
                            point.second
                        ) !in visited) && ((input[point.second][point.first + right].code - 48) != 9)
                    ) {
                        basinSize++
                        stack.add(Pair(point.first + right, point.second))
                        visited[Pair(point.first + right, point.second)] = true
                        right++
                    }
                    var left = 1
                    while ((point.first - left >= 0) && (Pair(
                            point.first - left,
                            point.second
                        ) !in visited) && ((input[point.second][point.first - left].code - 48) != 9)
                    ) {
                        basinSize++
                        stack.add(Pair(point.first - left, point.second))
                        visited[Pair(point.first - left, point.second)] = true
                        left++
                    }
                    var up = 1
                    while ((point.second - up >= 0) && (Pair(
                            point.first,
                            point.second - up
                        ) !in visited) && ((input[point.second - up][point.first].code - 48) != 9)
                    ) {
                        basinSize++
                        stack.add(Pair(point.first, point.second - up))
                        visited[Pair(point.first, point.second - up)] = true
                        up++
                    }
                    var down = 1
                    while ((point.second + down < 100) && (Pair(
                            point.first,
                            point.second + down
                        ) !in visited) && ((input[point.second + down][point.first].code - 48) != 9)
                    ) {
                        basinSize++
                        stack.add(Pair(point.first, point.second + down))
                        visited[Pair(point.first, point.second + down)] = true
                        down++
                    }
                }
                basins.add(basinSize)
            }
        }
    }
    return basins.sortedDescending().subList(0, 3).reduce { a, b -> a * b }
}


fun main() {
    println("part1: ${part1(TaskInput.data)}")
    println("part2: ${part2(TaskInput.data)}")
}