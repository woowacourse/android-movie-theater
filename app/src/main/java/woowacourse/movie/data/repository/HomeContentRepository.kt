package woowacourse.movie.data.repository

import woowacourse.movie.R
import woowacourse.movie.home.view.adapter.movie.HomeContent.Advertisement
import woowacourse.movie.home.view.adapter.movie.HomeContent.Movie
import woowacourse.movie.model.MovieDate
import woowacourse.movie.model.Theater
import java.time.LocalDate
import java.time.LocalTime

object HomeContentRepository {
    private val movies =
        List(100) {
            Movie(
                id = it.toLong(),
                image = R.drawable.titanic,
                title = "타이타닉 ${it + 1}",
                description =
                    "우연한 기회로 티켓을 구해 타이타닉호에 올라탄 자유로운 영혼을 가진 화가 ‘잭’(레오나르도 디카프리오)은 막강한 재력의 약혼자와 함께 1등실에 승선한 ‘로즈’(케이트 윈슬렛)에게 한눈에 반한다. " +
                        "진실한 사랑을 꿈꾸던 ‘로즈’ 또한 생애 처음 황홀한 감정에 휩싸이고, 둘은 운명 같은 사랑에 빠지는데… 가장 차가운 곳에서 피어난 뜨거운 사랑! 영원히 가라앉지 않는 세기의 사랑이 펼쳐진다!",
                date = MovieDate(LocalDate.of(2024, 4, 1), LocalDate.of(2024, 4, 28)),
                runningTime = 152,
                theaters =
                    listOf(
                        Theater(
                            "선릉선릉선릉선릉선릉선릉선릉선릉선릉선릉선릉선릉선릉선릉선릉선릉선릉",
                            listOf(LocalTime.of(10, 0), LocalTime.of(14, 0), LocalTime.of(18, 0)),
                        ),
                        Theater(
                            "잠실",
                            listOf(
                                LocalTime.of(12, 30),
                                LocalTime.of(13, 30),
                                LocalTime.of(15, 0),
                                LocalTime.of(17, 0),
                                LocalTime.of(21, 0),
                            ),
                        ),
                        Theater(
                            "강남",
                            listOf(LocalTime.of(17, 0), LocalTime.of(20, 0)),
                        ),
                    ),
            )
        }

    private val advertisements =
        List(10) {
            Advertisement(
                banner = R.drawable.advertisement,
                link = "https://www.woowacourse.io/",
            )
        }

    fun getAllMovies(): List<Movie> {
        return movies
    }

    fun getMovieById(id: Long): Movie? {
        return movies.firstOrNull { it.id == id }
    }

    fun getAllAdvertisements(): List<Advertisement> {
        return advertisements
    }
}
