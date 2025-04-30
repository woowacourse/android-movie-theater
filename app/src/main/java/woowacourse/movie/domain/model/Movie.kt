package woowacourse.movie.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val title: String,
    val startDate: MovieDate,
    val endDate: MovieDate,
    val runningTime: Int,
) : Parcelable {
    companion object {
        val movie0 = Movie("해리 포터와 마법사의 돌", MovieDate(2025, 4, 1), MovieDate(2025, 4, 25), 152)
        val movie1 = Movie("해리 포터와 비밀의 방", MovieDate(2025, 4, 1), MovieDate(2025, 4, 28), 162)
        val movie2 = Movie("해리 포터와 아즈카반의 죄수", MovieDate(2025, 5, 1), MovieDate(2025, 5, 31), 141)
        val movie3 = Movie("해리 포터와 불의 잔", MovieDate(2025, 6, 1), MovieDate(2025, 6, 30), 157)
        val movie4 = Movie("레디 플레이어 원", MovieDate(2025, 5, 11), MovieDate(2025, 9, 28), 140)

        val movies = listOf(movie0, movie1, movie2, movie3, movie4)
    }
}
