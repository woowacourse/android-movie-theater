package woowacourse.movie.presentation.home.reservation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationBinding
import woowacourse.movie.presentation.common.base.BaseActivity
import woowacourse.movie.presentation.common.base.HomeButtonHandler
import woowacourse.movie.presentation.common.extension.getParcelableCompat
import woowacourse.movie.presentation.common.model.MovieUiModel
import woowacourse.movie.presentation.common.model.TheaterUiModel
import woowacourse.movie.presentation.home.reservation.detail.ReservationDetailFragment

class ReservationActivity : BaseActivity<ActivityReservationBinding>(R.layout.activity_reservation) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionBar()
        setupBackPressedHandler()

        if (savedInstanceState == null) {
            navigateToInitialScreen()
        }
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupBackPressedHandler() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() = handleBackPressed()
            },
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            handleBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleBackPressed() {
        if (shouldFinishActivity()) {
            finish()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    private fun shouldFinishActivity(): Boolean {
        val fragments = supportFragmentManager.fragments.reversed()
        return fragments.firstOrNull() is HomeButtonHandler || supportFragmentManager.backStackEntryCount <= 1
    }

    private fun navigateToInitialScreen() {
        val movie =
            intent.getParcelableCompat<MovieUiModel>(
                woowacourse.movie.presentation.home.reservation.ReservationActivity.Companion.BUNDLE_KEY_MOVIE,
            )
        val theater =
            intent.getParcelableCompat<TheaterUiModel>(
                woowacourse.movie.presentation.home.reservation.ReservationActivity.Companion.BUNDLE_KEY_THEATER,
            )
        navigateToScreen(ReservationDetailFragment.newInstance(movie, theater))
    }

    private fun navigateToScreen(fragment: Fragment) {
        supportFragmentManager.commit {
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
            Intent(context, woowacourse.movie.presentation.home.reservation.ReservationActivity::class.java).apply {
                putExtra(woowacourse.movie.presentation.home.reservation.ReservationActivity.Companion.BUNDLE_KEY_MOVIE, movieUiModel)
                putExtra(woowacourse.movie.presentation.home.reservation.ReservationActivity.Companion.BUNDLE_KEY_THEATER, theaterUiModel)
            }
    }
}
