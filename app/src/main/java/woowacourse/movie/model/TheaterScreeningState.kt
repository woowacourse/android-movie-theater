package woowacourse.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.feature.movieList.bottomSheet.TheaterItemModel

@Parcelize
data class TheaterScreeningState(
    val theaterName: String,
    val screeningInfos: List<ScreeningMovieState>
) : Parcelable {
    fun toItemModel(onClick: (TheaterScreeningState) -> Unit): TheaterItemModel {
        return TheaterItemModel(this, onClick)
    }
}
