package woowacourse.movie.domain.usecase

import woowacourse.movie.data.repository.AdvertisementRepository
import woowacourse.movie.data.repository.ScreenRepository
import woowacourse.movie.domain.model.ScreenAndAd

class ScreensAndAdvertisementUseCase(
    private val screenRepository: ScreenRepository,
    private val advertisementRepository: AdvertisementRepository,
) {
    fun generatedScreenAndAdvertisement(): List<ScreenAndAd> =
        generated(
            screens = screenRepository.loadAllScreens(),
            advertisement = advertisementRepository.loadAdvertisement(),
        )

    private fun generated(
        screens: List<ScreenAndAd.Screen>,
        advertisement: ScreenAndAd.Advertisement,
    ): List<ScreenAndAd> {
        val totalItemsCount = screens.size + screens.size / (ADVERTISEMENT_INTERVAL - 1)
        return (0 until totalItemsCount).mapIndexed { index, _ ->
            if ((index + 1) % ADVERTISEMENT_INTERVAL == 0) {
                advertisement
            } else {
                val screenIndex = index - index / ADVERTISEMENT_INTERVAL
                screens[screenIndex]
            }
        }
    }

    companion object {
        private const val ADVERTISEMENT_INTERVAL = 4
    }
}
