package service

import connection.Connect
import model.ValidDataBaseModel.Companion.isValidStudentId
import model.ValidDataBaseModel.Companion.isValidStudentInfo
import java.sql.SQLException

class TableStudentService {
    companion object{

        var connection = Connect().creatConnect()
        fun addStudent(name_student: String, date_of_birth: String, addrss: String) {
            try {
                if (!isValidStudentInfo(name_student, date_of_birth, addrss)) {
                    println("As informações do aluno não pode estar vazio ou nulo.")
                    return
                }
                val sql = "INSERT INTO students (name_students, date_of_birth, address) VALUES ('$name_student', '$date_of_birth', '$addrss')"

                val statement = connection.createStatement()
                statement.executeUpdate(sql)
                println("Aluno $name_student adicionado com sucesso!")
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        fun deleteStudent(id: Int) {
            if (!isValidStudentId(id)) {
                println("ID de aluno inválido!")
                return
            }
            val sql =
                "DELETE FROM students WHERE id=$id"

            try {
                val statement = connection.createStatement()
                statement.executeUpdate(sql)
                println("Aluno deletado com sucesso!")
                statement.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        fun listSpecificStudent(idBook: Int) {
            if (!isValidStudentId(idBook)) {
                println("ID de aluno inválido!")
                return
            }
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery("SELECT * FROM students WHERE id=$idBook")

            try {
                while (resultSet.next()) {
                    val id = resultSet.getInt("id")
                    val name_student = resultSet.getString("name_student")
                    val date_of_birth = resultSet.getString("date_of_birth")
                    val addrss = resultSet.getString("address")

                    println("ID: $id | Nome do aluno: $name_student | Data de nascimento: $date_of_birth | Endereço: $addrss")
                }
                resultSet.close()
                statement.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
    }
}