package year2016.day4

import java.util.Collections.rotate

data class Room(val name: String, val sectorId: Int, val checkSum: String)

fun Room.isReal(): Boolean {
    val checkSumTarget = this.countOccurrencesInName()
        .sortedWith(LetterTupleComparator)
        .subList(0, 5)
        .map { it.first }
        .joinToString("")
    return checkSumTarget == checkSum
}

fun Room.countOccurrencesInName(): List<Pair<Char, Int>> {
    val occurrenceMap = mutableMapOf<Char, Int>()
    for (letter in this.name) {
        if (letter != '-') {
            occurrenceMap[letter] = occurrenceMap.getOrDefault(letter, 0) + 1
        }
    }
    return occurrenceMap.map { Pair(it.key, it.value) }
}

fun Room.decipherName(): String {
    val alphabet = ('a'..'z').toMutableList()
    rotate(alphabet, -this.sectorId)
    val words = this.name.split("-")
    val decipheredWords = mutableListOf<String>()
    for (word in words) {
        val realWord = mutableListOf<Char>()
        for (letter in word) {
            realWord.add(alphabet[letter.code - 97])
        }
        decipheredWords.add(realWord.joinToString(""))
    }
    return decipheredWords.joinToString(" ")
}

fun prepareInput(rawInput: List<String>): List<Room> {
    return rawInput.map { buildRoom(it) }
}

fun buildRoom(roomString: String): Room {
    val sectorId = roomString.extractFeature("\\d+".toRegex())
    val checkSumRaw = roomString.extractFeature("\\[[a-z]+]".toRegex())
    val checkSum = checkSumRaw.substring(1 until checkSumRaw.length - 1)
    val name = roomString
        .substring(0..(roomString.length - checkSum.length - sectorId.length - 4))
    return Room(name, sectorId.toInt(), checkSum)
}

fun String.extractFeature(feature: Regex): String = feature.find(this)!!.value

fun sumSectorIds(rooms: List<Room>): Int {
    return rooms
        .map { it.sectorId }
        .reduce { id1, id2 -> id1 + id2 }
}

fun getRealRooms(rawInput: List<String>): List<Room> {
    return prepareInput(rawInput)
        .filter { it.isReal() }
}

fun decipherRoomNames(rooms: List<Room>) {
    rooms.forEach {
        println("Room: ${it.decipherName()} | ${it.sectorId}")
    }
}

fun main() {
    val realRooms = getRealRooms(TaskInput.data)
    println("Real rooms sector IDs: ${sumSectorIds(realRooms)}")
    decipherRoomNames(realRooms)
}