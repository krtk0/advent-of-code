package year2021.day7

import util.InputTools

object TaskInput {
    val data = InputTools().getLinesFromFile("2021_day7.txt")[0]
        .split(",")
        .map(String::toInt)
}