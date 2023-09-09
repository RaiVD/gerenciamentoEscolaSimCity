package view

import model.InputUserModel

class AdminView {
    private val inputUserModel = InputUserModel()

    fun startOption() {
            println("\n========================================= Bem-Vindo =========================================")
            var option: Int
            do {
                menu()
                option = inputUserModel.readIntFromUser("Qual opção você deseja: ")

                when (option) {
                    0 -> MenuView()
                    1 -> StudentsView().startOption()
                    2 -> TeacherView().startOption()
                    3 -> CourseView().startOption()
                    4 -> EnrollmentView().startOption()
                    5 -> QueryInDatabaseView().startOption()
                    else -> println("Opção inválida, tente novamente!")
                }
            } while (option != 0)
    }
    private fun menu() {
        println("\n0. Menu Principal |" +
                " 1. Alunos |" +
                " 2. Professores |" +
                " 3. Cursos |" +
                " 4. Matriculas |" +
                " 5. Consultas"
        )
    }

}