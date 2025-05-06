package woowacourse.movie.view.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.domain.model.Seats

@Parcelize
class SeatsUiModel(
    val value: List<SeatUiModel>,
) : Parcelable {
    val size: Int
        get() = value.size

    fun labels(): List<String> = value.map { it.label }

    val totalPrice: Int
        get() = value.sumOf { it.price }
}

fun Seats.toPresentation(): SeatsUiModel =
    SeatsUiModel(
        value = this.value.map { it.toPresentation() },
    )
