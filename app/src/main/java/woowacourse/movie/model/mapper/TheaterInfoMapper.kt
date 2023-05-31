package woowacourse.movie.model.mapper

import com.example.domain.model.TheaterScreeningInfo
import woowacourse.movie.model.TheaterScreeningInfoState

fun TheaterScreeningInfo.asPresentation(): TheaterScreeningInfoState =
    TheaterScreeningInfoState(theater.asPresentation(), screeningInfos.map { it.asPresentation() })
