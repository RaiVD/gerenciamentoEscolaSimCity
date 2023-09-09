package service

import connection.Connect
import java.sql.SQLException

class QueryInDatabaseService {

    var connection = Connect().creatConnect()

    fun getStudentsInCourse(courseName: String) {
        try {
            val sql = """
                SELECT Students.name_student
                FROM Students
                INNER JOIN Enrollments ON Students.id = Enrollments.student_id
                INNER JOIN Courses ON Enrollments.course_id = Courses.id
                WHERE Courses.name_courses = ?
            """.trimIndent()

            val preparedStatement = connection.prepareStatement(sql)
            preparedStatement.setString(1, courseName)

            val resultSet = preparedStatement.executeQuery()
            var coursesFound = false


            while (resultSet.next()) {
                val studentName = resultSet.getString("name_student")
                println("Aluno matriculado em $courseName: $studentName")
                coursesFound = true
            }

            resultSet.close()
            preparedStatement.close()

            if (!coursesFound) {
                println("Nenhum aluno encontrado!")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun getCoursesTaughtByTeacher(teacherName: String) {
        try {
            val sql = """
                SELECT Courses.name_courses
                FROM Courses
                INNER JOIN Teachers ON Courses.responsible_teacher = Teachers.id
                WHERE Teachers.name_teacher = ?
            """.trimIndent()

            val preparedStatement = connection.prepareStatement(sql)
            preparedStatement.setString(1, teacherName)

            val resultSet = preparedStatement.executeQuery()
            var coursesFound = false

            while (resultSet.next()) {
                val courseName = resultSet.getString("name_courses")
                println("Curso ministrado por $teacherName: $courseName")
                coursesFound = true
            }

            resultSet.close()
            preparedStatement.close()

            if (!coursesFound) {
                println("Nenhum curso encontrado ministrado por $teacherName.!")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun getStudentsNotEnrolledInAnyCourse() {
        try {
            val sql = """
                SELECT Students.name_student
                FROM Students
                LEFT JOIN Enrollments ON Students.id = Enrollments.student_id
                WHERE Enrollments.student_id IS NULL;
            """.trimIndent()

            val statement = connection.createStatement()
            val resultSet = statement.executeQuery(sql)
            var coursesFound = false

            while (resultSet.next()) {
                val studentName = resultSet.getString("name_student")
                println("Aluno não matriculado em nenhum curso: $studentName")
                coursesFound = true
            }

            resultSet.close()
            statement.close()
            if (!coursesFound) {
                println("Nenhum aluno encontrado")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun getCoursesWithNoEnrollments() {
        try {
            val sql = """
                SELECT Courses.name_courses
                FROM Courses
                LEFT JOIN Enrollments ON Courses.id = Enrollments.course_id
                WHERE Enrollments.course_id IS NULL;
            """.trimIndent()

            val statement = connection.createStatement()
            val resultSet = statement.executeQuery(sql)
            var coursesFound = false


            while (resultSet.next()) {
                val courseName = resultSet.getString("name_courses")
                println("Curso sem alunos matriculados: $courseName")
                coursesFound = true
            }

            resultSet.close()
            statement.close()
            if (!coursesFound) {
                println("Nenhum curso encontrado")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun getStudentsEnrolledInMultipleCourses() {
        try {
            val sql = """
                SELECT Students.name_student
                FROM Students
                INNER JOIN (
                    SELECT student_id
                    FROM Enrollments
                    GROUP BY student_id
                    HAVING COUNT(course_id) > 1
                ) AS MultipleEnrollments ON Students.id = MultipleEnrollments.student_id;
            """.trimIndent()

            val statement = connection.createStatement()
            val resultSet = statement.executeQuery(sql)
            var coursesFound = false


            while (resultSet.next()) {
                val studentName = resultSet.getString("name_student")
                println("Aluno matriculado em mais de um curso: $studentName")
                coursesFound = true
            }
            resultSet.close()
            statement.close()
            if (!coursesFound) {
                println("Nenhum aluno encontrado")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }
}
