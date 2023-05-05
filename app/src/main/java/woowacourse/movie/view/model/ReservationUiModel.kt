package woowacourse.movie.view.model

import woowacourse.movie.fragment.reservationlist.ReservationItemModel
import java.io.Serializable

data class ReservationUiModel(
    val movie: MovieUiModel,
    val tickets: TicketsUiModel
) :
    UiModel,
    Serializable {

    fun toItemModel(onClick: (ReservationUiModel) -> Unit): ReservationItemModel {
        return ReservationItemModel(this, onClick)
    }
}
