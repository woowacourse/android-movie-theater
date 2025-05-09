package woowacourse.movie.ui.booking.view

import woowacourse.movie.domain.model.movie.Headcount

fun interface HeadcountButtonClickListener {
    fun onClick(headcount: Headcount)
}
