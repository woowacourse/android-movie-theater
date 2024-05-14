package woowacourse.movie.presentation.homefragments.reservationList

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.database.ReservationDao
import woowacourse.movie.database.ReservationDatabase
import woowacourse.movie.repository.DummyTheaterList

@ExtendWith(MockKExtension::class)
class ReservationListPresenterTest {
    @MockK
    private lateinit var view: ReservationListContract.View
    private lateinit var presenter: ReservationListContract.Presenter

    private lateinit var reservationDao: ReservationDao
    private lateinit var db: ReservationDatabase

    @BeforeEach
    fun setUp() {
        view = ReservationListFragment()
        presenter = ReservationListPresenter(view, DummyTheaterList)
        createDb()
    }

    private fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db =
            Room.inMemoryDatabaseBuilder(
                context,
                ReservationDatabase::class.java,
            ).build()
        reservationDao = db.reservationDao()
    }

    @Test
    fun test() {
        presenter.loadReservations(db)

        verify { view.displayReservations(emptyList()) }
    }
}
