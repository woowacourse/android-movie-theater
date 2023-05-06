package woowacourse.app.presentation.usecase.theater

import woowacourse.domain.theater.Theater
import woowacourse.domain.theater.TheaterRepository

class TheaterUseCase(private val theaterRepository: TheaterRepository) {

    fun getTheater(theaterId: Long): Theater? {
        return theaterRepository.getTheater(theaterId)
    }
}
