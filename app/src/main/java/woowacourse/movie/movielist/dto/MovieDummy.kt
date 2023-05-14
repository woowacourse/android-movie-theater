package woowacourse.movie.movielist.dto

import woowacourse.movie.R
import woowacourse.movie.theater.dto.MovieTheaterDto
import woowacourse.movie.theater.dto.MovieTheatersDto
import java.time.LocalDate
import java.time.LocalTime

object MovieDummy {
    val movieDatas = List(1000) {
        MovieDto(
            title = "해리포터 $it",
            startDate = LocalDate.of(2023, 5, 1),
            endDate = LocalDate.of(2023, 6, 1),
            runningTime = 200,
            description = "오래전 사악한 마법사 볼드모트에게서 부모를 잃지만 그를 몰락시키고 살아남은 아이 해리 포터는 자신이 마법사라는 사실을 알지 못하고 친척인 더즐리 집안에서 자라게 된다. 친척들로부터 갖은 구박을 받으며 힘든 나날을 보내던 도중, 11세가 되던 해에 해리에게로 마법 학교인 호그와트의 입학 통지서가 오게 된다. 이모부인 버넌 더즐리는 해리가 편지를 받지 못하게 방해하지만 해리는 우여곡절 끝에 자신이 마법사라는 사실을 알게 된다. 그리고 그를 맞이하러 온 호그와트의 숲지기 루비우스 해그리드의 안내로 호그와트에 입학하기 위한 준비를 하고, 마침내 학교로 가게 되는데...",
            moviePoster = R.drawable.img,
            theaters = MovieTheatersDto(
                listOf(
                    MovieTheaterDto("선릉", listOf(LocalTime.of(9, 0), LocalTime.of(12, 0))),
                    MovieTheaterDto(
                        "잠실",
                        listOf(
                            LocalTime.of(11, 0),
                            LocalTime.of(13, 0),
                            LocalTime.of(15, 0),
                            LocalTime.of(17, 0)
                        )
                    ),
                    MovieTheaterDto(
                        "강남",
                        listOf(
                            LocalTime.of(14, 0),
                            LocalTime.of(16, 0),
                            LocalTime.of(18, 0),
                            LocalTime.of(20, 0),
                            LocalTime.of(22, 0)
                        )
                    ),
                )
            )
        )
    }
}
