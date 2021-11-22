package year2016.day10

import year2016.day10.BalanceBotsFactory.outputs
import year2016.day10.BalanceBotsFactory.valueComparisonToRobot
import year2016.util.fetchNumber

enum class Destination {
    ROBOT,
    OUTPUT
}

fun String.detectDestination(clue: String): Destination = when (this.contains(clue)) {
    true -> Destination.ROBOT
    else -> Destination.OUTPUT
}

fun processInCommand(command: String) {
    val value = command.fetchNumber("value \\d+".toRegex())
    val destinationRobotId = command.fetchNumber("to bot \\d+".toRegex())
    BalanceBotsFactory.robots[destinationRobotId].topUpMicrochipQueue(value)
}

fun processOutCommand(command: String) {
    val sourceRobotId = command.fetchNumber("bot \\d+ gives".toRegex())
    val lowToId = command.fetchNumber("low to [a-z]+ \\d+".toRegex())
    val lowDestination = command.detectDestination("low to bot")
    val highToId = command.fetchNumber("high to [a-z]+ \\d+".toRegex())
    val highDestination = command.detectDestination("high to bot")
    BalanceBotsFactory.robots[sourceRobotId].addOutCommand(
        mapOf(
            Robot.VALUE_TYPE.LOW to Pair(lowDestination, lowToId),
            Robot.VALUE_TYPE.HIGH to Pair(highDestination, highToId)
        )
    )
}

fun processCommands(input: List<String>) {
    for (command in input) {
        if (command.substring(0, 5) == "value") {
            processInCommand(command)
        } else {
            processOutCommand(command)
        }
    }
}

fun solve1(): Int {
    return valueComparisonToRobot[Pair(17, 61)]!!
}

fun solve2(): Int {
    return outputs[0].first() * outputs[1].first() * outputs[2].first()
}

fun main() {
    val input = TaskInput.data
    processCommands(input)
    println("17-61 are compared by the robot #${solve1()}")
    println("Outputs 0, 1, 2 have microchips with value product ${solve2()}")
}