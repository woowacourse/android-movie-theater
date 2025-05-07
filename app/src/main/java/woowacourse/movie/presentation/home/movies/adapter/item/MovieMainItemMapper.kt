package woowacourse.movie.presentation.home.movies.adapter.item

import woowacourse.movie.domain.model.movie.MovieContent
import woowacourse.movie.presentation.common.model.toUiModel

fun MovieContent.toUiModel(): MovieMainItem =
    when (this) {
        is MovieContent.MovieEntry -> MovieItem(this.movie.toUiModel())
        is MovieContent.MovieAd -> AdItem(this.id)
    }
