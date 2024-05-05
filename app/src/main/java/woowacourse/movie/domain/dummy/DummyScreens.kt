package woowacourse.movie.domain.dummy

import woowacourse.movie.R
import woowacourse.movie.domain.dummy.DummyData.bang
import woowacourse.movie.domain.dummy.DummyData.piro
import woowacourse.movie.domain.dummy.DummyData.seatBoards
import woowacourse.movie.domain.dummy.DummyData.theaters
import woowacourse.movie.domain.dummy.DummyData.wizardStone
import woowacourse.movie.domain.dummy.DummyData.zoesu
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
