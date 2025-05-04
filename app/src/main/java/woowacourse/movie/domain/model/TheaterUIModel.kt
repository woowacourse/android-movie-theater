package woowacourse.movie.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.view.model.MovieUiModel
import woowacourse.movie.view.model.toLocalDate

@Parcelize
data class TheaterUIModel(
    val name: String,
    val movie: MovieUiModel,
    val timeSlotCount: Int,
) : Parcelable

fun TheaterUIModel.toReservationUiModel() =
    ReservationUiModel(
        title = this.movie.name,
        movieDate =
            MovieDate(
                this.movie.startDate.toLocalDate(),
                this.movie.endDate.toLocalDate(),
            ),
        movieTime = "",
        ticketCount = TicketCount().value,
        theaterName = this.name,
    )
