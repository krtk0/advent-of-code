package util

fun String.fetchOccurrence(feature: Regex): String = feature.find(this)!!.value

fun String.fetchOccurrences(feature: Regex): List<String> =
    feature.findAll(this).map { it.groupValues[0] }.toList()

fun String.fetchNumber(regex: Regex = ".+".toRegex()): Int {
    return this
        .fetchOccurrence(regex)
        .fetchOccurrence("\\d+".toRegex())
        .toInt()
}