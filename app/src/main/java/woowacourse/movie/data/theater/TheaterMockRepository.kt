package woowacourse.movie.data.theater

import woowacourse.movie.domain.policy.MorningPolicy
import woowacourse.movie.domain.policy.MovieDayPolicy
import woowacourse.movie.domain.policy.NightPolicy
import woowacourse.movie.domain.repository.TheaterRepository
import woowacourse.movie.domain.theater.Grade
import woowacourse.movie.domain.theater.Size
import woowacourse.movie.domain.theater.Theater

object TheaterMockRepository : TheaterRepository {
    private val rowGrade = mapOf(
        0 to Grade.B,
        1 to Grade.B,
        2 to Grade.S,
        3 to Grade.S,
        4 to Grade.A,
    )
    private val policies = listOf(
        MovieDayPolicy(),
        MorningPolicy(),
        NightPolicy(),
    )

    private val theaters: List<Theater> = listOf(
        Theater("정말아주아주아주아주아주아주아주긴극장이름", Size(5, 4), rowGrade, policies),
        Theater("선릉 극장", Size(5, 4), rowGrade, policies),
        Theater("잠실 극장", Size(5, 4), rowGrade, policies),
        Theater("강남 극장", Size(5, 4), rowGrade, policies),
    )

    override fun findAll(): List<Theater> {
        return theaters
    }

    override fun findTheater(name: String): Theater {
        return requireNotNull(theaters.find { it.name == name }) { "존재하지 않는 상영관입니다." }
    }
}
