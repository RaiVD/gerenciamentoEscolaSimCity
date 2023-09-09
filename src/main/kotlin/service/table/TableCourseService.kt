package service.table

import connection.Connect
import model.ValidDataBaseModel.Companion.isValidCourseId
import model.ValidDataBaseModel.Companion.isValidCoursesInfo
import java.sql.SQLException

class TableCourseService {
    companion object {

        var connection = Connect().creatConnect()
        fun addCourse(name_course: String, responsible_teacher: Int) {
            try {
                if (!isValidCoursesInfo(name_course)) {
                    println("As informações do curso não podem estar vazias ou nulas.")
                    return
                }
                val sql = "INSERT INTO courses (name_courses, responsible_teacher) VALUES ('$name_course', $responsible_teacher)"

                val statement = connection.createStatement()
                statement.executeUpdate(sql)
                println("Curso $name_course adicionado com sucesso!")
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        fun deleteCourse(id: Int) {
            if (!isValidCourseId(id)) {
                println("ID de curso inválido!")
                return
            }

            try {
                // Primeiro, exclua as matrículas associadas ao curso
                val deleteEnrollmentsSQL = "DELETE FROM enrollments WHERE course_id = $id"
                val statement = connection.createStatement()
                statement.executeUpdate(deleteEnrollmentsSQL)

                // Em seguida, exclua o curso da tabela courses
                val deleteCourseSQL = "DELETE FROM courses WHERE id = $id"
                statement.executeUpdate(deleteCourseSQL)

                println("Curso deletado com sucesso!")
                statement.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
        fun updateCourse(id: Int, responsibleTeacher: Int) {
            try {
                if (!isValidCourseId(id)) {
                    println("ID de curso inválido ou informações inválidas.")
                    return
                }
                val sql = "UPDATE courses SET responsible_teacher = $responsibleTeacher WHERE id = $id"

                val statement = connection.createStatement()
                val rowsUpdated = statement.executeUpdate(sql)

                if (rowsUpdated > 0) {
                    println("Curso com ID $id atualizado com sucesso!")
                } else {
                    println("Nenhum curso com ID $id encontrado para atualização.")
                }
                statement.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
        fun listCourse() {
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery("SELECT id, name_courses, responsible_teacher FROM courses")

            try {
                while (resultSet.next()) {
                    val courseId = resultSet.getInt("id")
                    val name_course = resultSet.getString("name_courses")
                    val responsible_teacher = resultSet.getInt("responsible_teacher")

                    println("ID: $courseId | Nome do curso: $name_course | Professor Responsável: $responsible_teacher")
                }
                resultSet.close()
                statement.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
        fun listSpecificCourse(id: Int) {
            if (!isValidCourseId(id)) {
                println("ID de curso inválido!")
                return
            }
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery("SELECT * FROM courses WHERE id=$id")

            try {
                while (resultSet.next()) {
                    val courseId = resultSet.getInt("id")
                    val name_course = resultSet.getString("name_courses")
                    val responsible_teacher = resultSet.getInt("responsible_teacher")

                    println("ID: $courseId | Nome do curso: $name_course | Professor Responsável: $responsible_teacher")
                }
                resultSet.close()
                statement.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
    }
}
