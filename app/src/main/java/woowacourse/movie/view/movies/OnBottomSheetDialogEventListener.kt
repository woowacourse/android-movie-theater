package woowacourse.movie.view.movies

import woowacourse.movie.domain.Theater

interface OnBottomSheetDialogEventListener {
    fun onClick(theater: Theater)
}
