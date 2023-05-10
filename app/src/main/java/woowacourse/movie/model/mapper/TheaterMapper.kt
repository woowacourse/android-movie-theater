package woowacourse.movie.model.mapper

import domain.Theater
import woowacourse.movie.model.TheaterUiModel

object TheaterMapper : DomainViewMapper<Theater, TheaterUiModel> {
    override fun Theater.toUi(): TheaterUiModel {
        return TheaterUiModel(
            name = name,
            screenTimes = screenTimes
        )
    }

    override fun TheaterUiModel.toDomain(): Theater {
        return Theater(
            name = name,
            screenTimes = screenTimes
        )
    }
}
