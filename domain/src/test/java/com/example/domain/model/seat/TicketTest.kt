package com.example.domain.model.seat

import com.example.domain.model.Money
import com.example.domain.model.Ticket
import com.example.domain.repository.dataSource.movieDataSources
import com.example.domain.repository.dataSource.theaterDataSources
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class TicketTest {
    @Test
    fun `티켓 좌석 정보로 랭크와 해당 티켓의 원본 가격을 알 수 있다`() {
        val ticket = Ticket(
            theaterDataSources[0],
            movieDataSources[0],
            LocalDateTime.of(2023, 4, 20, 12, 0, 0),
            SeatPosition(2, 3),
            Money(9000)
        )
        val actual = ticket.originMoney
        val expected = Money(10000)
        assertThat(actual).isEqualTo(expected)
    }
}
