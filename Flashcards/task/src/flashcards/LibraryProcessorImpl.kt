package flashcards

class LibraryProcessorImpl(override var library: Library = Library()) : LibraryProcessor {
    
    companion object {
        fun fillLibrary(cardAmount: Int): Library {
            val library = Library()
            repeat(cardAmount) {
                println("Card #${it + 1}:")
                var card = readLine()!!
                while (library.containsTerm(card)) {
                    println("The card \"$card\" already exists. Try again: ")
                    card = readLine()!!
                }
                println("The definition for card #${it + 1}")
                var definition = readLine()!!
                while (library.containsDefinition(definition)) {
                    println("The definition \"$definition\" already exists. Try again: ")
                    definition = readLine()!!
                }

                library += Flashcard(card, definition)
            }
            return library
        }

    }

    fun Flashcard.ask() {
        println("Print the definition of \"${term}\": ")
        val answer = readLine()!!
        println(when {
            definition == answer -> "Correct!"
            library.containsDefinition(answer) -> {
                val correctTerm = library.getByDefinition(answer)!!.term
                errorCount++
                "Wrong. The right answer is \"${definition}\", but your definition is correct for \"$correctTerm\"."
            }
            else -> {
                errorCount++
                "Wrong. The right answer is \"${definition}\"."
            }
        })
    }

    fun makeExamination() {
        library.forEach() { it.ask() }
    }

    override fun add() {
        println("The card: ")
        val term = readLine()!!
        if (library.containsTerm(term)) {
            println("The card \"$term\" already exists.")
            return
        }
        println("The definition of the card: ")
        val definition = readLine()!!
        if (library.containsDefinition(definition)) {
            println("The definition \"$definition\" already exists.")
            return
        }
        library.add(Flashcard(term, definition))
        println("The pair (\"$term\":\"$definition\") has been added.")
    }

    override fun remove() {
        println("The card: ")
        val term = readLine()!!
        if (library.containsTerm(term)) {
            library.minusAssign(library.getByTerm(term)!!)
            println("The card has been removed")
        } else {
            println("Can't remove \"$term\": there is no such card")
        }
    }

    override fun import() {
       println("File name: ")
       val importPath = readLine()!!
       library.import(importPath)
    }

    override fun export() {
        println("File name: ")
        val exportPath = readLine()!!
        library.export(exportPath)

    }

    override fun ask() {
        println("How many times to ask?")
        val times = readLine()!!.toInt()
        repeat(times) {
            val card = library.random()
            card.ask()
        }
    }

    override fun log() {
        println("File name: ")
        val path = readLine()!!
        println("The log has been saved.")
        saveLog(path)
    }

    override fun hardestCard() {
        val maxErrors = library.maxOrNull()?.errorCount ?: 0
        if (maxErrors == 0) {
            println("There are no cards with errors.")
            return
        }
        val hardestCards = library.filter { it.errorCount == maxErrors }
        val one = hardestCards.size == 1
        var cards = ""
        if (one) {
            cards = "\"${hardestCards[0].term}\""
        } else {
            hardestCards.forEach() { cards += "\"${it.term}\"" + " ," }
            cards = cards.dropLast(2)
        }
        val (s, toBe, pronoun) = if (one) arrayOf("", "is", "it") else arrayOf("s", "are", "them")
        println("The hardest card$s $toBe $cards. You have $maxErrors errors answering $pronoun.")
    }

    override fun resetStats() {
        library.forEach() { it.errorCount = 0 }
        println("Card statistics has been reset.")
    }
}