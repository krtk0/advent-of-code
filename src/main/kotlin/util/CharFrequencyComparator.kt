package util

class CharFrequencyComparator {
    companion object : Comparator<Pair<Char, Int>> {
        override fun compare(o1: Pair<Char, Int>, o2: Pair<Char, Int>): Int = when {
            o1.second != o2.second -> o2.second - o1.second
            else -> o1.first.compareTo(o2.first)
        }

        /**
         * Generates a sorted list of pairs (char, number of occurrences in string) most to least
         * frequent and then alphabetically.
         */
        fun getCharFrequencyPairsSorted(text: String): List<Pair<Char, Int>> {
            val frequencyMap = mutableMapOf<Char, Int>()
            for (letter in text) {
                frequencyMap[letter] = frequencyMap.getOrDefault(letter, 0) + 1
            }
            return frequencyMap
                .map { Pair(it.key, it.value) }
                .sortedWith(CharFrequencyComparator)
        }
    }
}