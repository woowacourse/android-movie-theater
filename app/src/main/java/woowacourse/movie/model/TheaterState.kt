package woowacourse.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.feature.movieList.bottomSheet.TheaterItemModel

@Parcelize
data class TheaterState(
    val theaterName: String,
    val screenInfos: List<ScreenMovieInfo>
) : Parcelable {
    fun toItemModel(onClick: (TheaterState) -> Unit): TheaterItemModel {
        return TheaterItemModel(this, onClick)
    }
}
