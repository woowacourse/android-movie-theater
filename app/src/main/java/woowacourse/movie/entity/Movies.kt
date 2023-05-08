package woowacourse.movie.entity

import woowacourse.movie.R
import woowacourse.movie.model.MovieModel
import woowacourse.movie.model.TheaterModel
import java.time.LocalDate
import java.time.LocalTime

class Movies {
    private val items: List<MovieModel> = listOf(
        MovieModel(
            R.drawable.fifty_shades_freed,
            "그레이의 50가지 그림자",
            LocalDate.of(2023, 4, 11),
            LocalDate.of(2023, 11, 19),
            105,
            "모든 과거를 잊고 서로에게 더 깊게 빠져든 ‘크리스찬 그레이’와 ‘아나스타샤’. 그레이의 독특한 취향으로 시작된 이 비밀스러운 관계는 더 큰 자극을 원하는 아나스타샤로 인해 역전되고, 마침내 그녀의 본능이 지배하는 마지막 절정의 순간을 맞이하게 되는데…",
            listOf(
                TheaterModel("선릉 극장", listOf(LocalTime.of(9, 0), LocalTime.of(11, 0)))
            )
        ),
        MovieModel(
            R.drawable.potter,
            "해리 포터와 마법사의 돌",
            LocalDate.of(2023, 4, 1),
            LocalDate.of(2023, 4, 30),
            152,
            "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
            listOf(
                TheaterModel("선릉 극장", listOf(LocalTime.of(13, 0), LocalTime.of(15, 0), LocalTime.of(17, 0))),
                TheaterModel("강남 극장", listOf(LocalTime.of(15, 0), LocalTime.of(20, 0))),
            )
        ),
        MovieModel(
            R.drawable.war,
            "범죄 도시1",
            LocalDate.of(2023, 4, 12),
            LocalDate.of(2023, 5, 14),
            121,
            "통쾌하고! 화끈하고! 살벌하게! 나쁜 놈들 때려잡는 강력반 형사들의 ‘조폭소탕작전’이 시작된다!",
            listOf(
                TheaterModel("횡성 극장", listOf(LocalTime.of(10, 0), LocalTime.of(15, 0))),
                TheaterModel("원주 극장", listOf(LocalTime.of(15, 0), LocalTime.of(20, 0))),
            )
        ),
    )

    fun getAll(): List<MovieModel> = items.toList()
}
