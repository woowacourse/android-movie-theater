package woowacourse.movie.data

import woowacourse.movie.R

object MoviePosterMockRepository : MoviePosterRepository {
    private val posters = mapOf(
        "스즈메의 문단속" to R.drawable.suzume_poster,
        "해리 포터와 마법사의 돌" to R.drawable.harry_potter1_poster,
        "스타워즈" to R.drawable.starwars_poster,
        "어벤져스: 엔드게임" to R.drawable.avengers_endgame_poster,
    )

    override fun findPoster(title: String): Int {
        return posters[title] ?: R.drawable.none_poster
    }
}
