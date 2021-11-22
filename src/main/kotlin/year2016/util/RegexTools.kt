package year2016.util

fun String.fetchOccurrence(feature: Regex): String = feature.find(this)!!.value

fun String.fetchOccurrences(feature: Regex): List<String> =
    feature.findAll(this).map { it.groupValues[0] }.toList()

fun String.fetchNumber(regex: Regex): Int {
    return this
        .fetchOccurrence(regex)
        .fetchOccurrence("\\d+".toRegex())
        .toInt()
}