package view

import model.InputUserModel
import service.table.TableStudentService

class StudentsView {
    private val inputUserModel = InputUserModel()
    fun startOption() {
        var option: Int
        do {
            menu()
            option = inputUserModel.readIntFromUser("Qual opção você deseja: ")
            when (option) {
                0 -> AdminView()
                1 -> listStudents()
                2 -> listSpecificStudent()
                3 -> addStudent()
                4 -> updateStudent()
                5 -> deleteStudent()
                else -> println("Opção inválida, tente novamente!")
            }
        } while (option != 0)
    }
    fun listStudents(){
        TableStudentService.listStudent()
    }
    fun listSpecificStudent(){
        val id = inputUserModel.readIntFromUser("ID do aluno: ")
        TableStudentService.listSpecificStudent(id)
    }

    fun addStudent(){
        val name = inputUserModel.readStringFromUser("Nome do aluno: ")
        val date_of_birth = inputUserModel.readStringFromUser("Data de nascimento: ")
        val address = inputUserModel.readStringFromUser("Endereço: ")
        TableStudentService.addStudent(name, date_of_birth, address)
    }

    fun updateStudent(){
        val id = inputUserModel.readIntFromUser("ID do aluno: ")
        val address = inputUserModel.readStringFromUser("Novo endereço: ")
        TableStudentService.updateStudent(id,address)
    }

    fun deleteStudent(){
        val id = inputUserModel.readIntFromUser("ID do aluno: ")
        TableStudentService.deleteStudent(id)
    }

    private fun menu() {
        println(
            "\n0. Menu do administrador |" +
                    " 1. Alunos cadastrados |" +
                    " 2. Buscar aluno por ID |" +
                    " 3. Adicionar aluno |" +
                    " 4. Editar dados do aluno |" +
                    " 5. Deletar aluno"
        )
    }
}