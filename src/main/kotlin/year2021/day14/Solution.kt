package year2021.day14


fun polymerize(input: List<String>, repeatTimes: Int): Long {
    val rules = getRules(input)
    var polymerPairs = mutableMapOf<String, Long>()
    val polymerCount = mutableMapOf<String, Long>()
    getPolymerTemplate(input).forEach { addToMap(polymerCount, it, 1) }
    getPolymerTemplate(input).windowed(2).forEach {
        val pairString = it.joinToString("")
        addToMap(polymerPairs, pairString, 1)
    }
    repeat(repeatTimes) {
        val newPolymerPairs = mutableMapOf<String, Long>()
        polymerPairs.forEach {
            val newPair1 = "${it.key[0]}${rules[it.key]}"
            val newPair2 = "${rules[it.key]}${it.key[1]}"
            addToMap(newPolymerPairs, newPair1, it.value)
            addToMap(newPolymerPairs, newPair2, it.value)
            addToMap(polymerCount, rules[it.key]!!, it.value)
        }
        polymerPairs = newPolymerPairs
    }
    val sortedPolymerCount =
        polymerCount.map { it.key to it.value }.sortedByDescending { it.second }
    println("Total: ${sortedPolymerCount.sumOf { it.second }}, $sortedPolymerCount")
    return sortedPolymerCount.first().second - sortedPolymerCount.last().second
}

fun addToMap(polymerPairs: MutableMap<String, Long>, keyToAdd: String, value: Long) {
    val pairCount = polymerPairs.getOrDefault(keyToAdd, 0L)
    polymerPairs[keyToAdd] = pairCount + value
}

private fun getRules(input: List<String>) = input.subList(2, input.size).associate {
    val rule = it.split(" -> ")
    rule[0] to rule[1]
}

private fun getPolymerTemplate(input: List<String>) = input[0].split("").filter { it != "" }


fun main() {
    println("part1: ${polymerize(TaskInput.data, 10)}")
    println("part2: ${polymerize(TaskInput.data, 40)}")
}