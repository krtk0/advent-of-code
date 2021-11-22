package year2016.day10

object BalanceBotsFactory {
    val robots = List(211) { Robot(it) }
    val valueComparisonToRobot = mutableMapOf<Pair<Int, Int>, Int>()
    val outputs = List(21) { mutableListOf<Int>() }
}