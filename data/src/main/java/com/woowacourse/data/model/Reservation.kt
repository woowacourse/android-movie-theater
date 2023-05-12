package com.woowacourse.data.model

typealias DataReservation = Reservation

data class Reservation(
    val movieTitle: String,
    val theaterName: String,
    val movieDate: DataMovieDate,
    val movieTime: DataMovieTime,
    val ticket: DataTicket,
    val seats: DataPickedSeats,
    val ticketPrice: DataTicketPrice,
) {
    internal val ticketCount: Int = ticket.count
    internal val totalPrice: Int = ticketPrice.amount
    internal val selectedSeats: List<DataSeat> = seats.items
    internal val formattedMovieDate: String = movieDate.transform()
    internal val formattedMovieTime: String = movieTime.transform()
}
