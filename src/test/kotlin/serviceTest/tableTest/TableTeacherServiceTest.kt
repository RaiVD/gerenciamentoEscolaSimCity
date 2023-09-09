package serviceTest.tableTest

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import service.table.TableTeacherService
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class TableTeacherServiceTest {

    private lateinit var teacherService: TableTeacherService.Companion
    private lateinit var mockConnection: Connection
    private lateinit var mockStatement: Statement
    private lateinit var mockPreparedStatement: PreparedStatement
    private lateinit var mockResultSet: ResultSet

    @BeforeEach
    fun setUp() {
        mockConnection = mock(Connection::class.java)
        mockStatement = mock(Statement::class.java)
        mockPreparedStatement = mock(PreparedStatement::class.java)
        mockResultSet = mock(ResultSet::class.java)

        teacherService = TableTeacherService
        teacherService.connection = mockConnection
    }

    @Test
    fun testAddTeacherValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        teacherService.addTeacher("John Doe", "Math")
        verify(mockStatement, times(1)).executeUpdate(anyString())
    }

    @Test
    fun testAddTeacherNotValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        teacherService.addTeacher("", "")
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testDeleteTeacherValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1, 1, 1)

        teacherService.deleteTeacher(1)
        verify(mockStatement, times(3)).executeUpdate(anyString())
    }

    @Test
    fun testDeleteTeacherNotValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        teacherService.deleteTeacher(-1)
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testUpdateTeacherValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        teacherService.updateTeacher(1, "Physics")
        verify(mockStatement, times(1)).executeUpdate(anyString())
    }

    @Test
    fun testUpdateTeacherNotValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        teacherService.updateTeacher(-1, "Chemistry")
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testListTeachers() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        `when`(mockResultSet.next()).thenReturn(true, true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1, 2)
        `when`(mockResultSet.getString("name_teacher")).thenReturn("John Doe", "Jane Smith")
        `when`(mockResultSet.getString("discipline")).thenReturn("Math", "Physics")

        teacherService.listTeacher()

        verify(mockStatement, times(1)).executeQuery(anyString())
        verify(mockResultSet, times(2)).getInt("id")
        verify(mockResultSet, times(2)).getString("name_teacher")
        verify(mockResultSet, times(2)).getString("discipline")
    }

    @Test
    fun testListSpecificTeacherAndIdValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        `when`(mockResultSet.next()).thenReturn(true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1)
        `when`(mockResultSet.getString("name_teacher")).thenReturn("John Doe")
        `when`(mockResultSet.getString("discipline")).thenReturn("Math")

        teacherService.listSpecificTeacher(1)
        verify(mockStatement, times(1)).executeQuery(anyString())

        verify(mockResultSet, times(1)).getInt("id")
        verify(mockResultSet, times(1)).getString("name_teacher")
        verify(mockResultSet, times(1)).getString("discipline")
    }

    @Test
    fun testListSpecificTeacherAndIdInvalid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        `when`(mockResultSet.next()).thenReturn(true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1)
        `when`(mockResultSet.getString("name_teacher")).thenReturn("John Doe")
        `when`(mockResultSet.getString("discipline")).thenReturn("Math")

        teacherService.listSpecificTeacher(-1)
        verify(mockStatement, never()).executeQuery(anyString())
    }
}
