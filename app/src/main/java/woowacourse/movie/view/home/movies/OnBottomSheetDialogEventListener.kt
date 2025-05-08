package woowacourse.movie.view.home.movies

import woowacourse.movie.domain.Showings

interface OnBottomSheetDialogEventListener {
    fun onClick(showings: Showings)
}
