package woowacourse.movie.presentation.mapper

import woowacourse.movie.domain.model.theater.DomainTheater
import woowacourse.movie.presentation.model.theater.PresentationTheater

fun PresentationTheater.toDomain(): DomainTheater =
    DomainTheater(name = name, runningTimes = movieTimes.map { it.toDomain() })

fun DomainTheater.toPresentation(): PresentationTheater =
    PresentationTheater(name, runningTimes.map { it.toPresentation() })
