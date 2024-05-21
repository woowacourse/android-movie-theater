package woowacourse.movie.domain.usecase

import woowacourse.movie.data.model.ScreenData
import woowacourse.movie.data.repository.AdRepository
import woowacourse.movie.data.repository.MovieDataSource
import woowacourse.movie.data.repository.ScreenRepository
import woowacourse.movie.domain.model.Image
import woowacourse.movie.domain.model.ScreenAndAd
import woowacourse.movie.domain.model.toScreen
import woowacourse.movie.ui.ScreenAd
import woowacourse.movie.ui.toPreviewUI

class ScreensAndAdvertisementUseCase(
    private val movieDataSource: MovieDataSource,
    private val screenRepository: ScreenRepository,
    private val adRepository: AdRepository,
) {
    fun generated2(): List<ScreenAndAd> {
        val screens = screenRepository.load().map { it.toScreen() }
        val advertisement = adRepository.load()

        return generatedScreenAdList2(screens, advertisement)
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
            screenRepository.load().map { screen ->
                val moviePoster = movieDataSource.imageSrc(screen.movieData.id)
                screen.toPreviewUI(image = moviePoster)
            }

        val advertisement = adRepository.load()
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

    fun loadedScreens(screenId: Int): Result<ScreenData> = screenRepository.findById(screenId)

    companion object {
        private const val ADVERTISEMENT_INTERVAL = 4
    }
}
