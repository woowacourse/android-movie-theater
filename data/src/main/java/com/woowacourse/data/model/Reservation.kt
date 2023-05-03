package com.woowacourse.data.model

typealias DataReservation = Reservation

data class Reservation(
    val movieTitle: String,
    val movieDate: DataMovieDate,
    val movieTime: DataMovieTime,
    val ticket: DataTicket,
    val seats: DataPickedSeats,
    val ticketPrice: DataTicketPrice,
)
