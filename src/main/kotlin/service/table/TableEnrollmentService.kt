package service.table

import connection.Connect
import model.ValidDataBaseModel.Companion.isValidEnrollmentId
import model.ValidDataBaseModel.Companion.isValidEnrollmentInfo
import java.sql.SQLException

class TableEnrollmentService {
    companion object {

        var connection = Connect().creatConnect()
        fun addEnrollment(student_id: Int, course_id: Int, enrollment_date: String) {
            try {
                if (!isValidEnrollmentInfo(enrollment_date)) {
                    println("As informações da matrícula não podem estar vazias ou nulas.")
                    return
                }
                val sql = "INSERT INTO enrollments (student_id, course_id, enrollment_date) VALUES ($student_id, $course_id, '$enrollment_date')"

                val statement = connection.createStatement()
                statement.executeUpdate(sql)
                println("Matrícula adicionada com sucesso!")
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        fun deleteEnrollment(id: Int) {
            if (!isValidEnrollmentId(id)) {
                println("ID de matrícula inválido!")
                return
            }
            val sql = "DELETE FROM enrollments WHERE id=$id"

            try {
                val statement = connection.createStatement()
                statement.executeUpdate(sql)
                println("Matrícula deletada com sucesso!")
                statement.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        fun listEnrollment() {
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery("SELECT id, student_id, course_id, enrollment_date FROM enrollments")

            try {
                while (resultSet.next()) {
                    val enrollmentId = resultSet.getInt("id")
                    val student_id = resultSet.getInt("student_id")
                    val course_id = resultSet.getInt("course_id")
                    val enrollment_date = resultSet.getString("enrollment_date")

                    println("ID: $enrollmentId | ID do Aluno: $student_id | ID do Curso: $course_id | Data de Matrícula: $enrollment_date")
                }
                resultSet.close()
                statement.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        fun listSpecificEnrollment(id: Int) {
            if (!isValidEnrollmentId(id)) {
                println("ID de matrícula inválido!")
                return
            }
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery("SELECT * FROM enrollments WHERE id=$id")

            try {
                while (resultSet.next()) {
                    val enrollmentId = resultSet.getInt("id")
                    val student_id = resultSet.getInt("student_id")
                    val course_id = resultSet.getInt("course_id")
                    val enrollment_date = resultSet.getString("enrollment_date")

                    println("ID: $enrollmentId | ID do Aluno: $student_id | ID do Curso: $course_id | Data de Matrícula: $enrollment_date")
                }
                resultSet.close()
                statement.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
    }
}
