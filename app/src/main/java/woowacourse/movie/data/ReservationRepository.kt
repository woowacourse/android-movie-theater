package woowacourse.movie.data

import woowacourse.movie.R
import woowacourse.movie.model.CountState
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.ReservationState
import java.time.LocalDate
import java.time.LocalDateTime

object ReservationRepository {

    fun allReservations(): List<ReservationState> = reservations.toList()

    fun addReservation(reservationState: ReservationState) = reservations.add(reservationState)

    private val movieState: MovieState = MovieState(
        R.drawable.slamdunk,
        "더 퍼스트 슬램덩크",
        LocalDate.of(2023, 1, 4),
        LocalDate.of(2023, 2, 23),
        124,
        "북산고 농구부는 전국 대회에 출전해 라이벌 산왕공고와 맞붙는다. 멤버 각자가 쌓아온 성과, 그들이 짊어진 과거, 다양한 생각들이 뜨거운 코트 위에서 다시 한번 격렬하게 충돌한다." +
            "북산고 농구부는 전국 대회에 출전해 라이벌 산왕공고와 맞붙는다. 멤버 각자가 쌓아온 성과, 그들이 짊어진 과거, 다양한 생각들이 뜨거운 코트 위에서 다시 한번 격렬하게 충돌한다." +
            "북산고 농구부는 전국 대회에 출전해 라이벌 산왕공고와 맞붙는다. 멤버 각자가 쌓아온 성과, 그들이 짊어진 과거, 다양한 생각들이 뜨거운 코트 위에서 다시 한번 격렬하게 충돌한다."
    )

    private val reservations: MutableList<ReservationState> = MutableList(0) {
        ReservationState(
            movieState,
            LocalDateTime.of(2023, 4, 25, 17, 54),
            CountState.of(2)
        )
    }
}
