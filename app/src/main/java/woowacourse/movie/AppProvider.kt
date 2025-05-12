package woowacourse.movie

import woowacourse.movie.domain.ReservationRepository
import woowacourse.movie.domain.SettingRepository

object AppProvider {
    private const val NOT_INITIALIZED_MESSAGE = "%s가 초기화되지 않았습니다."

    private var _settingRepository: SettingRepository? = null
    val settingRepository =
        requireNotNull(_settingRepository) { NOT_INITIALIZED_MESSAGE.format("SettingRepository") }

    private var _reservationRepository: ReservationRepository? = null
    val reservationRepository =
        requireNotNull(_reservationRepository) { NOT_INITIALIZED_MESSAGE.format("ReservationRepository") }


    fun initSettingRepository(repository: SettingRepository) {
        _settingRepository = repository
    }

    fun initReservationRepository(repository: ReservationRepository) {
        _reservationRepository = repository
    }
}
