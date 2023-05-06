package woowacourse.movie.presentation.model.movieitem

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import woowacourse.movie.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Parcelize
data class Movie(
    val id: Int = 0,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val runningTime: Int,
    val introduce: String,
    @DrawableRes val thumbnail: Int,
) : ListItem, Parcelable {
    @IgnoredOnParcel
    val formattedStartDate: String =
        startDate.format(DateTimeFormatter.ofPattern(MOVIE_DATE_PATTERN))

    @IgnoredOnParcel
    val formattedEndDate: String =
        endDate.format(DateTimeFormatter.ofPattern(MOVIE_DATE_PATTERN))

    companion object {
        internal const val MOVIE_DATE_PATTERN = "yyyy.MM.dd"

        private const val DEFAULT_LOAD_SIZE = 20
        private var loadedSize = 0
        private val dummy: List<Movie> = List(10_000) { id ->
            Movie(
                id,
                "해리 포터와 마법사의 돌 $id",
                LocalDate.of(2023, 4, 1),
                LocalDate.of(2023, 4, 30),
                152,
                "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                R.drawable.img_sample_movie_thumbnail1
            )
        }

        fun provideDummy(loadSize: Int = DEFAULT_LOAD_SIZE): List<Movie> {
            val temp: MutableList<Movie> = mutableListOf()

            while (temp.size < loadSize) {
                if (isAllLoaded()) return temp
                addDummy(temp)
            }
            return temp
        }

        private fun isAllLoaded() = loadedSize >= dummy.size

        private fun addDummy(tempDummy: MutableList<Movie>) {
            tempDummy.add(dummy[loadedSize])
            loadedSize++
        }
    }

    override fun isAd(): Boolean = false
}
