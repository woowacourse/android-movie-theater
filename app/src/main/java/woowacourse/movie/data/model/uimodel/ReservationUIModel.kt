package woowacourse.movie.data.model.uimodel

import woowacourse.movie.data.model.itemmodel.ReservationItemModel
import java.io.Serializable

data class ReservationUIModel(
    val movie: MovieUIModel,
    val tickets: TicketsUIModel
) :
    UIModel,
    Serializable {

    fun toItemModel(onClick: (ReservationUIModel) -> Unit): ReservationItemModel {
        return ReservationItemModel(this, onClick)
    }
}
