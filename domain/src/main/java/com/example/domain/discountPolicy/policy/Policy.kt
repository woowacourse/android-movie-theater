package com.example.domain.discountPolicy.policy

import com.example.domain.model.Money
import com.example.domain.model.Movie
import com.example.domain.model.seat.SeatPosition
import java.time.LocalDateTime

sealed class Policy {
    fun discount(
        money: Money,
        movie: Movie,
        dateTime: LocalDateTime,
        seatPosition: SeatPosition
    ): Money {
        return if (isDiscountable(movie, dateTime, seatPosition)) applyDiscount(money)
        else money
    }

    protected abstract fun isDiscountable(
        movie: Movie,
        dateTime: LocalDateTime,
        seatPosition: SeatPosition
    ): Boolean

    protected abstract fun applyDiscount(
        money: Money
    ): Money
}
