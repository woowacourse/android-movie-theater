package com.example.domain.model

import java.time.LocalDateTime

class Tickets(
    tickets: List<Ticket>
) {
    private val _tickets: List<Ticket> = tickets.toList()
    val tickets: List<Ticket>
        get() = _tickets.toList()

    val theater: Theater
        get() = _tickets.first().theater

    val movie: Movie
        get() = _tickets.first().movie

    val dateTime: LocalDateTime
        get() = _tickets.first().dateTime

    val getTotalDiscountedMoney: Money = Money(_tickets.sumOf { it.discountedMoney.value })

    init {
        require(tickets.isNotEmpty()) { ERROR_TICKETS_IS_EMPTY }
        val movie = tickets.first().movie
        val dateTime = tickets.first().dateTime
        require(tickets.all { it.movie == movie }) { ERROR_TICKETS_IS_DIFFERENT_EXIST_MOVIE }
        require(tickets.all { it.dateTime == dateTime }) { ERROR_TICKETS_IS_DIFFERENT_EXIST_DATETIME }
    }

//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (javaClass != other?.javaClass) return false
//
//        other as Tickets
//
//        if (_tickets != other._tickets) return false
//        if (getTotalDiscountedMoney != other.getTotalDiscountedMoney) return false
//
//        return true
//    }
//
//    override fun hashCode(): Int {
//        var result = _tickets.hashCode()
//        result = 31 * result + getTotalDiscountedMoney.hashCode()
//        return result
//    }

    companion object {
        private const val ERROR_TICKETS_IS_EMPTY = "[ERROR] 티켓이 비었습니다"
        private const val ERROR_TICKETS_IS_DIFFERENT_EXIST_MOVIE = "[ERROR] 티켓 중 다른 영화가 있습니다"
        private const val ERROR_TICKETS_IS_DIFFERENT_EXIST_DATETIME = "[ERROR] 티켓 중 다른 날짜가 있습니다"
    }
}
