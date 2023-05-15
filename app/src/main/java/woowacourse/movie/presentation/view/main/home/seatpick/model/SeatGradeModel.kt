package woowacourse.movie.presentation.view.main.home.seatpick.model

import woowacourse.movie.R

enum class SeatGradeModel(val seatColor: Int) {
    S_CLASS(R.color.seat_grade_s_class),
    A_CLASS(R.color.seat_grade_a_class),
    B_CLASS(R.color.seat_grade_b_class),
    NONE(R.color.black);
}