package util

/**
 * Returns a list of point coordinates on the grid of orthogonal distance to the next point
 * excluding starting point.
 */
fun Pair<Int, Int>.getPathToNextOrthogonal(next: Pair<Int, Int>): List<Pair<Int, Int>> {
    val result = mutableListOf<Pair<Int, Int>>()
    if (this.first == next.first) {
        for (y in getRange(this.second, next.second)) {
            result.add(Pair(this.first, y))
        }
    } else {
        for (x in getRange(this.first, next.first)) {
            result.add(Pair(x, this.second))
        }
    }
    return result
}

private fun getRange(current: Int, next: Int): IntProgression {
    return if (current < next) {
        (current + 1)..next
    } else {
        (current - 1) downTo next
    }
}

/**
 * Returns a list of point coordinates on the grid of diagonal distance to the next point
 * including starting point.
 */
fun Pair<Int, Int>.getPathToNextDiagonal(next: Pair<Int, Int>): List<Pair<Int, Int>> {
    val result = mutableListOf(this)
    val xCoordinates = mutableListOf<Int>()
    val yCoordinates = mutableListOf<Int>()
    for (x in getRange(this.first, next.first)) {
        xCoordinates.add(x)
    }
    for (y in getRange(this.second, next.second)) {
        yCoordinates.add(y)
    }
    for (i in xCoordinates.indices) {
        result.add(Pair(xCoordinates[i], yCoordinates[i]))
    }
    return result
}