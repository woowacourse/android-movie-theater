package woowacourse.movie.data

import woowacourse.movie.domain.model.Advertisement
import woowacourse.movie.domain.model.Content
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.MovieDate
import woowacourse.movie.domain.model.MovieTime
import woowacourse.movie.domain.model.Screening
import woowacourse.movie.domain.model.Screenings

object ContentService {
    private val movie0 = Movie(0, "해리 포터와 마법사의 돌", MovieDate(2025, 4, 1), MovieDate(2025, 4, 25), 152)
    private val movie1 = Movie(1, "해리 포터와 비밀의 방", MovieDate(2025, 4, 1), MovieDate(2025, 4, 28), 162)
    private val movie2 = Movie(2, "해리 포터와 아즈카반의 죄수", MovieDate(2025, 5, 1), MovieDate(2025, 5, 31), 141)
    private val movie3 = Movie(3, "해리 포터와 불의 잔", MovieDate(2025, 6, 1), MovieDate(2025, 6, 30), 157)
    private val movie4 = Movie(4, "레디 플레이어 원", MovieDate(2025, 5, 11), MovieDate(2025, 9, 28), 140)

    val movies = listOf(movie0, movie1, movie2, movie3, movie4)

    private const val THEATER_NAME_0: String = "CGV명동"
    private const val THEATER_NAME_1: String = "CGV동대문"
    private const val THEATER_NAME_2: String = "CGV청담씨네시티"
    private const val THEATER_NAME_3: String = "CGV명동역 씨네라이브러리"
    private const val THEATER_NAME_4: String = "CINE de CHEF 용산아이파크몰"

    private const val CONTENT_SWAP_SIZE = 3

    val screenings =
        Screenings(
            listOf(
                Screening(
                    movie0,
                    THEATER_NAME_0,
                    listOf(MovieTime(9, 0), MovieTime(12, 0), MovieTime(15, 0)),
                ),
                Screening(
                    movie0,
                    THEATER_NAME_1,
                    listOf(MovieTime(10, 0), MovieTime(13, 0)),
                ),
                Screening(
                    movie0,
                    THEATER_NAME_2,
                    listOf(
                        MovieTime(11, 0),
                        MovieTime(14, 0),
                        MovieTime(17, 0),
                        MovieTime(20, 0),
                    ),
                ),
                Screening(
                    movie1,
                    THEATER_NAME_3,
                    listOf(MovieTime(11, 0), MovieTime(14, 0), MovieTime(17, 0)),
                ),
                Screening(
                    movie1,
                    THEATER_NAME_4,
                    listOf(MovieTime(10, 0), MovieTime(12, 0), MovieTime(18, 0)),
                ),
                Screening(
                    movie2,
                    THEATER_NAME_2,
                    listOf(MovieTime(9, 0), MovieTime(11, 0)),
                ),
                Screening(
                    movie2,
                    THEATER_NAME_3,
                    listOf(MovieTime(13, 0), MovieTime(15, 0)),
                ),
                Screening(
                    movie2,
                    THEATER_NAME_4,
                    listOf(MovieTime(17, 0), MovieTime(19, 0)),
                ),
                Screening(
                    movie2,
                    THEATER_NAME_0,
                    listOf(MovieTime(21, 0)),
                ),
                Screening(
                    movie3,
                    THEATER_NAME_4,
                    listOf(MovieTime(10, 0), MovieTime(13, 0)),
                ),
                Screening(
                    movie3,
                    THEATER_NAME_1,
                    listOf(MovieTime(11, 0), MovieTime(14, 0), MovieTime(17, 0)),
                ),
                Screening(
                    movie3,
                    THEATER_NAME_3,
                    listOf(MovieTime(15, 0), MovieTime(18, 0)),
                ),
                Screening(
                    movie4,
                    THEATER_NAME_2,
                    listOf(MovieTime(12, 0), MovieTime(15, 0), MovieTime(18, 0)),
                ),
                Screening(
                    movie4,
                    THEATER_NAME_0,
                    listOf(MovieTime(10, 0), MovieTime(13, 0)),
                ),
            ),
        )

    fun getAllContents(): List<Content> {
        var id = movies.maxOf { it.id } + 1
        val contents = mutableListOf<Content>()

        movies.chunked(CONTENT_SWAP_SIZE).forEach { movieChunk ->
            movieChunk.forEach { movie -> contents.add(movie) }
            if (movieChunk.size == CONTENT_SWAP_SIZE) contents.add(Advertisement(id++))
        }

        return contents
    }

    fun getMovieScreenings(movieTitle: String): Screenings =
        Screenings(
            screenings.value.filter { screening ->
                screening.movie.title == movieTitle
            },
        )
}
