package woowacourse.movie.view.uiModel

import woowacourse.movie.view.bindingadapter.ImageSource

data class MovieUiModel(
    val title: String,
    val posterResource: ImageSource,
    val screeningPeriod: String,
    val runningTime: String,
)
