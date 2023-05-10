package woowacourse.movie.model

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime

class TheaterUiModel(val name: String, val screenTimes: List<LocalDateTime>) : UiModel,
    Serializable {

}
