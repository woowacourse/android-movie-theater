package woowacourse.movie.domain

import domain.Tickets
import io.mockk.*
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class BlackTest {

    class Sample {
        fun test(str: String = "James"): String {
            return str        }

        fun run() {

        }
    }

    @Test
    fun `mockk_test1`() {
        val mockk = mockk<Sample>()
        every { mockk.test() } returns "Hello World"
        assertEquals("Hello World", mockk.test())
    }

    @Test
    fun `mockk_test2`() {
        val mockk = mockk<Sample>()
        every { mockk.test() } throws Exception()
        assertThrows(Exception::class.java) { mockk.test() }
    }

    @Test
    fun `mockk_verify`() {
        // given
        val mockk = mockk<Sample>()
        every { mockk.run() } just Runs

        // when
        mockk.run()
        mockk.run()

        // then
        verify(exactly = 2) { mockk.run() }
        verify(exactly = 0) { mockk.test("james") }
    }
}



class BlankTest() {
    class Tickets(val headCount: Int, val price: Int)

    class TicketReceiver {
        fun run(): Tickets {
            // TODO: 티켓을 받아오는 로직이 있다고 가정
            val tickets = Tickets(1, 10000) // 티켓을 받아왔다고 가정
            return tickets
        }
    }

    class Calculator {
        fun calculate(tickets: Tickets): Int {
            return tickets.headCount * tickets.price
        }
    }

    @Test
    fun `TicketReceiver_TEST`() {
        val mockk = mockk<TicketReceiver>()
        every { mockk.run() } returns Tickets(3, 10000)
        val calculator = Calculator()
        assertEquals(30000, calculator.calculate(mockk.run()))
    }

}