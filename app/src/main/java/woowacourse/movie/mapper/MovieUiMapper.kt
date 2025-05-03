package woowacourse.movie.mapper

import woowacourse.movie.R
import woowacourse.movie.model.Movie
import woowacourse.movie.ui.model.MovieUiModel

fun Movie.toUiModel(): MovieUiModel {
    return MovieUiModel(
        imageSource = getImageResIdFromUrl(title),
        title = title,
        screeningStartDate = screeningStartDate,
        screeningEndDate = screeningEndDate,
        runningTime = runningTime,
    )
}

fun MovieUiModel.toDomain(): Movie {
    return Movie(
        title = title,
        screeningStartDate = screeningStartDate,
        screeningEndDate = screeningEndDate,
        runningTime = runningTime,
    )
}

private fun getImageResIdFromUrl(imageUrl: String): Int {
    val resourceName = imageUrl.substringBeforeLast('.')
    return imageNameToResIdMap[resourceName] ?: R.drawable.harry_potter
}

private val imageNameToResIdMap =
    mapOf(
        "해리 포터와 마법사의 돌" to R.drawable.harry_potter,
        "해리 포터와 비밀의 방" to R.drawable.harry_potter2,
        "해리 포터와 아즈카반의 죄수" to R.drawable.harry_potter3,
        "해리 포터와 불의 잔" to R.drawable.harry_potter4,
        "스타 이즈 본" to R.drawable.star_is_born,
        "해리 포터와 마법사의 돌2" to R.drawable.harry_potter,
        "해리 포터와 비밀의 방2" to R.drawable.harry_potter2,
        "해리 포터와 아즈카반의 죄수2" to R.drawable.harry_potter3,
        "해리 포터와 불의 잔2" to R.drawable.harry_potter4,
        "스타 이즈 본2" to R.drawable.star_is_born,
    )
