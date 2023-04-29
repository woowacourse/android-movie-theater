package com.example.domain.model.seat

import com.example.domain.model.Money

enum class SeatRank(val money: Money) {
    B(Money(10000)),
    S(Money(15000)),
    A(Money(12000));

    companion object {
        fun from(seatPosition: SeatPosition): SeatRank {
            return when (seatPosition.row) {
                SeatRow.A, SeatRow.B -> B
                SeatRow.C, SeatRow.D -> S
                SeatRow.E -> A
            }
        }
    }
}
