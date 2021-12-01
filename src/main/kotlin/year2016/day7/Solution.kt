package year2016.day7

import util.fetchOccurrences

fun String.getIpParts(regex: Regex): List<String> {
    return this.fetchOccurrences(regex).map { it.replace("[", "").replace("]", "") }
}

fun String.hasABBA(): Boolean {
    for (i in 0 until this.length) {
        try {
            if (this[i] == this[i + 3] && this[i] != this[i + 1] && this[i + 1] == this[i + 2]) {
                return true
            }
        } catch (e: StringIndexOutOfBoundsException) {
        }
    }
    return false
}

fun String.getABAs(): List<String> {
    val abas = mutableListOf<String>()
    for (i in 0 until this.length) {
        try {
            if (this[i] == this[i + 2] && this[i] != this[i + 1]) {
                abas.add("${this[i]}${this[i + 1]}${this[i + 2]}")
            }
        } catch (e: StringIndexOutOfBoundsException) {
        }
    }
    return abas
}

fun getSupernet(ip: String): List<String> {
    return ip.getIpParts("([a-z]+\\[|][a-z]+\\[|][a-z]+)".toRegex())
}

fun getHypernet(ip: String): List<String> {
    return ip.getIpParts("\\[[a-z]+]".toRegex())
}

fun supportsTls(ip: String): Boolean {
    return getHypernet(ip).none { it.hasABBA() } && getSupernet(ip).any { it.hasABBA() }
}

fun supportsSsl(ip: String): Boolean {
    val abas = getSupernet(ip).map { it.getABAs() }.flatten().toSet()
    val babs = getHypernet(ip).map { it.getABAs() }.flatten().toSet()
    return abas
        .map { "${it[1]}${it[0]}${it[1]}" }
        .intersect(babs)
        .isNotEmpty()
}

fun solve1(ips: List<String>): Int {
    return ips.count { supportsTls(it) }
}

fun solve2(ips: List<String>): Int {
    return ips.count { supportsSsl(it) }
}

fun main() {
    val input = TaskInput.data
    println("TLS: ${solve1(input)}")
    println("SSL: ${solve2(input)}")
}