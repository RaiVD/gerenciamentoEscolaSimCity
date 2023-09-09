package service.table

import connection.Connect
import model.ValidDataBaseModel.Companion.isValidAdministratorId
import model.ValidDataBaseModel.Companion.isValidAdministratorInfo
import model.ValidDataBaseModel.Companion.isValidEmail
import java.sql.SQLException

class TableAdministratorService {
    companion object {
        var connection = Connect().creatConnect()

        fun addAdministrator(name: String, email: String, password: Int) {
            try {
                if (!isValidAdministratorInfo(name, email)) {
                    println("As informações do administrador não podem estar vazias ou nulas.")
                    return
                }
                if (!isValidEmail(email)){
                    println("Email invalido")
                }
                val sql = "INSERT INTO administrator (name_admin, email_user, password_user) VALUES ('$name', '$email', $password)"

                val statement = connection.createStatement()
                statement.executeUpdate(sql)
                println("Administrador $name adicionado com sucesso!")
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        fun deleteAdministrator(id: Int) {
            if (!isValidAdministratorId(id)) {
                println("ID de administrador inválido!")
                return
            }
            val sql = "DELETE FROM administrator WHERE id=$id"

            try {
                val statement = connection.createStatement()
                statement.executeUpdate(sql)
                println("Administrador deletado com sucesso!")
                statement.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        fun listAdministrators() {
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery("SELECT id, name_admin, email_user FROM administrator")

            try {
                while (resultSet.next()) {
                    val administratorId = resultSet.getInt("id")
                    val name = resultSet.getString("name_admin")
                    val email = resultSet.getString("email_user")

                    println("ID: $administratorId | Nome do Administrador: $name | Email: $email")
                }
                resultSet.close()
                statement.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        fun listSpecificAdministrator(id: Int) {
            if (!isValidAdministratorId(id)) {
                println("ID de administrador inválido!")
                return
            }
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery("SELECT * FROM administrator WHERE id=$id")

            try {
                while (resultSet.next()) {
                    val administratorId = resultSet.getInt("id")
                    val name = resultSet.getString("name_admin")
                    val email = resultSet.getString("email_user")
                    val password = resultSet.getInt("password_user")

                    println("ID: $administratorId | Nome do Administrador: $name | Email: $email | Senha: $password")
                }
                resultSet.close()
                statement.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
    }
}
