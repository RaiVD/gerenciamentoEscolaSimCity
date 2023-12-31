package serviceTest.tableTest

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import service.table.TableStudentService
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class TableStudentServiceTest {

    private lateinit var studentService: TableStudentService.Companion
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

        studentService = TableStudentService
        studentService.connection = mockConnection
    }

    @Test
    fun testAddStudentValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        studentService.addStudent("John Doe", "2000-01-01", "123 Main St")
        verify(mockStatement, times(1)).executeUpdate(anyString())
    }

    @Test
    fun testAddStudentNotValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        studentService.addStudent("", "", "")
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testDeleteStudentValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1, 1)

        studentService.deleteStudent(2)
        verify(mockStatement, times(2)).executeUpdate(anyString())
    }

    @Test
    fun testDeleteStudentNotValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        studentService.deleteStudent(-1)
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testUpdateStudentValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        studentService.updateStudent(2, "456 Elm St")
        verify(mockStatement, times(1)).executeUpdate(anyString())
    }

    @Test
    fun testUpdateStudentInvalid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        studentService.updateStudent(-1, "456 Elm St")
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testListStudents() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        `when`(mockResultSet.next()).thenReturn(true, true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1, 2)
        `when`(mockResultSet.getString("name_student")).thenReturn("John Doe", "Jane Smith")
        `when`(mockResultSet.getString("date_of_birth")).thenReturn("2000-01-01", "1999-12-31")
        `when`(mockResultSet.getString("address")).thenReturn("123 Main St", "456 Elm St")

        studentService.listStudent()

        verify(mockStatement, times(1)).executeQuery(anyString())
        verify(mockResultSet, times(2)).getInt("id")
        verify(mockResultSet, times(2)).getString("name_student")
        verify(mockResultSet, times(2)).getString("date_of_birth")
        verify(mockResultSet, times(2)).getString("address")
    }

    @Test
    fun testListSpecificStudentValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        `when`(mockResultSet.next()).thenReturn(true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1)
        `when`(mockResultSet.getString("name_student")).thenReturn("John Doe")
        `when`(mockResultSet.getString("date_of_birth")).thenReturn("2000-01-01")
        `when`(mockResultSet.getString("address")).thenReturn("123 Main St")

        studentService.listSpecificStudent(2)

        verify(mockStatement, times(1)).executeQuery(anyString())

        verify(mockResultSet, times(1)).getInt("id")
        verify(mockResultSet, times(1)).getString("name_student")
        verify(mockResultSet, times(1)).getString("date_of_birth")
        verify(mockResultSet, times(1)).getString("address")
    }

    @Test
    fun testListSpecificStudentInvalid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        studentService.listSpecificStudent(-1)

        verify(mockStatement, never()).executeQuery(anyString())

        verify(mockResultSet, never()).getInt("id")
        verify(mockResultSet, never()).getString("name_student")
        verify(mockResultSet, never()).getString("date_of_birth")
        verify(mockResultSet, never()).getString("address")
    }
}
