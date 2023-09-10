package view

import model.InputUserModel
import service.table.TableEnrollmentService
import service.table.TableStudentService

class EnrollmentView {
    private val inputUserModel = InputUserModel()
    fun startOption() {
        var option: Int
        do {
            menu()
            option = inputUserModel.readIntFromUser("Qual opção você deseja: ")
            when (option) {
                0 -> AdminView()
                1 -> listEnrollment()
                2 -> listSpecificEnrollment()
                3 -> addEnrollment()
                4 -> deleteEnrollment()
                else -> println("Opção inválida, tente novamente!")
            }
        } while (option != 0)
    }
    fun listEnrollment(){
        TableEnrollmentService.listEnrollment()
    }
    fun listSpecificEnrollment(){
        val id = inputUserModel.readIntFromUser("ID da matricula: ")
        TableEnrollmentService.listSpecificEnrollment(id)
    }

    fun addEnrollment(){
        val id_Student = inputUserModel.readIntFromUser("ID do aluno: ")
        val id_course = inputUserModel.readIntFromUser("ID do curso: ")
        val date = inputUserModel.readStringFromUser("Data da matricula (DD/MM/YYYY): ")
        TableEnrollmentService.addEnrollment(id_Student, id_course, date)
    }

    fun deleteEnrollment(){
        val id = inputUserModel.readIntFromUser("ID do matricula: ")
        TableEnrollmentService.deleteEnrollment(id)
    }

    private fun menu() {
        println(
            "\n0. Menu do administrador |" +
                    " 1. Matriculas cadastradas |" +
                    " 2. Buscar matricula por ID |" +
                    " 3. Adicionar matricula |" +
                    " 4. Deletar matricula"
        )
    }
}