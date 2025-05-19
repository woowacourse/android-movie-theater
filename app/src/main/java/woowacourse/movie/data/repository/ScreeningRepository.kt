package woowacourse.movie.data.repository

import woowacourse.movie.data.dummy.ScreeningData
import woowacourse.movie.domain.model.Screening

interface ScreeningRepository {
    fun fetch(): List<Screening>
}

class LocalScreeningRepository : ScreeningRepository {
    override fun fetch(): List<Screening> = ScreeningData.values
}
