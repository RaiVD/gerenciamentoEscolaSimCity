package service.table

import connection.Connect
import model.ValidDataBaseModel.Companion.isValidTeacherId
import model.ValidDataBaseModel.Companion.isValidTeacherInfo
import java.sql.SQLException

class TableTeacherService {
    companion object {

        var connection = Connect().creatConnect()
        fun addTeacher(name_teacher: String, discipline: String) {
            try {
                if (!isValidTeacherInfo(name_teacher, discipline)) {
                    println("As informações do professor não podem estar vazias ou nulas.")
                    return
                }
                val sql = "INSERT INTO teachers (name_teacher, discipline) VALUES ('$name_teacher', '$discipline')"

                val statement = connection.createStatement()
                statement.executeUpdate(sql)
                println("Professor $name_teacher adicionado com sucesso!")
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        fun deleteTeacher(id: Int) {
            if (!isValidTeacherId(id)) {
                println("ID de professor inválido!")
                return
            }

            try {
                val deleteEnrollmentsSQL = "DELETE FROM enrollments WHERE course_id IN (SELECT id FROM courses WHERE responsible_teacher = $id)"
                val statement = TableCourseService.connection.createStatement()
                statement.executeUpdate(deleteEnrollmentsSQL)

                val deleteCoursesSQL = "DELETE FROM courses WHERE responsible_teacher = $id"
                statement.executeUpdate(deleteCoursesSQL)

                val deleteTeacherSQL = "DELETE FROM teachers WHERE id = $id"
                statement.executeUpdate(deleteTeacherSQL)

                println("Professor deletado com sucesso!")
                statement.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        fun updateTeacher(id: Int, discipline: String) {
            try {
                if (!isValidTeacherId(id) || !discipline.isNotBlank()) {
                    println("ID de professor inválido ou informações inválidas.")
                    return
                }
                val sql = "UPDATE teachers SET discipline = '$discipline' WHERE id = $id"

                val statement = connection.createStatement()
                val rowsUpdated = statement.executeUpdate(sql)

                if (rowsUpdated > 0) {
                    println("Professor com ID $id atualizado com sucesso!")
                } else {
                    println("Nenhum professor com ID $id encontrado para atualização.")
                }
                statement.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        fun listTeacher() {
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery("SELECT id, name_teacher, discipline FROM teachers")

            try {
                while (resultSet.next()) {
                    val teacherId = resultSet.getInt("id")
                    val name_teacher = resultSet.getString("name_teacher")
                    val discipline = resultSet.getString("discipline")

                    println("ID: $teacherId | Nome do professor: $name_teacher | Disciplina: $discipline")
                }
                resultSet.close()
                statement.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
        fun listSpecificTeacher(id: Int) {
            if (!isValidTeacherId(id)) {
                println("ID de professor inválido!")
                return
            }
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery("SELECT * FROM teachers WHERE id=$id")

            try {
                while (resultSet.next()) {
                    val teacherId = resultSet.getInt("id")
                    val name_teacher = resultSet.getString("name_teacher")
                    val discipline = resultSet.getString("discipline")

                    println("ID: $teacherId | Nome do professor: $name_teacher | Disciplina: $discipline")
                }
                resultSet.close()
                statement.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
    }
}
