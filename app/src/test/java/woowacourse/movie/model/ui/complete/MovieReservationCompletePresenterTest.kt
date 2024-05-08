package woowacourse.movie.model.ui.complete

import android.content.Context
import androidx.room.Room
import io.kotest.assertions.any
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.model.db.UserTicket
import woowacourse.movie.model.db.UserTicketDatabase
import woowacourse.movie.ui.complete.MovieReservationCompleteContract
import woowacourse.movie.ui.complete.MovieReservationCompletePresenter

class MovieReservationCompletePresenterTest {
    private lateinit var presenter: MovieReservationCompletePresenter
    private lateinit var view: MovieReservationCompleteContract.View
    private lateinit var db: UserTicketDatabase

    @BeforeEach
    fun setUp() {
        view = mockk<MovieReservationCompleteContract.View>(relaxed = true)
        presenter = MovieReservationCompletePresenter(view)
        db = mockk<UserTicketDatabase>()
    }

    @Test
    fun `티켓 정보를 보여준다`() {
        val context = mockk<Context>()
        setUpDatabase(context)

        every { db.userTicketDao().find(1L) } returns mockk<UserTicket>()

        presenter.loadTicket(1L)

        verify { view.showTicket(any()) }
    }

    @Test
    fun `티켓 정보가 없을 경우 에러를 보여준다`() {
        val context = mockk<Context>()
        setUpDatabase(context)
        every { db.userTicketDao().find(-1L) } throws NoSuchElementException()

        presenter.loadTicket(-1L)

        verify { view.showError(any()) }
    }

    @Test
    fun `에러를 처리한다`() {
        // given

        // when
        presenter.handleError(Throwable())

        // then
        verify { view.showError(any()) }
    }

    private fun setUpDatabase(context: Context) {
        mockkStatic(Room::class)
        every {
            Room.databaseBuilder(
                context.applicationContext,
                UserTicketDatabase::class.java,
                any(),
            ).build()
        } returns db

        UserTicketDatabase.initialize(context)
    }
}
