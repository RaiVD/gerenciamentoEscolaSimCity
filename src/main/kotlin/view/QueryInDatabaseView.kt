package view

import model.InputUserModel
import service.QueryInDatabaseService
import service.table.TableCourseService

class QueryInDatabaseView {
    val query = QueryInDatabaseService()

    private val inputUserModel = InputUserModel()
    fun startOption() {
        var option: Int
        do {
            menu()
            option = inputUserModel.readIntFromUser("Qual opção você deseja: ")
            when (option) {
                0 -> AdminView()
                1 -> studentsInCourse()
                2 -> coursesTaughtByTeacher()
                3 -> studentsNotEnrolledInAnyCourse()
                4 -> coursesWithNoEnrollments()
                5 -> studentsEnrolledInMultipleCourses()
                else -> println("Opção inválida, tente novamente!")
            }
        } while (option != 0)
    }
    fun studentsInCourse(){
        val courseName = inputUserModel.readStringFromUser("Qual o nome do curso: ")
        query.getStudentsInCourse(courseName)
    }
    fun coursesTaughtByTeacher(){
        val teacherName = inputUserModel.readStringFromUser("Qual o nome do professor: ")
        query.getCoursesTaughtByTeacher(teacherName)
    }

    fun studentsNotEnrolledInAnyCourse(){
        query.getStudentsNotEnrolledInAnyCourse()
    }

    fun coursesWithNoEnrollments(){
        query.getCoursesWithNoEnrollments()
    }

    fun studentsEnrolledInMultipleCourses(){
        query.getStudentsEnrolledInMultipleCourses()
    }

    private fun menu() {
        println(
            "\n0. Menu do administrador |" +
                    " 1. Aluno matriculado em curso especifico |" +
                    " 2. Curso ministrado por um professor |" +
                    " 3. Alunos não matriculados em nenhum curso |" +
                    " 4. Cursos sem alunos matriculados |" +
                    " 5. Alunos matriculados em mais de um curso"
        )
    }
}