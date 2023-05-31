package woowacourse.movie.data

import androidx.annotation.DrawableRes
import woowacourse.movie.R

object MovieImageMapper {
    fun mapper(movieId: Int): Int {
        return MoviePosterType.from(movieId).imgRes
    }

    private enum class MoviePosterType(val movieId: Int, @DrawableRes val imgRes: Int) {
        SLAM_DUNK(0, R.drawable.slamdunk_poster),
        GUARDIANS_OF_THE_GALAXY(1, R.drawable.ga_oh_galaxy_poster),
        IMITATION_GAME(2, R.drawable.imitation_game_poster),
        EMPTY_MOVIE(-1, R.drawable.empty_image);

        companion object {
            fun from(movieId: Int): MoviePosterType {
                return values().find { it.movieId == movieId } ?: EMPTY_MOVIE
            }
        }
    }
}
