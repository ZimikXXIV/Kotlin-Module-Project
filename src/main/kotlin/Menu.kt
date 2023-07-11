import java.util.Scanner

object Menu {

    private val listOfArchives: MutableList<Archives> = mutableListOf()
    private val reader = Scanner(System.`in`)
    private var selectedIndex: Int = 0 //сохраняет индекс выбранного архива
    private var activity: Int = 0 // указывает на выбранный "экран"

    fun addArchive() {
        while (true) {
            println("Введите название архива:")
            reader.nextLine()
            val newName = reader.nextLine()
            if (listOfArchives.any { it -> it.archiveName == newName } && newName != null) {
                println("Данное название уже используется, введите другое")
                continue
            }
            listOfArchives.add(Archives(newName))
            println("Архив с названием $newName создан")
            break
        }
    }

    fun getArchiveList(): Int {
        var counter = 0
        println("${counter++}. Создать архив")
        listOfArchives.forEach { value ->
            println("${counter++}. ${value.archiveName}")
        }
        println("${counter}. Выход")
        return counter
    }

    fun showMenu(isNotes: Boolean) {
        val stopper : Int
        if (isNotes) stopper =
            listOfArchives[selectedIndex].getNotesList()  // если меню запрашиваем по заметкам
        else stopper = getArchiveList()       // если меню запрашиваем по архивам


        userAction(isNotes, stopper)

    }

    fun openArchive(selector: Int) {
        selectedIndex = selector - 1
    }

    fun userAction(isNotes: Boolean, stopper: Int) {
        val selector = readUserChoice()

        if (selector == stopper) {
            if (activity > 1) selectedIndex = 0 // сбрасываем сохраненный индекс
            activity-- // если выбран элемент меню выход
        } else if (selector == 0) {
            if (isNotes) {
                listOfArchives[selectedIndex].addNote() // добавляем в список новую заметку, экран не меняется
            } else {
                addArchive()    // добавляем в список новый архив, экран не меняется
            }
        } else if (stopper < 0) { // выходим из просмотра
            activity--
        } else if (selector > stopper) { // выходим из просмотра
            println("Введено неверное значение, повторите ввод")
        } else {
            if (isNotes) {
                listOfArchives[selectedIndex].loadNote(selector - 1)  // открываем выбранную заметку
                activity++
            } else {
                openArchive(selector)
                activity++
            }
        }

    }

    fun showedNote() {
        userAction(true, -1)
    }

    fun endWork() {
        listOfArchives.clear()
        println("Спасибо что пользовались нашим приложением")
        reader.close()
        activity--
    }

    fun selectActivity() {

        when (activity) {
            -1 -> endWork()
            0 -> showMenu(false)
            1 -> showMenu(true)
            2 -> showedNote()
            else -> {
                println("Ошибка ввода, происходит сброс меню")
                activity = 0
            }
        }
    }

    fun readUserChoice(): Int {
        while (true) {
            try {
                val selector = reader.nextInt()
                return selector
            } catch (ex: Exception) {
                println("Введено некорректное значение, повторите ввод ")
                reader.nextLine()
            }
        }


    }

    fun start() {
        println("Добро пожаловать в приложение Заметки")
        while (true) {
            if (activity == -2) break // При выходе из меню, при значении -1 чистится значения, чтобы окончательно выйти требуется значение -2
            selectActivity()
        }
    }

}
