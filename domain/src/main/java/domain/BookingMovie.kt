package domain

import domain.movieinfo.MovieDate
import domain.movieinfo.MovieTime

data class BookingMovie(
    val title: String,
    val date: MovieDate,
    val time: MovieTime,
    val ticketCount: TicketCount,
    val seats: String,
    val theaterName: String,
    val price: Int,
)
