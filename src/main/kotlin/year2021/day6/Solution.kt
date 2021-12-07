package year2021.day6

fun growLanternfishPopulationBruteforce(initialPopulation: List<Int>, dayAmount: Int): Int {
    var population = initialPopulation.toMutableList()
    var notFertileYet = 0
    for (day in 1..dayAmount) {
        population = population.map { it - 1 }.toMutableList()
        val newBornAmount = population.count { it == -1 }
        population.replaceAll {
            val newTimerValue = when (it) {
                -1 -> 6
                else -> it
            }
            newTimerValue
        }
        if (day <= dayAmount - 6) {
            val newBorns = List(newBornAmount) { 8 }
            population.addAll(newBorns)
        } else {
            notFertileYet += newBornAmount
        }
        println("day $day: total ${population.size}")
    }
    return population.size + notFertileYet
}

fun growLanternfishPopulation(population: List<Int>, dayAmount: Int): Long {
    val fishPerCycleDay = ArrayDeque(List(9) { 0L })
    for (fish in population) {
        fishPerCycleDay[fish]++
    }
    repeat(dayAmount) {
        fishPerCycleDay.addLast(fishPerCycleDay.removeFirst())
        fishPerCycleDay[6] += fishPerCycleDay[8]
    }
    return fishPerCycleDay.sum()
}

fun main() {
    println("part1: ${growLanternfishPopulation(TaskInput.data, 80)}")
    println("part2: ${growLanternfishPopulation(TaskInput.data, 256)}")
}