package woowacourse.movie.view.uiModel

import woowacourse.movie.view.bindingadapter.ImageSource

data class MovieUiModel(
    val title: String,
    val posterResource: ImageSource,
    val screeningStartDate: String,
    val screeningEndDate: String,
    val runningTime: Int,
)
