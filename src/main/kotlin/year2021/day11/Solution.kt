package year2021.day11


fun solve(input: List<Array<Int>>): Pair<Int, Int> {
    var totalFlashesDay100 = 0
    var totalFlashes = 0
    var allFlashDay = 0
    fun accumulateEnergy(
        posI: Int,
        posJ: Int,
        flashedToday: MutableMap<Pair<Int, Int>, Boolean>,
        flashImpactQueue: MutableList<Pair<Int, Int>>
    ) {
        if ((posI in 0 until 10) && (posJ in 0 until 10) && (Pair(posI, posJ) !in flashedToday)) {
            if (input[posJ][posI] < 9) {
                input[posJ][posI]++
            } else {
                input[posJ][posI] = 0
                totalFlashes++
                flashedToday[Pair(posI, posJ)] = true
                flashImpactQueue.add(Pair(posI + 1, posJ))
                flashImpactQueue.add(Pair(posI - 1, posJ))
                flashImpactQueue.add(Pair(posI, posJ + 1))
                flashImpactQueue.add(Pair(posI, posJ - 1))
                flashImpactQueue.add(Pair(posI + 1, posJ + 1))
                flashImpactQueue.add(Pair(posI - 1, posJ - 1))
                flashImpactQueue.add(Pair(posI + 1, posJ - 1))
                flashImpactQueue.add(Pair(posI - 1, posJ + 1))
            }
        }
    }

    var day = 1
    while (allFlashDay == 0) {
        val flashedToday = mutableMapOf<Pair<Int, Int>, Boolean>()
        val flashingImpactQueue = mutableListOf<Pair<Int, Int>>()
        for (j in 0 until 10) {
            for (i in 0 until 10) {
                accumulateEnergy(i, j, flashedToday, flashingImpactQueue)
            }
        }
        while (flashingImpactQueue.isNotEmpty()) {
            val flashingOctopus = flashingImpactQueue.removeFirst()
            accumulateEnergy(
                flashingOctopus.first,
                flashingOctopus.second,
                flashedToday,
                flashingImpactQueue
            )
        }
        if (flashedToday.size == 100) {
            allFlashDay = day
        }
        if (day == 100) {
            totalFlashesDay100 = totalFlashes
        }
        day++
    }
    return Pair(totalFlashesDay100, allFlashDay)
}

fun main() {
    val solution = solve(TaskInput.data)
    println("Flashes on day 100: ${solution.first}.\nAll flash day: ${solution.second}.")
}