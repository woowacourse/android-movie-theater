package woowacourse.movie.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Title(
    val value: String,
) : Parcelable {
    init {
        require(value.isNotEmpty()) { TitleResult.EmptyMovieTitle }
        require(value.isNotBlank()) { TitleResult.BlankMovieTitle }
    }
}

sealed class TitleResult {
    data class Success(val title : Title) : TitleResult()
    object EmptyMovieTitle : TitleResult()
    object BlankMovieTitle : TitleResult()
}
