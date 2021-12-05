package year2021.day4

import util.InputTools

object TaskInput {
    private val data = InputTools().getLinesFromFile("2021_day4.txt")

    val draw = data[0].split(",").map { it.toInt() }

    val boards = data.subList(2, data.size)
        .filter { it != "" }
        .chunked(5)
        .map { board ->
            board.map {
                it.split(" ")
                    .filter { number -> number !in listOf("", " ") }
                    .map { number -> Pair(number.toInt(), false) }.toMutableList()
            }
        }
}