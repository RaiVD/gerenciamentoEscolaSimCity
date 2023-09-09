package serviceTest.tableTest

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import service.table.TableEnrollmentService
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class TableEnrollmentServiceTest {

    private lateinit var enrollmentService: TableEnrollmentService.Companion
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

        enrollmentService = TableEnrollmentService
        enrollmentService.connection = mockConnection
    }

    @Test
    fun testAddEnrollmentValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        enrollmentService.addEnrollment(1, 2, "2023-09-08")
        verify(mockStatement, times(1)).executeUpdate(anyString())
    }

    @Test
    fun testAddEnrollmentNotValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        enrollmentService.addEnrollment(-1, -2, "")
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testDeleteEnrollmentValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1, 1)

        enrollmentService.deleteEnrollment(1)
        verify(mockStatement, times(2)).executeUpdate(anyString())
    }

    @Test
    fun testDeleteEnrollmentNotValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        enrollmentService.deleteEnrollment(-1)
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testListEnrollments() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        `when`(mockResultSet.next()).thenReturn(true, true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1, 2)
        `when`(mockResultSet.getInt("student_id")).thenReturn(1, 2)
        `when`(mockResultSet.getInt("course_id")).thenReturn(1, 2)
        `when`(mockResultSet.getString("enrollment_date")).thenReturn("2023-09-08", "2023-09-09")

        enrollmentService.listEnrollment()

        verify(mockStatement, times(1)).executeQuery(anyString())
        verify(mockResultSet, times(2)).getInt("id")
        verify(mockResultSet, times(2)).getInt("student_id")
        verify(mockResultSet, times(2)).getInt("course_id")
        verify(mockResultSet, times(2)).getString("enrollment_date")
    }

    @Test
    fun testListSpecificEnrollmentAndIdValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        `when`(mockResultSet.next()).thenReturn(true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1)
        `when`(mockResultSet.getInt("student_id")).thenReturn(1)
        `when`(mockResultSet.getInt("course_id")).thenReturn(1)
        `when`(mockResultSet.getString("enrollment_date")).thenReturn("2023-09-08")

        enrollmentService.listSpecificEnrollment(1)
        verify(mockStatement, times(1)).executeQuery(anyString())

        verify(mockResultSet, times(1)).getInt("id")
        verify(mockResultSet, times(1)).getInt("student_id")
        verify(mockResultSet, times(1)).getInt("course_id")
        verify(mockResultSet, times(1)).getString("enrollment_date")
    }

    @Test
    fun testListSpecificEnrollmentAndIdInvalid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        `when`(mockResultSet.next()).thenReturn(true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1)
        `when`(mockResultSet.getInt("student_id")).thenReturn(1)
        `when`(mockResultSet.getInt("course_id")).thenReturn(1)
        `when`(mockResultSet.getString("enrollment_date")).thenReturn("2023-09-08")

        enrollmentService.listSpecificEnrollment(-1)
        verify(mockStatement, never()).executeQuery(anyString())
    }
}
