package woowacourse.movie.view.mapper

import woowacourse.movie.view.model.UiModel

interface DomainViewMapper<T, U : UiModel> {
    fun U.toDomain(): T
    fun T.toUi(): U
}
