package woowacourse.movie.model.mapper

import com.example.domain.model.Adv
import woowacourse.movie.data.AdvImageMapper
import woowacourse.movie.model.AdvState

fun AdvState.asDomain(): Adv = Adv(advId, advDescription)

fun Adv.asPresentation(): AdvState = AdvState(AdvImageMapper.mapper(advId), advId, advDescription)
