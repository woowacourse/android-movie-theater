package woowacourse.movie.domain.model.home

import androidx.annotation.DrawableRes
import woowacourse.movie.R
import java.time.LocalDate

sealed class HomeItem {
    data class MovieItem(
        val id: Long,
        val title: String,
        val posterResourceId: Int,
        val firstScreeningDate: LocalDate,
        val runningTime: Int,
        val description: String
    ) : HomeItem()

    data class AdvertisementItem(
        @DrawableRes
        val resourceId: Int = R.drawable.advertisement
    ) : HomeItem()
}
