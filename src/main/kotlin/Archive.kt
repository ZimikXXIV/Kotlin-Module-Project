import java.util.Scanner

data class Notes(val noteName: String, val noteText: String)

class Archives(val archiveName: String) {
    var listOfNotes: MutableList<Notes> = mutableListOf()
    private val reader = Scanner(System.`in`)


    fun addNote() {
        while (true) {
            println("Введите название заметки:")
            val newName = reader.nextLine()
            if (newName != null && listOfNotes.any { it -> it.noteName == newName }) {
                println("Данное название уже используется, введите другое")
                continue
            }
            println("Введите текст заметки:")
            val newText = reader.nextLine()
            listOfNotes.add(Notes(newName, newText))
            println("Заметка с названием $newName создана")
            break
        }
    }

    fun getNotesList(): Int {
        var counter = 0
        println("${counter++}. Создать заметку")
        listOfNotes.forEach { value ->
            println("${counter++}. ${value.noteName}")
        }
        println("${counter}. Выход")
        return counter
    }

    fun loadNote(selector: Int) {
        println(listOfNotes[selector].noteText)
        println("Введите любое число для выхода из просмотра")
    }

}
