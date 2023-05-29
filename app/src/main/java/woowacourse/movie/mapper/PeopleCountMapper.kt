package woowacourse.movie.mapper

import woowacourse.movie.domain.PeopleCount
import woowacourse.movie.uimodel.PeopleCountModel

fun PeopleCount.toModel(): PeopleCountModel = PeopleCountModel(count)

fun PeopleCountModel.toDomain(): PeopleCount = PeopleCount(count)
