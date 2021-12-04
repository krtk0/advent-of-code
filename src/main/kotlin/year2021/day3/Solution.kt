package year2021.day3

import util.CharFrequencyComparator.Companion.getCharFrequencyPairsSorted
import util.CharFrequencyComparatorZtoA.Companion.getCharFrequencyPairsSortedZtoA
import kotlin.math.pow

fun colToRow(input: List<String>): List<String> {
    val col = List(input.first().length) { mutableListOf<Char>() }
    input.forEach {
        it.forEachIndexed { index, char ->
            col[index].add(char)
        }
    }
    return col.map { it.joinToString("") }
}

fun toDecimal(binaryNumber: String): Int {
    var sum = 0
    binaryNumber.reversed().forEachIndexed { k, v ->
        sum += v.toString().toInt() * 2.toDouble().pow(k.toDouble()).toInt()
    }
    return sum
}

fun calculateRatePart1(
    input: List<String>,
    pickFunc: (List<Pair<Char, Int>>) -> Pair<Char, Int>
): Int {
    return toDecimal(input.map { pickFunc(getCharFrequencyPairsSorted(it)).first }
        .joinToString(""))
}

fun calculateRatePart2(
    input: List<String>,
    pickFunc: (List<Pair<Char, Int>>) -> Pair<Char, Int>
): Int {
    var mutableInput = input.map { it }
    loop@ for (i in 0 until input.first().length) {
        val charCriteria =
            pickFunc(getCharFrequencyPairsSortedZtoA(mutableInput.map { it[i] }
                .joinToString(""))).first
        mutableInput = mutableInput.filter { it[i] == charCriteria }
        if (mutableInput.size == 1) {
            break@loop
        }
    }
    return toDecimal(mutableInput.first())
}

fun gammaTimesEpsilon(input: List<String>): Int {
    val gammaRate = calculateRatePart1(input) { a: List<Pair<Char, Int>> -> a.first() }
    val epsilonRate = calculateRatePart1(input) { a: List<Pair<Char, Int>> -> a.last() }
    return gammaRate * epsilonRate
}

fun oxygenTimesCo2(input: List<String>): Int {
    val oxygenRate = calculateRatePart2(input) { a: List<Pair<Char, Int>> -> a.first() }
    val co2Rate = calculateRatePart2(input) { a: List<Pair<Char, Int>> -> a.last() }
    return oxygenRate * co2Rate
}

fun main() {
    val input = TaskInput.data
    println("part1: ${gammaTimesEpsilon(colToRow(input))}")
    println("part2: ${oxygenTimesCo2(input)}")
}