package woowacourse.movie.presentation.common.extension

import woowacourse.movie.presentation.common.model.PosterUiModel

fun PosterUiModel.toInt() =
    when (val poster = this) {
        is PosterUiModel.Resource -> poster.resId
        is PosterUiModel.Url -> 0
    }
