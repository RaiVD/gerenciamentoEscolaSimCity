package view

import model.InputUserModel
import service.table.TableStudentService
import service.table.TableTeacherService

class TeacherView {
    private val inputUserModel = InputUserModel()
    fun startOption() {
        var option: Int
        do {
            menu()
            option = inputUserModel.readIntFromUser("Qual opção você deseja: ")
            when (option) {
                0 -> AdminView()
                1 -> listStudents()
                2 -> listSpecificTeacher()
                3 -> addTeacher()
                4 -> updateTeacher()
                5 -> deleteTeacher()
                else -> println("Opção inválida, tente novamente!")
            }
        } while (option != 0)
    }
    fun listStudents(){
        TableTeacherService.listTeacher()
    }
    fun listSpecificTeacher(){
        val id = inputUserModel.readIntFromUser("ID do professor: ")
        TableTeacherService.listSpecificTeacher(id)
    }

    fun addTeacher(){
        val name = inputUserModel.readStringFromUser("Nome do professor: ")
        val discipline = inputUserModel.readStringFromUser("Diciplina: ")
        TableTeacherService.addTeacher(name, discipline)
    }

    fun updateTeacher(){
        val id = inputUserModel.readIntFromUser("ID do professor: ")
        val discipline = inputUserModel.readStringFromUser("Diciplina: ")
        TableTeacherService.updateTeacher(id, discipline)
    }

    fun deleteTeacher(){
        val id = inputUserModel.readIntFromUser("ID do professor: ")
        TableTeacherService.deleteTeacher(id)
    }

    private fun menu() {
        println(
            "\n0. Menu do administrador |" +
                    " 1. Professores cadastrados |" +
                    " 2. Buscar Professores por ID |" +
                    " 3. Adicionar Professores |" +
                    " 4. Editar dados do Professores |" +
                    " 5. Deletar Professores"
        )
    }
}