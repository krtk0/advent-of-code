package year2021.day11

import util.InputTools

object TaskInput {
    val data = InputTools().getLinesFromFile("2021_day11.txt")
        .map {
            it.split("")
                .filter(String::isNotEmpty)
                .map(String::toInt)
        }
        .map { it.toTypedArray() }
}