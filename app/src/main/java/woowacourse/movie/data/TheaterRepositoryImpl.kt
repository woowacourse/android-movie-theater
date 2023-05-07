package woowacourse.movie.data

import woowacourse.movie.R
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.ScreeningMovieState
import woowacourse.movie.model.TheaterScreeningState
import java.time.LocalDate
import java.time.LocalTime

object TheaterRepositoryImpl : TheaterRepository {
    override fun getAllTheaters(): List<TheaterScreeningState> {
        return theaters.toList()
    }

    override fun getScreeningMovieTheaters(movieState: MovieState): List<TheaterScreeningState> {
        return theaters.filter { theater -> movieState in theater.screeningInfos.map { it.movie } }
            .map {
                it.copy(screeningInfos = it.screeningInfos.filter { movieState == it.movie })
            }
    }

    private val movies: List<MovieState> = listOf(
        MovieState(
            R.drawable.slamdunk_poster,
            0,
            "더 퍼스트 슬램덩크",
            LocalDate.of(2023, 4, 29),
            LocalDate.of(2023, 5, 20),
            124,
            "북산고 농구부는 전국 대회에 출전해 라이벌 산왕공고와 맞붙는다. 멤버 각자가 쌓아온 성과, 그들이 짊어진 과거, 다양한 생각들이 뜨거운 코트 위에서 다시 한번 격렬하게 충돌한다." +
                "북산고 농구부는 전국 대회에 출전해 라이벌 산왕공고와 맞붙는다. 멤버 각자가 쌓아온 성과, 그들이 짊어진 과거, 다양한 생각들이 뜨거운 코트 위에서 다시 한번 격렬하게 충돌한다." +
                "북산고 농구부는 전국 대회에 출전해 라이벌 산왕공고와 맞붙는다. 멤버 각자가 쌓아온 성과, 그들이 짊어진 과거, 다양한 생각들이 뜨거운 코트 위에서 다시 한번 격렬하게 충돌한다."
        ),
        MovieState(
            R.drawable.ga_oh_galaxy_poster,
            1,
            "가디언즈 오브 갤럭시: Volume 3",
            LocalDate.of(2023, 5, 3),
            LocalDate.of(2023, 7, 20),
            150,
            "‘가모라’를 잃고 슬픔에 빠져 있던 ‘피터 퀼’이 위기에 처한 은하계와 동료를 지키기 위해 다시 한번 가디언즈 팀과 힘을 모으고, 성공하지 못할 경우 그들의 마지막이 될지도 모르는 미션에 나서는 이야기"
        ),
        MovieState(
            R.drawable.imitation_game_poster,
            2,
            "이미테이션 게임",
            LocalDate.of(2023, 5, 1),
            LocalDate.of(2023, 8, 20),
            114,
            "매 순간 3명이 죽는 사상 최악의 위기에 처한 제 2차 세계대전. 절대 해독이 불가능한 암호 ‘에니그마’로 인해 연합군은 속수무책으로 당하게 된다. 결국 각 분야의 수재들을 모아 기밀 프로젝트 암호 해독팀을 가동한다. 천재 수학자 앨런 튜링(베네딕트 컴버배치)은 암호 해독을 위한 특별한 기계를 발명하지만 24시간 마다 바뀌는 완벽한 암호 체계 때문에 번번히 좌절하고 마는데... 과연, 앨런 튜링과 암호 해독팀은 암호를 풀고 전쟁의 승리를 끌어낼 수 있을까…?"
        )
    )

    private val theaters: List<TheaterScreeningState> = listOf(
        TheaterScreeningState(
            "선릉 극장",
            listOf(
                ScreeningMovieState(
                    movies[0],
                    listOf(
                        LocalTime.of(10, 0),
                        LocalTime.of(12, 0),
                        LocalTime.of(14, 0),
                        LocalTime.of(16, 0),
                        LocalTime.of(18, 0),
                        LocalTime.of(20, 0),
                    )
                ),
                ScreeningMovieState(
                    movies[1],
                    listOf(
                        LocalTime.of(12, 0),
                        LocalTime.of(16, 0),
                        LocalTime.of(20, 0),
                    )
                ),
                ScreeningMovieState(
                    movies[2],
                    listOf(
                        LocalTime.of(10, 0),
                        LocalTime.of(14, 0),
                        LocalTime.of(18, 0),
                    )
                ),
            )
        ),
        TheaterScreeningState(
            "잠실 극장",
            listOf(
                ScreeningMovieState(
                    movies[0],
                    listOf(
                        LocalTime.of(10, 0),
                        LocalTime.of(16, 0),
                        LocalTime.of(20, 0),
                    )
                ),
                ScreeningMovieState(
                    movies[1],
                    listOf(
                        LocalTime.of(10, 0),
                        LocalTime.of(12, 0),
                        LocalTime.of(14, 0),
                        LocalTime.of(16, 0),
                        LocalTime.of(18, 0),
                        LocalTime.of(20, 0),
                    )
                ),
                ScreeningMovieState(
                    movies[2],
                    listOf(
                        LocalTime.of(12, 0),
                        LocalTime.of(20, 0),
                    )
                ),
            )
        ),
        TheaterScreeningState(
            "강남 극장",
            listOf(
                ScreeningMovieState(
                    movies[0],
                    listOf(
                        LocalTime.of(12, 0),
                        LocalTime.of(16, 0),
                        LocalTime.of(20, 0),
                    )
                ),
                ScreeningMovieState(
                    movies[2],
                    listOf(
                        LocalTime.of(10, 0),
                        LocalTime.of(12, 0),
                        LocalTime.of(14, 0),
                        LocalTime.of(16, 0),
                        LocalTime.of(18, 0),
                        LocalTime.of(20, 0),
                    )
                ),
            )
        )
    )
}
