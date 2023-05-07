package woowacourse.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.feature.movieList.bottomSheet.TheaterItemModel

@Parcelize
data class TheaterScreeningInfoState(
    val theater: TheaterState,
    val screeningInfos: List<ScreeningMovieTimesState>
) : Parcelable {
    fun toItemModel(onClick: (TheaterScreeningInfoState) -> Unit): TheaterItemModel {
        return TheaterItemModel(this, onClick)
    }
}
