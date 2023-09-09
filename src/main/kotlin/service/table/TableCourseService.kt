package service

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
            val sql = "DELETE FROM courses WHERE id=$id"

            try {
                val statement = connection.createStatement()
                statement.executeUpdate(sql)
                println("Curso deletado com sucesso!")
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
