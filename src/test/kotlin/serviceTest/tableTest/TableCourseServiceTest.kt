package serviceTest.tableTest

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import service.table.TableCourseService
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class TableCourseServiceTest {

    private lateinit var courseService: TableCourseService.Companion
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

        courseService = TableCourseService
        courseService.connection = mockConnection
    }

    @Test
    fun testAddCourseValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        courseService.addCourse("Math", 1)
        verify(mockStatement, times(1)).executeUpdate(anyString())
    }

    @Test
    fun testAddCourseNotValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        courseService.addCourse("", 0)
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testDeleteCourseValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1, 1)

        courseService.deleteCourse(1)
        verify(mockStatement, times(2)).executeUpdate(anyString())
    }

    @Test
    fun testDeleteCourseNotValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        courseService.deleteCourse(-1)
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testUpdateCourseValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        courseService.updateCourse(1, 2)
        verify(mockStatement, times(1)).executeUpdate(anyString())
    }

    @Test
    fun testUpdateCourseNotValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeUpdate(anyString())).thenReturn(1)

        courseService.updateCourse(-1, 0)
        verify(mockStatement, never()).executeUpdate(anyString())
    }

    @Test
    fun testListCourses() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        `when`(mockResultSet.next()).thenReturn(true, true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1, 2)
        `when`(mockResultSet.getString("name_courses")).thenReturn("Math", "Science")
        `when`(mockResultSet.getInt("responsible_teacher")).thenReturn(1, 2)

        courseService.listCourse()

        verify(mockStatement, times(1)).executeQuery(anyString())
        verify(mockResultSet, times(2)).getInt("id")
        verify(mockResultSet, times(2)).getString("name_courses")
        verify(mockResultSet, times(2)).getInt("responsible_teacher")
    }

    @Test
    fun testListSpecificCourseAndIdValid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        `when`(mockResultSet.next()).thenReturn(true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1)
        `when`(mockResultSet.getString("name_courses")).thenReturn("Math")
        `when`(mockResultSet.getInt("responsible_teacher")).thenReturn(1)

        courseService.listSpecificCourse(1)
        verify(mockStatement, times(1)).executeQuery(anyString())

        verify(mockResultSet, times(1)).getInt("id")
        verify(mockResultSet, times(1)).getString("name_courses")
        verify(mockResultSet, times(1)).getInt("responsible_teacher")
    }

    @Test
    fun testListSpecificCourseAndIdInvalid() {
        `when`(mockConnection.createStatement()).thenReturn(mockStatement)
        `when`(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)

        `when`(mockResultSet.next()).thenReturn(true, false)
        `when`(mockResultSet.getInt("id")).thenReturn(1)
        `when`(mockResultSet.getString("name_courses")).thenReturn("Math")
        `when`(mockResultSet.getInt("responsible_teacher")).thenReturn(1)

        courseService.listSpecificCourse(-1)
        verify(mockStatement, never()).executeQuery(anyString())
    }
}
