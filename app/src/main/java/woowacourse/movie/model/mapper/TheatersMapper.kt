package woowacourse.movie.model.mapper

import domain.Theaters
import woowacourse.movie.model.TheatersUiModel
import woowacourse.movie.model.mapper.TheaterMapper.toDomain
import woowacourse.movie.model.mapper.TheaterMapper.toUi

object TheatersMapper : DomainViewMapper<Theaters, TheatersUiModel> {
    override fun Theaters.toUi(): TheatersUiModel {
        return TheatersUiModel(
            list = list.map { it.toUi() }
        )
    }

    override fun TheatersUiModel.toDomain(): Theaters {
        return Theaters(
            list = list.map { it.toDomain() }
        )
    }
}

