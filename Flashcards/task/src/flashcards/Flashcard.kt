package flashcards

class Flashcard(val term: String, val definition: String, var errorCount: Int = 0) : Comparable<Flashcard> {
    val RIGHT_ANSWER = "Your answer is right!"
    val WRONG_ANSWER = "Your answer is wrong!"

    fun print() = println("Card:\n$term\nDefinition:\n$definition")
    fun checkAnswer(answer: String): Boolean = answer == this.definition

    override fun equals(other: Any?): Boolean =
            other is Flashcard && (other.term == this.term || other.definition == this.definition)

    override fun hashCode(): Int = 300
    override fun compareTo(other: Flashcard): Int = this.errorCount.compareTo(other.errorCount)

}