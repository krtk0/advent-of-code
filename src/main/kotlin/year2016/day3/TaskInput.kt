package year2016.day3

import year2016.util.InputTools

object TaskInput {
    val data = InputTools().getLinesFromFile("day3.txt")
        .map {
            it.trim()
            it.split(" +".toRegex())
        }
        .map {
            it.filter { side ->
                side.isNotEmpty()
            }
        }
        .map {
            it.map { side ->
                side.toInt()
            }
        }
}