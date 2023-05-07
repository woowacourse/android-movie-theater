package woowacourse.movie.data.model.mapper

import domain.Theater
import woowacourse.movie.data.model.uimodel.TheaterUiModel

object TheaterMapper : DomainViewMapper<Theater, TheaterUiModel> {
    override fun toDomain(viewModel: TheaterUiModel): Theater {
        return Theater(viewModel.name, viewModel.timeTable)
    }

    override fun toUi(domainModel: Theater): TheaterUiModel {
        return TheaterUiModel(domainModel.name, domainModel.timeTable)
    }
}
