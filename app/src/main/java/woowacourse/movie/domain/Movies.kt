package woowacourse.movie.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.R

@Parcelize
data class Movies(
    val movies: Map<Title, Movie>,
) : Parcelable {
    init {
        movies.keys.map { key ->
            require(key.value == movies[key]?.title)
        }

        require(movies.size <= MAX_NUMBER_OF_MOVIES) { MovieResult.MovieCountUnder10000O }
    }

    fun add(movie: Movie): Movies {
        val newMap = this.movies + (Title(movie.title) to movie)
        return Movies(newMap)
    }

    fun find(title: Title): MovieResult =
        movies[title]
            ?.let { MovieResult.Success(it) }
            ?: MovieResult.NotMovie


    fun toList(): List<Movie> = movies.values.toList()

    companion object {
        private const val MAX_NUMBER_OF_MOVIES: Int = 10_000

        val value =
            Movies(
                mapOf(
                    Title("해리포터와 마법사의 돌") to
                            Movie(
                                Title("해리포터와 마법사의 돌"),
                                R.drawable.movie_poster,
                                ScreeningPeriod.ofDot("2025.04.01", "2025.04.25"),
                                152,
                            ),
                    Title("해리포터와 비밀의 방") to
                            Movie(
                                Title("해리포터와 비밀의 방"),
                                R.drawable.harry_potter_and_the_chamber_of_secrets,
                                ScreeningPeriod.ofDot("2025.04.01", "2025.04.28"),
                                162,
                            ),
                    Title("해리포터와 아즈카반의 죄수") to
                            Movie(
                                Title("해리포터와 아즈카반의 죄수"),
                                R.drawable.harry_potter_and_the_prisoner_of_azkaban,
                                ScreeningPeriod.ofDot("2025.05.01", "2025.05.31"),
                                141,
                            ),
                    Title("해리포터와 불의 잔") to
                            Movie(
                                Title("해리포터와 불의 잔"),
                                R.drawable.harry_potter_and_the_goblet_of_fire,
                                ScreeningPeriod.ofDot("2025.06.01", "2025.06.30"),
                                157,
                            ),
                    Title("해리포터와 마법사의 돌1") to
                            Movie(
                                Title("해리포터와 마법사의 돌1"),
                                R.drawable.movie_poster,
                                ScreeningPeriod.ofDot("2025.04.01", "2025.04.25"),
                                152,
                            ),
                    Title("해리포터와 비밀의 방1") to
                            Movie(
                                Title("해리포터와 비밀의 방1"),
                                R.drawable.harry_potter_and_the_chamber_of_secrets,
                                ScreeningPeriod.ofDot("2025.04.01", "2025.04.28"),
                                162,
                            ),
                    Title("해리포터와 아즈카반의 죄수1") to
                            Movie(
                                Title("해리포터와 아즈카반의 죄수1"),
                                R.drawable.harry_potter_and_the_prisoner_of_azkaban,
                                ScreeningPeriod.ofDot("2025.05.01", "2025.05.31"),
                                141,
                            ),
                    Title("해리포터와 불의 잔1") to
                            Movie(
                                Title("해리포터와 불의 잔1"),
                                R.drawable.harry_potter_and_the_goblet_of_fire,
                                ScreeningPeriod.ofDot("2025.06.01", "2025.06.30"),
                                157,
                            ),
                ),
            )

        val seolleungMovies =
            Movies(
                mapOf(
                    Title("해리포터와 불의 잔") to
                            Movie(
                                Title("해리포터와 불의 잔"),
                                R.drawable.harry_potter_and_the_goblet_of_fire,
                                ScreeningPeriod.ofDot("2025.06.01", "2025.06.30"),
                                157,
                            ),
                    Title("해리포터와 아즈카반의 죄수") to
                            Movie(
                                Title("해리포터와 아즈카반의 죄수"),
                                R.drawable.harry_potter_and_the_prisoner_of_azkaban,
                                ScreeningPeriod.ofDot("2025.05.01", "2025.05.31"),
                                141,
                            ),
                ),
            )

        val jamsilMovies =
            Movies(
                mapOf(
                    Title("해리포터와 마법사의 돌") to
                            Movie(
                                Title("해리포터와 마법사의 돌"),
                                R.drawable.movie_poster,
                                ScreeningPeriod.ofDot("2025.04.01", "2025.04.25"),
                                152,
                            ),
                    Title("해리포터와 아즈카반의 죄수") to
                            Movie(
                                Title("해리포터와 아즈카반의 죄수"),
                                R.drawable.harry_potter_and_the_prisoner_of_azkaban,
                                ScreeningPeriod.ofDot("2025.05.01", "2025.05.31"),
                                141,
                            ),
                    Title("해리포터와 비밀의 방") to
                            Movie(
                                Title("해리포터와 비밀의 방"),
                                R.drawable.harry_potter_and_the_chamber_of_secrets,
                                ScreeningPeriod.ofDot("2025.04.01", "2025.04.28"),
                                162,
                            ),
                ),
            )

        val gangnamMovies =
            Movies(
                mapOf(
                    Title("해리포터와 불의 잔") to
                            Movie(
                                Title("해리포터와 불의 잔"),
                                R.drawable.harry_potter_and_the_goblet_of_fire,
                                ScreeningPeriod.ofDot("2025.06.01", "2025.06.30"),
                                157,
                            ),
                    Title("해리포터와 아즈카반의 죄수") to
                            Movie(
                                Title("해리포터와 아즈카반의 죄수"),
                                R.drawable.harry_potter_and_the_prisoner_of_azkaban,
                                ScreeningPeriod.ofDot("2025.05.01", "2025.05.31"),
                                141,
                            ),
                ),
            )
    }
}

sealed class MovieResult {
    data class Success(val movie: Movie) : MovieResult()
    object NotMovie : MovieResult()
    object MovieCountUnder10000O : MovieResult()
}
