package flashcards

fun main(args: Array<String>) {
    val arguments = argsParse(args)
    val processor: LibraryProcessor = LibraryProcessorImpl()
    if (arguments.containsKey("import")) processor.library.import(arguments["import"]!!)

    var action = ""
    while (action != "exit") {
        println("\nInput the action (add, remove, import, export, ask, exit, log, hardest card, reset stats): ")
        action = readLine()!!
        with(processor) {
            when (action) {
                "add" -> add()
                "remove" -> remove()
                "import" -> import()
                "export" -> export()
                "ask" -> ask()
                "log" -> log()
                "hardest card" -> hardestCard()
                "reset stats" -> resetStats()
                "exit" -> {
                    println("Bye!")
                    if (arguments.containsKey("export")) {
                        processor.library.export(arguments["export"]!!)
                    }
                }
                else -> println("Wrong command")
            }
        }
    }
}

fun argsParse(args: Array<String>): Map<String, String> {
    val keysIndices = args
            .mapIndexed { index, s -> if (s.startsWith('-')) index else -1 }
            .filter { it != -1 }
    return keysIndices.associate {
        if (it == args.lastIndex || args[it + 1].startsWith('-')) {
            args[it].drop(1) to ""
        } else {
            args[it].drop(1) to args[it + 1]
        }
    }
}