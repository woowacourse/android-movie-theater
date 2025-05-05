package woowacourse.movie.data.repository

import woowacourse.movie.data.ScreeningData
import woowacourse.movie.domain.model.Screening

interface ScreeningRepository {
    fun fetch(): List<Screening>
}

class DefaultScreeningRepository : ScreeningRepository {
    override fun fetch(): List<Screening> = ScreeningData.values
}
