package woowacourse.movie.data.model.uimodel

import woowacourse.movie.data.model.ReservationItemModel
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
