package woowacourse.movie.view.main.reservationList

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.database.AppDatabase
import woowacourse.movie.model.reservation.ReservationInfo
import woowacourse.movie.model.reservationSUZUME
import woowacourse.movie.model.reservationWEATHER
import woowacourse.movie.model.reservationYOURNAME
import woowacourse.movie.view.main.reservationlist.ReservationListContract
import woowacourse.movie.view.main.reservationlist.ReservationListPresenter

class ReservationListPresenterTest {
    private lateinit var database: AppDatabase
    private lateinit var testView: TestView
    private lateinit var presenter: ReservationListPresenter
    private lateinit var context: Context

    class TestView : ReservationListContract.View {
        var reservationsShown: List<ReservationInfo>? = null

        override fun showReservationInfos(reservationInfos: List<ReservationInfo>) {
            reservationsShown = reservationInfos
        }
    }

    @BeforeEach
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        database =
            Room
                .inMemoryDatabaseBuilder(
                    context,
                    AppDatabase::class.java,
                ).allowMainThreadQueries()
                .build()

        testView = TestView()
        presenter = ReservationListPresenter(testView, context)

        AppDatabase::class.java.getDeclaredField("INSTANCE").apply {
            isAccessible = true
            set(null, database)
        }
    }

    @AfterEach
    fun teardown() {
        database.close()
    }

    @Test
    fun `저장된_예약_정보를_표시한다`() {
        // given
        database.reservationInfoDao().insertReservation(reservationYOURNAME)
        database.reservationInfoDao().insertReservation(reservationWEATHER)
        database.reservationInfoDao().insertReservation(reservationSUZUME)

        // when
        presenter.loadReservationInfos()
        Thread.sleep(1000)

        // then
        val loadedReservations = presenter.getReservationInfos()
        assertThat(loadedReservations).hasSize(3)
        assertThat(loadedReservations.map { it.title })
            .containsExactlyInAnyOrder("너의 이름은.", "날씨의 아이", "스즈메의 문단속")
        assertThat(testView.reservationsShown).hasSize(3)
        assertThat(testView.reservationsShown?.get(0)?.title).isEqualTo("너의 이름은.")
    }
}
