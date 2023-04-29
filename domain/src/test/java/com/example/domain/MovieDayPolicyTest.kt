package com.example.domain

import com.example.domain.discountPolicy.policy.MovieDayPolicy
import com.example.domain.model.Money
import com.example.domain.model.Movie
import com.example.domain.model.seat.SeatPosition
import com.example.domain.model.seat.SeatRank
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class MovieDayPolicyTest {
    @Test
    fun `무비 데이이면 할인을 받을 수 있다`() {
        val date = LocalDate.of(2023, 4, 10)
        val time = LocalTime.of(16, 0)
        val dateTime = LocalDateTime.of(date, time)
        val mockMovie = Movie(
            0,
            "title",
            LocalDate.now(),
            LocalDate.now(),
            120,
            ""
        )
        val position = SeatPosition(3, 1) // 15000원
        val rank = SeatRank.from(position)

        val actual = MovieDayPolicy.discount(rank.money, mockMovie, dateTime, position)
        val expected = Money(13500)
        assertThat(actual).isEqualTo(expected)
    }
}
