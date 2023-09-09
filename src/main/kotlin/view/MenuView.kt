package view

import model.InputUserModel
import model.ValidDataBaseModel

class MenuView {
    private val inputUserModel = InputUserModel()
    fun start() {
        println("\n========================== RH Gerenciamento - Escola SimCity ============================")
        var option: Int
        do {
            printMenu()
            option = inputUserModel.readIntFromUser("Qual opção você deseja: ")

            when (option) {
                1 -> {
                    val email_user = inputUserModel.readStringFromUser("Email: ")
                    val password_user = inputUserModel.readIntFromUser("Senha: ")
                    if (ValidDataBaseModel.isValidAdminCredentials(email_user, password_user)) {
                        AdminView().startOption()
                    } else {
                        println("Email ou Senha invalidos!")
                        return
                    }
                }
                0 -> println("Encerrando o programa...")
                else -> println("Opção inválida, tente novamente!")
            }
        } while (option != 0)
    }
    private fun printMenu() {
        println("\n0. Sair | 1. Login")
    }
}