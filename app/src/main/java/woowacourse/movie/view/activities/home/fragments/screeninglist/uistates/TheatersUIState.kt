package woowacourse.movie.view.activities.home.fragments.screeninglist.uistates

import woowacourse.movie.domain.screening.Screening

data class TheatersUIState(val screeningId: Long, val theaters: List<TheaterUIState>) {

    companion object {
        fun from(screening: Screening): TheatersUIState {
            return TheatersUIState(
                screening.id
                    ?: throw IllegalArgumentException("아이디가 부여되지 않은 상영에 대해 UI 상태를 생성할 수 없습니다."),
                screening.timeTable.timeTable.map { TheaterUIState.of(it.key, it.value) }
            )
        }
    }
}