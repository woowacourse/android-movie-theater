package woowacourse.movie.data

import woowacourse.movie.R
import woowacourse.movie.domain.policy.MorningPolicy
import woowacourse.movie.domain.policy.MovieDayPolicy
import woowacourse.movie.domain.policy.NightPolicy
import woowacourse.movie.domain.repository.TheaterRepository
import woowacourse.movie.domain.theater.Grade
import woowacourse.movie.domain.theater.SeatInfo
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
    private val seatInfo = SeatInfo(Size(5, 4), rowGrade)
    private val policies = listOf(
        MovieDayPolicy(),
        MorningPolicy(),
        NightPolicy(),
    )

    val theaters: List<Theater> = listOf(
        Theater("선릉 극장", seatInfo, policies),
        Theater("잠실 극장", seatInfo, policies),
        Theater("강남 극장", seatInfo, policies),
    )

    val gradeColor = mapOf(
        Grade.B to R.color.seat_rank_b,
        Grade.S to R.color.seat_rank_s,
        Grade.A to R.color.seat_rank_a,
    )

    override fun findAll(): List<Theater> {
        return theaters
    }

    override fun findTheater(name: String): Theater {
        return theaters.find { it.name == name } ?: throw java.lang.IllegalArgumentException("존재하지 않는 상영관입니다.")
    }
}
