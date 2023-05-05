package domain.seatPolicy

import domain.Seat

interface SeatPolicy {
    val seatRank: SeatRank
    fun checkCondition(seat: Seat): Boolean
}
