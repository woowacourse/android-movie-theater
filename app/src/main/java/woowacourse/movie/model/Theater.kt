package woowacourse.movie.model

import woowacourse.movie.model.movie.ScreeningTimes

class Theater(
    val theaterId: Int,
    val name: String,
    val screeningTimes: ScreeningTimes,
)

// 영화 Id로 극장리스트를 얻을 수 있고, 극장 리스트는 시간 정보를 가진다
