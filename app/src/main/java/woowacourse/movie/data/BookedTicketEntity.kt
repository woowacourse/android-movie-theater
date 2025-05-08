package woowacourse.movie.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import woowacourse.movie.domain.model.BookedTicket
import woowacourse.movie.domain.model.Headcount
import woowacourse.movie.domain.model.MovieSchedule
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.Seats

@Entity(tableName = "booked_tickets")
data class BookedTicketEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "theater_name") val theaterName: String,
    @ColumnInfo(name = "movie_title") val movieTitle: String,
    @ColumnInfo(name = "screening_date_time") val screeningDateTime: LocalDateTime,
    @ColumnInfo(name = "select_seats") val selectSeats: Set<Seat>,
    @ColumnInfo(name = "headcount") val headcount: Int,
)

fun BookedTicketEntity.toBookedTicket(): BookedTicket {
    return BookedTicket(
        theaterName = this.theaterName,
        movieTitle = this.movieTitle,
        movieSchedule =
            MovieSchedule(
                this.screeningDateTime,
                Seats(this.selectSeats.toMutableSet()),
            ),
        headcount = Headcount(headcount),
    )
}
