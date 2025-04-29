package woowacourse.movie.presentation.view

import android.os.Bundle
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieTheaterBinding
import woowacourse.movie.presentation.base.BaseActivity
import woowacourse.movie.presentation.view.movies.MoviesFragment

class MovieTheaterActivity : BaseActivity<ActivityMovieTheaterBinding>(R.layout.activity_movie_theater) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container_view, MoviesFragment())
                addToBackStack(null)
            }
        }
    }
}
