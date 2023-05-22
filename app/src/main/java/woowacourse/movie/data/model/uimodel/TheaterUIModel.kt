package woowacourse.movie.data.model.uimodel

import woowacourse.movie.data.model.itemmodel.TheaterItemModel
import java.io.Serializable
import java.time.LocalTime

data class TheaterUIModel(
    val name: String,
    val timeTable: List<LocalTime>
) : UIModel, Serializable {

    fun toItemModel(onClick: (TheaterUIModel) -> Unit): TheaterItemModel {
        return TheaterItemModel(this, onClick)
    }
}
