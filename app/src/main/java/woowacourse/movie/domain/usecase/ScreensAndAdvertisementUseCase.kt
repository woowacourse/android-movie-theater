package woowacourse.movie.domain.usecase

import woowacourse.movie.data.model.ScreenData
import woowacourse.movie.data.repository.AdvertisementRepository
import woowacourse.movie.data.repository.ScreenRepository
import woowacourse.movie.data.source.AdvertisementDataSource
import woowacourse.movie.data.source.MovieDataSource
import woowacourse.movie.data.source.ScreenDataSource
import woowacourse.movie.domain.model.Image
import woowacourse.movie.domain.model.ScreenAndAd
import woowacourse.movie.ui.ScreenAd
import woowacourse.movie.ui.toPreviewUI

class ScreensAndAdvertisementUseCase(
    private val movieDataSource: MovieDataSource,
    private val screenDataSource: ScreenDataSource,
    private val advertisementDataSource: AdvertisementDataSource,
) {
    fun generated2(): List<ScreenAndAd> {
//        val screens = screenDataSource.load().map { it.toScreen() }
//        val advertisement = advertisementDataSource.load()
//
//        return generatedScreenAdList2(screens, advertisement)
        // TODO:
        throw NotImplementedError()
    }

    private fun generatedScreenAdList2(
        screens: List<ScreenAndAd.Screen>,
        advertisement: ScreenAndAd.Advertisement,
    ): List<ScreenAndAd> {
        val totalItems = screens.size + screens.size / (ADVERTISEMENT_INTERVAL - 1)
        return (0 until totalItems).mapIndexed { index: Int, _ ->
            if ((index + 1) % ADVERTISEMENT_INTERVAL == 0) {
                advertisement
            } else {
                val screenIndex = index - index / ADVERTISEMENT_INTERVAL
                screens[screenIndex]
            }
        }
    }

    fun generated(): List<ScreenAd> {
        val screenPreviewUis =
            screenDataSource.load().map { screen ->
                val moviePoster = movieDataSource.imageSrc(screen.movieData.id)
                screen.toPreviewUI(image = moviePoster)
            }

        val advertisement = advertisementDataSource.load()
        return generatedScreenAdList(
            screenPreviewUis,
            ScreenAd.Advertisement(id = 1, Image.DrawableImage(0)),
        )
    }

    private fun generatedScreenAdList(
        screenPreviewUis: List<ScreenAd.ScreenPreviewUi>,
        advertisement: ScreenAd.Advertisement,
    ): List<ScreenAd> {
        val totalItems = screenPreviewUis.size + screenPreviewUis.size / (ADVERTISEMENT_INTERVAL - 1)
        return (0 until totalItems).mapIndexed { index: Int, _ ->
            if ((index + 1) % ADVERTISEMENT_INTERVAL == 0) {
                advertisement
            } else {
                val screenIndex = index - index / ADVERTISEMENT_INTERVAL
                screenPreviewUis[screenIndex]
            }
        }
    }

    fun loadedScreens(screenId: Int): Result<ScreenData> = screenDataSource.findById(screenId)

    companion object {
        private const val ADVERTISEMENT_INTERVAL = 4
    }
}

class ScreensAndAdvertisementUseCase2(
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
