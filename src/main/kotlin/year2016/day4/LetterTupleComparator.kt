package year2016.day4

class LetterTupleComparator {
    companion object : Comparator<Pair<Char, Int>> {
        override fun compare(o1: Pair<Char, Int>, o2: Pair<Char, Int>): Int = when {
            o1.second != o2.second -> o2.second - o1.second
            else -> o1.first.compareTo(o2.first)
        }
    }
}