package year2016.day9

fun getNextInput(input: String, index: Int): String = when (index) {
    in input.indices -> input.substring(index)
    else -> ""
}

fun getDecompressedSize(input: String, fully: Boolean = false): Long {
    if (input.isEmpty()) {
        return 0
    } else {
        return if (input[0] != '(') {
            1 + getDecompressedSize(getNextInput(input, 1), fully)
        } else {
            var i = 1
            var marker = ""
            while (input[i] != ')') {
                marker += input[i]
                i++
            }
            val markerPair = marker.split("x")
            val repetitiveSequenceLength = markerPair[0].toInt()
            val times = markerPair[1].toInt()
            val nextBlockStart = i + 1 + repetitiveSequenceLength
            getDecompressedSize(getNextInput(input, nextBlockStart), fully) + when (fully) {
                true -> getDecompressedSize(
                    input.substring(i + 1, nextBlockStart).repeat(times),
                    fully
                )
                else -> input.substring(i + 1, nextBlockStart).length.toLong() * times
            }
        }
    }
}

fun main() {
    val input = TaskInput.data
    println(getDecompressedSize(input))
    println(getDecompressedSize(input, fully = true))
}