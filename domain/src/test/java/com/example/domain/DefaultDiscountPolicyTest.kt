package com.example.domain

import com.example.domain.discountPolicy.DefaultDiscountPolicy
import com.example.domain.model.Money
import com.example.domain.model.Movie
import com.example.domain.model.seat.SeatPosition
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime

class DefaultDiscountPolicyTest {
    private val movie = Movie(0, "", LocalDate.now(), LocalDate.now(), 120, "")
    private val seatPosition = SeatPosition(2, 3)

    @Test
    fun `무비데이(10, 20, 30)면 10% 할인을 받는다`() {
        // given
        val dateTime: LocalDateTime = LocalDateTime.of(2023, 4, 10, 15, 0, 0)

        // when
        val actual = DefaultDiscountPolicy().discount(movie, dateTime, seatPosition)
        val expected = Money(9_000)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `영화 시간대가 아침이면 2000원 할인을 받는다`() {
        // given
        val dateTime: LocalDateTime = LocalDateTime.of(2023, 4, 29, 9, 0, 0)

        // when
        val actual = DefaultDiscountPolicy().discount(movie, dateTime, seatPosition)
        val expected = Money(8_000)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `영화 시간대가 야간이면 2000원 할인을 받는다`() {
        // given
        val dateTime: LocalDateTime = LocalDateTime.of(2023, 4, 29, 23, 0, 0)

        // when
        val actual = DefaultDiscountPolicy().discount(movie, dateTime, seatPosition)
        val expected = Money(8_000)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `무비데이(10, 20, 30)이고 아침이면 10%를 할인 받고 추가로 2000원 할인을 더 받는다`() {
        // given
        val dateTime: LocalDateTime = LocalDateTime.of(2023, 4, 20, 9, 0, 0)

        // when
        val actual = DefaultDiscountPolicy().discount(movie, dateTime, seatPosition)

        // then
        val expected = Money(7_000)
        assertThat(actual).isEqualTo(expected)
    }
}
