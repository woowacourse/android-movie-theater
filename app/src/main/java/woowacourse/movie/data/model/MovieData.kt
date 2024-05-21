package woowacourse.movie.data.model

data class MovieData(
    val id: Int,
    val title: String,
    val runningTime: Int,
    val description: String,
) {
    companion object {
        val NULL =
            MovieData(
                id = -1,
                title = "",
                runningTime = -1,
                description = "",
            )
    }
}
