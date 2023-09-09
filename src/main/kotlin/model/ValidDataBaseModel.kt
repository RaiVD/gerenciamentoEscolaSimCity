package model

import connection.Connect
import java.sql.SQLException

class ValidDataBaseModel {
    companion object {
        private val connection = Connect().creatConnect()

        // Validar ID
        fun isValidStudentId(id: Int): Boolean {
            val sql = "SELECT COUNT(*) FROM students WHERE id=?"

            try {
                val preparedStatement = connection.prepareStatement(sql)
                preparedStatement.setInt(1, id)
                val resultSet = preparedStatement.executeQuery()
                resultSet.next()
                val count = resultSet.getInt(1)

                resultSet.close()
                preparedStatement.close()

                return count > 0
            } catch (e: SQLException) {
                e.printStackTrace()
            }

            return false
        }

        fun isValidTeacherId(id: Int): Boolean {
            val sql = "SELECT COUNT(*) FROM teachers WHERE id=?"

            try {
                val preparedStatement = connection.prepareStatement(sql)
                preparedStatement.setInt(1, id)
                val resultSet = preparedStatement.executeQuery()
                resultSet.next()
                val count = resultSet.getInt(1)

                resultSet.close()
                preparedStatement.close()

                return count > 0
            } catch (e: SQLException) {
                e.printStackTrace()
            }

            return false
        }

        fun isValidCourseId(id: Int): Boolean {
            val sql = "SELECT COUNT(*) FROM courses WHERE id=?"

            try {
                val preparedStatement = connection.prepareStatement(sql)
                preparedStatement.setInt(1, id)
                val resultSet = preparedStatement.executeQuery()
                resultSet.next()
                val count = resultSet.getInt(1)

                resultSet.close()
                preparedStatement.close()

                return count > 0
            } catch (e: SQLException) {
                e.printStackTrace()
            }

            return false
        }

        fun isValidEnrollmentId(id: Int): Boolean {
            val sql = "SELECT COUNT(*) FROM enrollments WHERE id = ?"

            try {
                val preparedStatement = connection.prepareStatement(sql)
                preparedStatement.setInt(1, id)
                val resultSet = preparedStatement.executeQuery()
                resultSet.next()
                val count = resultSet.getInt(1)

                resultSet.close()
                preparedStatement.close()

                return count > 0
            } catch (e: SQLException) {
                e.printStackTrace()
            }

            return false
        }
        fun isValidAdministratorId(id: Int): Boolean {
            val sql = "SELECT COUNT(*) FROM courses WHERE id=?"

            try {
                val preparedStatement = connection.prepareStatement(sql)
                preparedStatement.setInt(1, id)
                val resultSet = preparedStatement.executeQuery()
                resultSet.next()
                val count = resultSet.getInt(1)

                resultSet.close()
                preparedStatement.close()

                return count > 0
            } catch (e: SQLException) {
                e.printStackTrace()
            }

            return false
        }

//        //Validar Indormações nulas ou vazias
        fun isValidStudentInfo(name_student: String, date_of_birth: String, addrss: String): Boolean {
            return name_student.isNotBlank() && date_of_birth.isNotBlank() && addrss.isNotBlank()
        }
        fun isValidTeacherInfo(name_teacher: String, discipline: String): Boolean {
            return name_teacher.isNotBlank() && discipline.isNotBlank()
        }
        fun isValidCoursesInfo(name_course: String): Boolean {
            return name_course.isNotBlank()
        }
        fun isValidEnrollmentInfo(enrollment_date: String): Boolean{
            return enrollment_date.isNotBlank()
        }
        fun isValidAdministratorInfo(name: String, email: String): Boolean{
            return name.isNotBlank() && email.isNotBlank()
        }

       // Validar entrada de email
        fun isValidEmail(email: String): Boolean {
            return email.contains("@") && email.endsWith("@gmail.com")
        }


        fun isValidAdminCredentials(email_user: String, password_user: Int): Boolean {
            if (email_user.isBlank()) {
                println("O email do usuário e a senha não podem estar vazios.")
                return false
            }

            val sql = "SELECT COUNT(*) FROM administrator WHERE email_user=? AND password_user=?"

            try {
                val preparedStatement = connection.prepareStatement(sql)
                preparedStatement.setString(1, email_user)
                preparedStatement.setInt(2, password_user)
                val resultSet = preparedStatement.executeQuery()
                resultSet.next()
                val count = resultSet.getInt(1)

                resultSet.close()
                preparedStatement.close()

                return count > 0
            } catch (e: SQLException) {
                e.printStackTrace()
            }

            return false
        }
    }
}