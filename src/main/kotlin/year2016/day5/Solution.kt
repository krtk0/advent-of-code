package year2016.day5

import java.math.BigInteger
import java.security.MessageDigest

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

fun getHashes1(code: String): List<String> {
    val hashes = mutableListOf<String>()
    var idx = 0
    while (hashes.size < 8) {
        val candidate = code + idx
        val md5 = candidate.md5()
        if ("00000" == md5.substring(0, 5)) {
            println(md5)
            hashes.add(md5)
        }
        idx++
    }
    return hashes
}

fun generatePassword1(code: String): String {
    return getHashes1(code)
        .map { it[5] }
        .joinToString("")
}

fun generatePassword2(code: String): String {
    val passwordSymbols = mutableMapOf<Int, Char>()
    var idx = 0
    while (passwordSymbols.size < 8) {
        val candidate = code + idx
        val md5 = candidate.md5()
        val positionCandidate = md5[5].code - 48
        if ("00000" == md5.substring(0, 5)
            && positionCandidate in 0..7
            && positionCandidate !in passwordSymbols.keys
        ) {
            println(md5)
            passwordSymbols[positionCandidate] = md5[6]
        }
        idx++
    }
    val password = mutableListOf<Char>()
    for (key in 0..7) {
        password.add(passwordSymbols[key]!!)
    }
    return password.joinToString("")
}

fun main() {
    println(generatePassword1(TaskInput.data))
    println(generatePassword2(TaskInput.data))
}