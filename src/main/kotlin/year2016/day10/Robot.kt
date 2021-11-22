package year2016.day10

import year2016.day10.BalanceBotsFactory.valueComparisonToRobot

class Robot(
    private val id: Int,
    private val values: MutableList<Int> = mutableListOf(),
    private val outCommands: MutableList<Map<VALUE_TYPE, Pair<Destination, Int>>> = mutableListOf(),
    private val pendingMicrochips: MutableList<Int> = mutableListOf()
) {
    enum class VALUE_TYPE {
        LOW,
        HIGH
    }

    private fun selectValue(valueType: VALUE_TYPE): Int? {
        return when (valueType) {
            VALUE_TYPE.LOW -> this.values.removeFirstOrNull()
            VALUE_TYPE.HIGH -> this.values.removeLastOrNull()
        }
    }

    private fun takeMicrochip(value: Int) {
        this.values.add(value)
        this.values.sort()
        if (this.values.size == 2) {
            valueComparisonToRobot[Pair(this.values[0], this.values[1])] = this.id
            this.executeOutCommand()
        }
    }

    private fun giveMicrochip(valueType: VALUE_TYPE, receiver: Destination, receiverId: Int) {
        val valueToGive = this.selectValue(valueType)
        if (valueToGive != null) {
            when (receiver) {
                Destination.ROBOT -> BalanceBotsFactory.robots[receiverId].takeMicrochip(valueToGive)
                Destination.OUTPUT -> BalanceBotsFactory.outputs[receiverId].add(valueToGive)
            }
        }
    }

    fun addOutCommand(newCommand: Map<VALUE_TYPE, Pair<Destination, Int>>) {
        this.outCommands.add(newCommand)
        if (this.values.size == 2) {
            this.executeOutCommand()
        }
    }

    fun topUpMicrochipQueue(newMicrochip: Int) {
        this.pendingMicrochips.add(newMicrochip)
        if (this.values.size < 2) {
            this.takeMicrochip(this.pendingMicrochips.removeFirst())
        }
    }

    private fun executeOutCommand() {
        val command = this.outCommands.firstOrNull()
        if (command != null) {
            this.giveMicrochip(
                VALUE_TYPE.LOW,
                command[VALUE_TYPE.LOW]!!.first,
                command[VALUE_TYPE.LOW]!!.second
            )
            this.giveMicrochip(
                VALUE_TYPE.HIGH,
                command[VALUE_TYPE.HIGH]!!.first,
                command[VALUE_TYPE.HIGH]!!.second
            )
        }
    }
}