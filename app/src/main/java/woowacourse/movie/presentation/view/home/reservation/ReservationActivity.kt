package woowacourse.movie.presentation.view.home.reservation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationBinding
import woowacourse.movie.presentation.base.BaseActivity
import woowacourse.movie.presentation.base.HomeButtonHandler
import woowacourse.movie.presentation.extension.getParcelableCompat
import woowacourse.movie.presentation.model.MovieUiModel
import woowacourse.movie.presentation.model.TheaterUiModel
import woowacourse.movie.presentation.view.home.reservation.detail.ReservationDetailFragment

class ReservationActivity : BaseActivity<ActivityReservationBinding>(R.layout.activity_reservation) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) navigateToReservationDetailScreen()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (isLastScreen()) {
                finish()
                return true
            }

            supportFragmentManager.popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun isLastScreen(): Boolean = supportFragmentManager.fragments.reversed().firstOrNull() is HomeButtonHandler

    private fun navigateToReservationDetailScreen() {
        val movie = intent.getParcelableCompat<MovieUiModel>(BUNDLE_KEY_MOVIE)
        val theater = intent.getParcelableCompat<TheaterUiModel>(BUNDLE_KEY_THEATER)

        val fragment = ReservationDetailFragment.newInstance(movie, theater)
        navigateToScreen(fragment)
    }

    private fun navigateToScreen(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, fragment)
            addToBackStack(null)
        }
    }

    companion object {
        private const val BUNDLE_KEY_MOVIE = "movie"
        private const val BUNDLE_KEY_THEATER = "theater"

        fun newIntent(
            context: Context,
            movieUiModel: MovieUiModel,
            theaterUiModel: TheaterUiModel,
        ): Intent =
            Intent(context, ReservationActivity::class.java).apply {
                putExtra(BUNDLE_KEY_MOVIE, movieUiModel)
                putExtra(BUNDLE_KEY_THEATER, theaterUiModel)
            }
    }
}
