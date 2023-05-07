package woowacourse.movie.data.model.uimodel

import woowacourse.movie.data.model.itemmodel.TheaterItemModel
import java.io.Serializable
import java.time.LocalTime

data class TheaterUiModel(
    val name: String,
    val timeTable: List<LocalTime>
) : UiModel, Serializable {

    fun toItemModel(onClick: (TheaterUiModel) -> Unit): TheaterItemModel {
        return TheaterItemModel(this, onClick)
    }
}
