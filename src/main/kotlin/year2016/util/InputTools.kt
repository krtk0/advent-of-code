package year2016.util

import java.io.File

class InputTools {
    fun getLinesFromFile(fileName: String) =
        File(this.javaClass.classLoader.getResource(fileName)!!.file).useLines { it.toList() }
}