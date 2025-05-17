package woowacourse.movie.data

import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ReservationRepositoryTest {
    private lateinit var repository: ReservationRepository

    @BeforeEach
    fun setup() {
        ReservationRepository.initialize(ApplicationProvider.getApplicationContext())
        ReservationRepository.get()
        repository = ReservationRepository.get()
    }

    @Test
    fun 입력한_값은_id로_호출했을_때_동일한_타이틀을_보여준다() {
        val reservation = Reservation(title = "title", date = "2025.04.01", time = "18:00", personnel = 2, seats = "A2,B2", theater = "선릉", price = 24_000)
        repository.insert(reservation)

        val result = repository.getReservation(1L)
        assertThat(result?.title).isEqualTo("title")
    }

    @Test
    fun 입력한_값은_id로_호출했을_때_동일한_날짜를_보여준다() {
        val reservation = Reservation(title = "title", date = "2025.04.01", time = "18:00", personnel = 2, seats = "A2,B2", theater = "선릉", price = 24_000)
        repository.insert(reservation)

        val result = repository.getReservation(1L)
        assertThat(result?.date).isEqualTo("2025.04.01")
    }

    @Test
    fun 입력한_값은_id로_호출했을_때_동일한_시간을_보여준다() {
        val reservation = Reservation(title = "title", date = "2025.04.01", time = "18:00", personnel = 2, seats = "A2,B2", theater = "선릉", price = 24_000)
        repository.insert(reservation)

        val result = repository.getReservation(1L)
        assertThat(result?.time).isEqualTo("18:00")
    }

    @Test
    fun 입력한_값은_id로_호출했을_때_동일한_인원_수를_보여준다() {
        val reservation = Reservation(title = "title", date = "2025.04.01", time = "18:00", personnel = 2, seats = "A2,B2", theater = "선릉", price = 24_000)
        repository.insert(reservation)

        val result = repository.getReservation(1L)
        assertThat(result?.personnel).isEqualTo(2)
    }

    @Test
    fun 입력한_값은_id로_호출했을_때_동일한_좌석을_보여준다() {
        val reservation = Reservation(title = "title", date = "2025.04.01", time = "18:00", personnel = 2, seats = "A2,B2", theater = "선릉", price = 24_000)
        repository.insert(reservation)

        val result = repository.getReservation(1L)
        assertThat(result?.seats).isEqualTo("A2,B2")
    }

    @Test
    fun 입력한_값은_id로_호출했을_때_동일한_극장을_보여준다() {
        val reservation = Reservation(title = "title", date = "2025.04.01", time = "18:00", personnel = 2, seats = "A2,B2", theater = "선릉", price = 24_000)
        repository.insert(reservation)

        val result = repository.getReservation(1L)
        assertThat(result?.theater).isEqualTo("선릉")
    }

    @Test
    fun 입력한_값은_id로_호출했을_때_동일한_가격을_보여준다() {
        val reservation = Reservation(title = "title", date = "2025.04.01", time = "18:00", personnel = 2, seats = "A2,B2", theater = "선릉", price = 24_000)
        repository.insert(reservation)

        val result = repository.getReservation(1L)
        assertThat(result?.price).isEqualTo(24000)
    }
}
