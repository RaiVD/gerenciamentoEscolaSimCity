package service.table

import connection.Connect
import model.ValidDataBaseModel.Companion.isValidStudentId
import model.ValidDataBaseModel.Companion.isValidStudentInfo
import model.ValidDataBaseModel.Companion.parseDate
import java.sql.SQLException
import java.text.ParseException
import java.time.format.DateTimeParseException

class TableStudentService {
    companion object{

        var connection = Connect().creatConnect()
        fun addStudent(name_student: String, date_of_birth: String, addrss: String) {
            try {
                if (!isValidStudentInfo(name_student, date_of_birth, addrss)) {
                    println("As informações do aluno não pode estar vazio ou nulo.")
                    return
                }
                val sql = "INSERT INTO students (name_student, date_of_birth, address) VALUES ('$name_student', '${parseDate(date_of_birth)}', '$addrss')"

                val statement = connection.createStatement()
                statement.executeUpdate(sql)
                println("Aluno $name_student adicionado com sucesso!")
            } catch (e: ParseException) {
                println("Formato de data inválido. Use o formato DD-MM-AAAA.")
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        fun deleteStudent(id: Int) {
            if (!isValidStudentId(id)) {
                println("ID de aluno inválido!")
                return
            }

            try {
                // Excluir matrículas associadas ao aluno
                val deleteEnrollmentsSQL = "DELETE FROM enrollments WHERE student_id = $id"
                val statement = connection.createStatement()
                statement.executeUpdate(deleteEnrollmentsSQL)

                // Em seguida, excluir o aluno
                val deleteStudentSQL = "DELETE FROM students WHERE id = $id"
                statement.executeUpdate(deleteStudentSQL)

                println("Aluno e matrículas associadas excluídos com sucesso!")
                statement.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        fun updateStudent(id: Int, address: String) {
            try {
                if (!isValidStudentId(id) || !address.isNotBlank()) {
                    println("ID de aluno inválido ou informações inválidas.")
                    return
                }
                val sql = "UPDATE students SET address = '$address' WHERE id = $id"

                val statement = connection.createStatement()
                val rowsUpdated = statement.executeUpdate(sql)

                if (rowsUpdated > 0) {
                    println("Aluno com ID $id atualizado com sucesso!")
                } else {
                    println("Nenhum aluno com ID $id encontrado para atualização.")
                }
                statement.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
        fun listStudent() {
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery("SELECT id, name_student, date_of_birth, address FROM students")

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

        fun listSpecificStudent(id: Int) {
            if (!isValidStudentId(id)) {
                println("ID de aluno inválido!")
                return
            }
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery("SELECT * FROM students WHERE id=$id")

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