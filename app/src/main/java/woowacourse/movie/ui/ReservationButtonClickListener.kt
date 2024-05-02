package woowacourse.movie.ui

import android.view.View

fun interface ReservationButtonClickListener {
    fun onClick(
        movieContentId: Long,
    )
}
