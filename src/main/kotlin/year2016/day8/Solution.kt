package year2016.day8

import year2016.util.fetchOccurrence
import java.util.*

val screen = mutableListOf(
    MutableList(50) { false },
    MutableList(50) { false },
    MutableList(50) { false },
    MutableList(50) { false },
    MutableList(50) { false },
    MutableList(50) { false },
)

fun rect(a: Int, b: Int) {
    for (x in 0 until a) {
        for (y in 0 until b) {
            screen[y][x] = true
        }
    }
}

fun rotateRow(y: Int, shift: Int) {
    Collections.rotate(screen[y], shift)
}

fun rotateColumn(x: Int, shift: Int) {
    val column = mutableListOf<Boolean>()
    for (row in screen) {
        column.add(row[x])
    }
    Collections.rotate(column, shift)
    for (row in screen) {
        row[x] = column.removeFirst()
    }
}

fun countPixels(): Int {
    return screen.flatten().count { it }
}

fun swipeCard(commands: List<String>) {
    for (command in commands) {
        if (command.substring(0, 4) == "rect") {
            val dimensions = command.substring(5).split("x").map { it.toInt() }
            rect(dimensions[0], dimensions[1])
        } else {
            val shift = command.fetchOccurrence("by \\d+".toRegex()).substring(3).toInt()
            if (command[12] == '=') {
                val y = command.fetchOccurrence("y=\\d+".toRegex()).substring(2).toInt()
                rotateRow(y, shift)
            } else {
                val x = command.fetchOccurrence("x=\\d+".toRegex()).substring(2).toInt()
                rotateColumn(x, shift)
            }
        }
    }
}

fun prettyPrintScreen() {
    val prettyScreen = screen.map { row ->
        row.map {
            when {
                it -> "#"
                else -> " "
            }
        }
    }
    prettyScreen.forEach { println(it) }
}

fun main() {
    val input = TaskInput.data
    swipeCard(input)
    println("Pixels on: ${countPixels()}")
    prettyPrintScreen()
}