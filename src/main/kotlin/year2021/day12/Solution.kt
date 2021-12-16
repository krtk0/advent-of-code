package year2021.day12

data class Cave(val name: String, val parent: Cave?)

private fun Cave.getPath(): List<String> {
    val path = mutableListOf(this.name)
    var cameFrom = parent
    while (cameFrom != null) {
        path.add(cameFrom.name)
        cameFrom = cameFrom.parent
    }
    return path.reversed()
}

fun part1(caveSystem: Map<String, List<String>>): Int {
    return countPathsToEnd(caveSystem, part = 1)
}

fun part2(caveSystem: Map<String, List<String>>): Int {
    return countPathsToEnd(caveSystem, part = 2)
}

private fun countPathsToEnd(caveSystem: Map<String, List<String>>, part: Int): Int {
    var pathCount = 0
    val caveStack = ArrayDeque<Cave>()
    caveStack.addLast(Cave("start", null))
    while (caveStack.isNotEmpty()) {
        val current = caveStack.removeLast()
        if (current.name == "end") {
            pathCount++
        } else {
            caveSystem[current.name]!!.forEach { neighbor ->
                if (neighbor.lowercase() == neighbor) {
                    if (canSmallCaveBeVisited(
                            smallCaveName = neighbor,
                            currentCave = current,
                            part = part
                        )
                    ) {
                        caveStack.addLast(Cave(neighbor, parent = current))
                    }
                } else {
                    caveStack.addLast(Cave(neighbor, parent = current))
                }
            }
        }
    }
    return pathCount
}

private fun canSmallCaveBeVisited(smallCaveName: String, currentCave: Cave, part: Int): Boolean {
    return when (part) {
        1 -> smallCaveName !in currentCave.getPath()
        2 -> (smallCaveName != "start") && (smallCaveName !in currentCave.getPath() || neverVisitedSmallCaveTwice(
            currentCave.getPath()
        ))
        else -> false
    }
}

private fun neverVisitedSmallCaveTwice(path: List<String>): Boolean {
    val smallCavesInPath = path.filter { it.lowercase() == it }
    return smallCavesInPath.size == smallCavesInPath.toSet().size
}

private fun populateCaveMap(input: List<String>): Map<String, List<String>> {
    val caveSystem = mutableMapOf<String, ArrayList<String>>()
    input.forEach { tunnel ->
        val caves = tunnel.split("-")
        val firstCave = caveSystem.getOrDefault(caves[0], arrayListOf())
        val secondCave = caveSystem.getOrDefault(caves[1], arrayListOf())
        firstCave.add(caves[1])
        secondCave.add(caves[0])
        caveSystem[caves[0]] = firstCave
        caveSystem[caves[1]] = secondCave
    }
    return caveSystem
}

fun main() {
    val caveSystem = populateCaveMap(TaskInput.data)
    println("part1: ${part1(caveSystem)}")
    println("part2: ${part2(caveSystem)}")
}