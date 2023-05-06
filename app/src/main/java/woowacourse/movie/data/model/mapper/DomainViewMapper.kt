package woowacourse.movie.data.model.mapper

import woowacourse.movie.data.model.uimodel.UiModel

interface DomainViewMapper<T, U : UiModel> {
    fun toDomain(viewModel: U): T
    fun toUi(domainModel: T): U
}
