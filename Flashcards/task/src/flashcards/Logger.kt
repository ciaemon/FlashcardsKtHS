package flashcards

import java.io.File

val logger: MutableList<String> = mutableListOf()

fun println(message: Any?) {
    kotlin.io.println(message)
    logger += message.toString()
}

fun readLine(): String? {
    val s = kotlin.io.readLine()
    logger += s!!
    return s
}

fun saveLog(path: String) {
    val file = File(path)
    for (line in logger) {
        file.appendText(line)
        file.appendText("\n")
    }
}