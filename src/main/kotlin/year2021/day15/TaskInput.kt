package year2021.day15

import util.InputTools

object TaskInput {
    val data = InputTools().getLinesFromFile("2021_day15.txt")
        .map {
            it
                .split("").filter { it != "" }
                .map { cost -> cost.toInt() }
        }
}