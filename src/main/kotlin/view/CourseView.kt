package view

import model.InputUserModel
import service.table.TableCourseService
import service.table.TableStudentService

class CourseView {
    private val inputUserModel = InputUserModel()
    fun startOption() {
        var option: Int
        do {
            menu()
            option = inputUserModel.readIntFromUser("Qual opção você deseja: ")
            when (option) {
                0 -> AdminView()
                1 -> listCourse()
                2 -> listSpecificCourse()
                3 -> addCourse()
                4 -> updateCourse()
                5 -> deleteCourse()
                else -> println("Opção inválida, tente novamente!")
            }
        } while (option != 0)
    }
    fun listCourse(){
        TableCourseService.listCourse()
    }
    fun listSpecificCourse(){
        val id = inputUserModel.readIntFromUser("ID do course: ")
        TableCourseService.listSpecificCourse(id)
    }

    fun addCourse(){
        val name = inputUserModel.readStringFromUser("Nome do course: ")
        val responsibleTeacher = inputUserModel.readIntFromUser("ID do professor responsavel: ")
        TableCourseService.addCourse(name, responsibleTeacher)
    }

    fun updateCourse(){
        val id = inputUserModel.readIntFromUser("ID do course: ")
        val responsibleTeacher = inputUserModel.readIntFromUser("ID do professor responsavel: ")
        TableCourseService.updateCourse(id,responsibleTeacher)
    }

    fun deleteCourse(){
        val id = inputUserModel.readIntFromUser("ID do course: ")
        TableCourseService.deleteCourse(id)
    }

    private fun menu() {
        println(
            "\n0. Menu do administrador |" +
                    " 1. Cursos cadastrados |" +
                    " 2. Buscar cursos por ID |" +
                    " 3. Adicionar cursos |" +
                    " 4. Editar dados do cursos |" +
                    " 5. Deletar cursos"
        )
    }
}