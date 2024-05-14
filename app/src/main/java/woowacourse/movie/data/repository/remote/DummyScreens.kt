package woowacourse.movie.data.repository.remote

import woowacourse.movie.R
import woowacourse.movie.data.repository.remote.DummyData.bang
import woowacourse.movie.data.repository.remote.DummyData.piro
import woowacourse.movie.data.repository.remote.DummyData.seatBoards
import woowacourse.movie.data.repository.remote.DummyData.theaters
import woowacourse.movie.data.repository.remote.DummyData.wizardStone
import woowacourse.movie.data.repository.remote.DummyData.zoesu
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.ScreenView
import woowacourse.movie.domain.model.ScreenView.Ads
import woowacourse.movie.domain.model.SeatBoard
import woowacourse.movie.domain.repository.ScreenRepository

object DummyScreens : ScreenRepository {
    override fun load(): List<ScreenView> =
        listOf(
            wizardStone,
            bang,
            zoesu,
            Ads(R.drawable.img_ads),
            piro,
        )

    override fun loadSeatBoard(id: Int): Result<SeatBoard> =
        runCatching {
            seatBoards.find { seatBoard -> seatBoard.id == id }
                ?: throw NoSuchElementException()
        }

    override fun findByScreenId(
        theaterId: Int,
        movieId: Int,
    ): Result<Screen> =
        runCatching {
            theaters.find { it.id == theaterId }?.screens?.find { screen -> screen.movie.id == movieId }
                ?: throw NoSuchElementException()
        }
}
