package year2021.day5

import util.InputTools

object TaskInput {
    val data = InputTools().getLinesFromFile("2021_day5.txt")
        .map { lineCoordinatesString ->
            lineCoordinatesString.split(" -> ")
                .map {
                    val lineCoordinatesList = it.split(",")
                    Pair(lineCoordinatesList[0].toInt(), lineCoordinatesList[1].toInt())
                }
        }
        .map { ThermalLine(it[0], it[1]) }
}