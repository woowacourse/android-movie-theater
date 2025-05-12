package woowacourse.movie.model.reservation

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.database.AppDatabase
import woowacourse.movie.model.reservationSUZUME
import woowacourse.movie.model.reservationWEATHER
import woowacourse.movie.model.reservationYOURNAME
import woowacourse.movie.model.seatsA1A2B3
import woowacourse.movie.model.seatsB3
import java.time.LocalDate

class ReservationInfoDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var dao: ReservationInfoDao

    @BeforeEach
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database =
            Room
                .inMemoryDatabaseBuilder(
                    context,
                    AppDatabase::class.java,
                ).allowMainThreadQueries()
                .build()

        dao = database.reservationInfoDao()
    }

    @AfterEach
    fun teardown() {
        database.close()
    }

    @Test
    fun `예약_정보를_저장하고_ID를_반환한다`() {
        // when
        val id = dao.insertReservation(reservationSUZUME)

        // then
        assertThat(id).isGreaterThan(0)
    }

    @Test
    fun `전체_예약_정보를_조회한다`() {
        // given
        dao.insertReservation(reservationYOURNAME)
        dao.insertReservation(reservationWEATHER)
        dao.insertReservation(reservationSUZUME)

        // when
        val reservations = dao.getAllReservations()

        // then
        assertThat(reservations).hasSize(3)
        assertThat(reservations.map { it.title })
            .containsExactlyInAnyOrder("너의 이름은.", "날씨의 아이", "스즈메의 문단속")
    }

    @Test
    fun `ID로_특정_예약_정보를_조회한다`() {
        // given
        dao.insertReservation(reservationWEATHER)
        val id = dao.insertReservation(reservationYOURNAME)

        // when
        val foundReservation = dao.getReservationById(id.toInt())

        // then
        assertThat(foundReservation).isNotNull
        assertThat(foundReservation.title).isEqualTo("너의 이름은.")
        assertThat(foundReservation.theaterName).isEqualTo("보라매")
    }

    @Test
    fun `예약_정보를_삭제한다`() {
        // given
        dao.insertReservation(reservationYOURNAME)
        val reservations = dao.getAllReservations()
        assertThat(reservations).hasSize(1)

        // when
        dao.deleteReservation(reservations[0])

        // then
        val remainingReservations = dao.getAllReservations()
        assertThat(remainingReservations).isEmpty()
    }

    @Test
    fun `LocalDate_타입_컨버터_테스트`() {
        // given
        val testDate = LocalDate.of(2025, 5, 11)

        val reservation =
            ReservationInfo(
                title = "테스트 영화",
                date = testDate,
                time = "18:00",
                seats = seatsB3,
                price = 15000,
                theaterName = "테스트 극장",
            )

        // when
        val id = dao.insertReservation(reservation)
        val retrievedReservation = dao.getReservationById(id.toInt())

        // then
        assertThat(retrievedReservation.date).isEqualTo(testDate)
    }

    @Test
    fun `Seats_타입_컨버터_테스트`() {
        // given
        val reservation =
            ReservationInfo(
                title = "테스트 영화",
                date = LocalDate.of(2025, 5, 11),
                time = "18:00",
                seats = seatsA1A2B3,
                price = 15000,
                theaterName = "테스트 극장",
            )

        // when
        val id = dao.insertReservation(reservation)
        val retrievedReservation = dao.getReservationById(id.toInt())

        // then
        val retrievedSeats = retrievedReservation.seats.value
        assertThat(retrievedSeats).hasSize(3)
        val seatsList = retrievedSeats.toList()
        assertThat(seatsList).isEqualTo(seatsA1A2B3.value)
    }
}
