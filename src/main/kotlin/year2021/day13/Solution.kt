package year2021.day13


fun part1(input: List<String>): Int {
    val foldingCommands = getFoldingCommands(input)
    return foldPaper(getPaper(input), foldingCommands[0]).sumOf { it.count { dot -> dot == "%" } }
}

fun part2(input: List<String>) {
    val foldingCommands = getFoldingCommands(input)
    var paper = getPaper(input)
    for (command in foldingCommands) {
        paper = foldPaper(paper, command)
    }
    paper.forEach { println(it) }
}

fun foldPaper(
    paper: List<MutableList<String>>,
    foldCommand: Pair<Char, Int>
): List<MutableList<String>> {
    return when (foldCommand.first) {
        'x' -> foldLeft(paper, foldCommand.second)
        'y' -> foldUp(paper, foldCommand.second)
        else -> emptyList()
    }
}

fun foldLeft(paper: List<MutableList<String>>, foldingLine: Int): List<MutableList<String>> {
    val foldedPaper = List(paper.size) { MutableList(foldingLine) { " " } }
    foldedPaper.forEachIndexed { y, row ->
        val theOtherHalf = paper[y].subList(foldingLine + 1, paper[y].size).reversed()
        for (i in row.indices) {
            if (paper[y][i] == "%") {
                row[i] = "%"
            } else if ((i in theOtherHalf.indices) && (theOtherHalf[i] == "%")) {
                row[i + row.size - theOtherHalf.size] = "%"
            }
        }
    }
    return foldedPaper
}

fun foldUp(paper: List<MutableList<String>>, foldingLine: Int): List<MutableList<String>> {
    val foldedPaper = paper.subList(0, foldingLine)
    for (i in foldedPaper[0].indices) {
        val theOtherHalf = paper.map { it[i] }.subList(foldingLine + 1, paper.size).reversed()
        for (y in foldedPaper.indices) {
            if ((y in theOtherHalf.indices) && (theOtherHalf[y] == "%")) {
                foldedPaper[y + foldedPaper.size - theOtherHalf.size][i] = "%"
            }
        }
    }
    return foldedPaper
}

private fun getPaper(input: List<String>): List<MutableList<String>> {
    val dots = getDots(input)
    val xDimension = dots.maxByOrNull { it.first }!!.first + 1
    val yDimension = dots.maxByOrNull { it.second }!!.second + 1
    val paper = List(yDimension) { MutableList(xDimension) { " " } }
    dots.forEach { paper[it.second][it.first] = "%" }
    return paper
}

private fun getFoldingCommands(input: List<String>): List<Pair<Char, Int>> {
    return input.subList(input.indexOf("") + 1, input.size).map {
        val foldingAxis = it[it.indexOf('=') - 1]
        val foldingLine = it.substring(it.indexOf('=') + 1).toInt()
        Pair(foldingAxis, foldingLine)
    }
}

private fun getDots(input: List<String>): List<Pair<Int, Int>> {
    return input.subList(0, input.indexOf("")).map {
        val coordinates = it.split(",")
        Pair(coordinates[0].toInt(), coordinates[1].toInt())
    }
}


fun main() {
    println("part1: ${part1(TaskInput.data)}")
    part2(TaskInput.data)
}