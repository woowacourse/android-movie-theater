package woowacourse.movie.model.mapper

import woowacourse.movie.model.UiModel

interface DomainViewMapper<T, U : UiModel> {
    fun U.toDomain(): T
    fun T.toUi(): U
}
