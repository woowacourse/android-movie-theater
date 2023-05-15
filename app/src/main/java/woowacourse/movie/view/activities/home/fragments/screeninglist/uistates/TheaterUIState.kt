package woowacourse.movie.view.activities.home.fragments.screeninglist.uistates

import woowacourse.movie.domain.theater.Theater
import java.time.LocalTime

data class TheaterUIState(
    val theaterName: String,
    val screeningTimeCount: Int,
    val theaterId: Long
) {

    companion object {
        fun of(theater: Theater, times: List<LocalTime>): TheaterUIState {
            return TheaterUIState(
                theater.name,
                times.size,
                theater.id
                    ?: throw IllegalArgumentException("아이디가 부여되지 않은 극장에 대해 UI 상태를 생성할 수 없습니다.")
            )
        }
    }
}