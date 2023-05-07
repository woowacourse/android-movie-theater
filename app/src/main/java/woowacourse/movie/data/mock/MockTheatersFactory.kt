package woowacourse.movie.data.mock

import woowacourse.movie.data.model.uimodel.TheaterUiModel
import java.time.LocalTime

object MockTheatersFactory {

    fun generateTheaters(): List<TheaterUiModel> {
        val theaters = mutableListOf<TheaterUiModel>()
        theaters.add(TheaterUiModel("잠실 극장", listOf(LocalTime.of(14, 0), LocalTime.of(16, 0), LocalTime.of(18, 0))))
        theaters.add(TheaterUiModel("몽촌 토성 극장", listOf(LocalTime.of(11, 0), LocalTime.of(15, 0), LocalTime.of(18, 0), LocalTime.of(20, 0))))
        theaters.add(TheaterUiModel("선릉 극장", listOf(LocalTime.of(10, 0), LocalTime.of(12, 0))))
        return theaters.toList()
    }
}
