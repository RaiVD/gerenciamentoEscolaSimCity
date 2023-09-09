package view

import model.InputUserModel

class MainView {
    private val inputUserModel = InputUserModel()
    fun start() {
        println("\n========================== RH Gerenciamento - Escola SimCity ============================")
        var option: Int
        do {
            printMenu()
            option = inputUserModel.readIntFromUser("Qual opção você deseja: ")

            when (option) {
                0 -> println("Encerrando o programa...")
                1 -> AdminView()
                else -> println("Opção inválida, tente novamente!")
            }
        } while (option != 0)
    }
    private fun printMenu() {
        println("0. Sair | 1. Login")
    }
}