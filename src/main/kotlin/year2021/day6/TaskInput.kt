package year2021.day6

import util.InputTools

object TaskInput {
    val data = InputTools().getLinesFromFile("2021_day6.txt")[0]
        .split(",")
        .map(String::toInt)
}