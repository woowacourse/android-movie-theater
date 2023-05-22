package woowacourse.movie.data.model.uimodel

import androidx.annotation.DrawableRes
import woowacourse.movie.data.model.itemmodel.MovieItemModel
import java.io.Serializable
import java.time.LocalDate
import java.time.Period

data class MovieUIModel(
    @DrawableRes val picture: Int,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val runningTime: Int,
    val description: String,
) : UIModel, Serializable {
    fun getDateList(): List<LocalDate> {
        return (0..Period.between(startDate, endDate).days).map { startDate.plusDays(it.toLong()) }
    }

    fun toItemModel(onClick: (MovieUIModel) -> Unit): MovieItemModel {
        return MovieItemModel(this, onClick)
    }
}
