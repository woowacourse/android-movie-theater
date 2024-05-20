package woowacourse.movie.domain.usecase

import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.repository.AdRepository
import woowacourse.movie.domain.repository.MovieRepository
import woowacourse.movie.domain.repository.ScreenRepository
import woowacourse.movie.ui.ScreenAd
import woowacourse.movie.ui.toPreviewUI

class ScreensAndAdvertisementUseCase(
    private val movieRepository: MovieRepository,
    private val screenRepository: ScreenRepository,
    private val adRepository: AdRepository,
) {
    fun generated(): List<ScreenAd> {
        val screenPreviewUis =
            screenRepository.load().map { screen ->
                val moviePoster = movieRepository.imageSrc(screen.movie.id)
                screen.toPreviewUI(image = moviePoster)
            }

        val advertisement = adRepository.load()
        return generatedScreenAdList(screenPreviewUis, advertisement)
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

    fun loadedScreens(screenId: Int): Result<Screen> = screenRepository.findById(screenId)

    companion object {
        private const val ADVERTISEMENT_INTERVAL = 4
    }
}
