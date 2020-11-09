package flashcards

interface LibraryProcessor {
    var library: Library
    fun add()
    fun remove()
    fun import()
    fun export()
    fun ask()
    fun log()
    fun hardestCard()
    fun resetStats()
}