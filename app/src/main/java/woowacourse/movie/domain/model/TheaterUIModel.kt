package woowacourse.movie.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.view.model.MovieUiModel

@Parcelize
data class TheaterUIModel(
    val name: String,
    val movie: MovieUiModel,
    val timeSlotCount: Int,
) : Parcelable

fun TheaterUIModel.toReservationUiModel() =
    ReservationUiModel(
        title = this.movie.name,
        movieDate = "",
        movieTime = "",
        ticketCount = TicketCount().value,
        theaterName = this.name,
    )
