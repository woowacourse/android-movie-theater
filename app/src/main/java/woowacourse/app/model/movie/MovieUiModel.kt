package woowacourse.app.model.movie

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize
import woowacourse.app.model.MainData
import woowacourse.app.ui.main.home.adapter.MainViewType
import java.time.LocalDate

@Parcelize
data class MovieUiModel(
    override val id: Long,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val screeningDates: List<LocalDate>,
    val runningTime: Int,
    val description: String,
    @DrawableRes val thumbnail: Int,
    @DrawableRes val poster: Int,
) : MainData(), Parcelable {
    override val mainViewType: MainViewType = MainViewType.CONTENT
}
