package woowacourse.movie.view.movies

import woowacourse.movie.domain.Showings

interface OnBottomSheetDialogEventListener {
    fun onClick(showings: Showings)
}
