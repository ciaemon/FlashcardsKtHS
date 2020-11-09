package flashcards

import java.io.File

class Library : Iterable<Flashcard> {
    private val lib: MutableSet<Flashcard> = HashSet()
    val size
        get() =  lib.size
    fun asSet() = lib.toSet()
    operator fun plusAssign(card: Flashcard) {
        lib.add(card)
    }

    fun add(card: Flashcard, replace: Boolean = true) {
        if (replace) {
            lib.remove(card)
        }
        lib.add(card)
    }

    operator fun minusAssign(card: Flashcard) {
        lib.remove(card)
    }
    fun random(): Flashcard = lib.random()
    fun getByTerm(term: String): Flashcard? = lib.find { it.term == term }
    fun containsTerm(term: String): Boolean = getByTerm(term) != null
    fun getByDefinition(definition: String): Flashcard? = lib.find { it.definition == definition }
    fun containsDefinition(definition: String): Boolean = getByDefinition(definition) != null
    override fun iterator(): Iterator<Flashcard> = lib.iterator()

    fun export(path: String) {
        val exportFile = File(path)
        exportFile.writeText("")
        for (card in this) {
            exportFile.appendText("${card.term} : ${card.definition} : ${card.errorCount}\n")
        }
        println("${this.size} cards have been saved")
    }
    fun import(path: String) {
        val importFile = File(path)
        if (!importFile.canRead()) {
            println("File not found.")
            return
        }
        val linesList = importFile.readLines()
        val newCardsNumber = linesList.size
        for (line in linesList) {
            val (term, definition, errorCount) = line.split(" : ")
            this.add(Flashcard(term, definition, errorCount.toInt()))
        }
        println("$newCardsNumber cards have been loaded")
    }


}