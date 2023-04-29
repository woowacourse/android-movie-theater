package com.example.domain.discountPolicy.policy

import com.example.domain.model.Money
import com.example.domain.model.Movie
import com.example.domain.model.seat.SeatPosition
import java.time.LocalDateTime

object MovieDayPolicy : Policy() {
    override fun isDiscountable(
        movie: Movie,
        dateTime: LocalDateTime,
        seatPosition: SeatPosition
    ): Boolean {
        return dateTime.dayOfMonth in MOVIE_DAYS
    }

    override fun applyDiscount(money: Money): Money = money * MOVIE_DAY_DISCOUNT

    private val MOVIE_DAYS = listOf(10, 20, 30)
    private const val MOVIE_DAY_DISCOUNT = 0.9f
}
