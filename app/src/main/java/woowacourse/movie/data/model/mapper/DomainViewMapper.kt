package woowacourse.movie.data.model.mapper

import woowacourse.movie.data.model.uimodel.UIModel

interface DomainViewMapper<T, U : UIModel> {
    fun toDomain(viewModel: U): T
    fun toUI(domainModel: T): U
}
