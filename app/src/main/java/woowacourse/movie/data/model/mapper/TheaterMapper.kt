package woowacourse.movie.data.model.mapper

import domain.Theater
import woowacourse.movie.data.model.uimodel.TheaterUIModel

object TheaterMapper : DomainViewMapper<Theater, TheaterUIModel> {
    override fun toDomain(viewModel: TheaterUIModel): Theater {
        return Theater(viewModel.name, viewModel.timeTable)
    }

    override fun toUI(domainModel: Theater): TheaterUIModel {
        return TheaterUIModel(domainModel.name, domainModel.timeTable)
    }
}
