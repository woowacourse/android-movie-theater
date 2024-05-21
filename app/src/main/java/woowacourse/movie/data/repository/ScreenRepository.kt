package woowacourse.movie.data.repository

import woowacourse.movie.domain.model.ScreenAndAd

interface ScreenRepository {
    fun loadAllScreens(): List<ScreenAndAd.Screen>

    fun loadScreen(screenId: Int): ScreenAndAd.Screen
}
