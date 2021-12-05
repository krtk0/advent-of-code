package year2021.day4

/**
 * List of boards with list of coordinate pairs (rowIndex, columnIndex)
 * where matches with draws are located.
 */
var drawnAndMatched = MutableList(TaskInput.boards.size) { mutableListOf<Pair<Int, Int>>() }
var boards = TaskInput.boards

/**
 * Returns index of the first winning board or -1 if none.
 */
fun getWinningBoard() = drawnAndMatched.indexOfFirst { boardWon(it) }

fun boardWon(matchedCoordinates: List<Pair<Int, Int>>): Boolean {
    for (i in 0..4) {
        val rowMatch = matchedCoordinates.count { it.first == i }
        val columnMatch = matchedCoordinates.count { it.second == i }
        if (rowMatch == 5 || columnMatch == 5) {
            return true
        }
    }
    return false
}

fun getBoardScore(winningDraw: Int, board: List<List<Pair<Int, Boolean>>>): Int {
    return winningDraw * board.flatten().filter { !it.second }.sumOf { it.first }
}

fun getFirstWinningBoardScore(): Int {
    for (drawNumber in TaskInput.draw) {
        for (boardIdx in boards.indices) {
            for (rowIdx in boards[boardIdx].indices) {
                for (columnIdx in boards[boardIdx][rowIdx].indices) {
                    if (boards[boardIdx][rowIdx][columnIdx].first == drawNumber) {
                        boards[boardIdx][rowIdx][columnIdx] = Pair(drawNumber, true)
                        drawnAndMatched[boardIdx].add(Pair(rowIdx, columnIdx))
                        val winningBoardIdx = getWinningBoard()
                        if (winningBoardIdx != -1) {
                            return getBoardScore(drawNumber, boards[winningBoardIdx])
                        }
                    }
                }
            }
        }
    }
    return -1
}

fun getLastWinningBoardScore(): Int {
    drawnAndMatched = MutableList(TaskInput.boards.size) { mutableListOf() }
    boards = TaskInput.boards
    var wonBoardsCount = TaskInput.boards.size
    for (drawNumber in TaskInput.draw) {
        for (boardIdx in boards.indices) {
            for (rowIdx in boards[boardIdx].indices) {
                for (columnIdx in boards[boardIdx][rowIdx].indices) {
                    if (boards[boardIdx][rowIdx][columnIdx].first == drawNumber) {
                        boards[boardIdx][rowIdx][columnIdx] = Pair(drawNumber, true)
                        drawnAndMatched[boardIdx].add(Pair(rowIdx, columnIdx))
                        val winningBoardIdx = getWinningBoard()
                        if (winningBoardIdx != -1) {
                            drawnAndMatched[winningBoardIdx] = mutableListOf()
                            wonBoardsCount--
                            if (wonBoardsCount == 0) {
                                return getBoardScore(drawNumber, boards[winningBoardIdx])
                            }
                        }
                    }
                }
            }
        }
    }
    return -1
}

fun main() {
    println("part1: ${getFirstWinningBoardScore()}")
    println("part2: ${getLastWinningBoardScore()}")
}