package com.example.domain.discountPolicy.policy

import com.example.domain.model.Money
import com.example.domain.model.Movie
import com.example.domain.model.seat.SeatPosition
import java.time.LocalDateTime

object JoJoNightPolicy : Policy() {
    override fun isDiscountable(
        movie: Movie,
        dateTime: LocalDateTime,
        seatPosition: SeatPosition
    ): Boolean {
        return dateTime.hour in TIME_CONDITION
    }

    override fun applyDiscount(money: Money): Money = money - DISCOUNT

    private val TIME_CONDITION = (0..10) + (20..23)
    private val DISCOUNT = Money(2000)
}
