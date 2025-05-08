package woowacourse.movie.presentation.home.reservation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationBinding
import woowacourse.movie.presentation.common.base.BaseActivity
import woowacourse.movie.presentation.common.extension.getParcelableCompat
import woowacourse.movie.presentation.common.model.MovieUiModel
import woowacourse.movie.presentation.common.model.TheaterUiModel
import woowacourse.movie.presentation.home.reservation.detail.ReservationDetailFragment

class ReservationActivity : BaseActivity<ActivityReservationBinding>(R.layout.activity_reservation) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionBar()

        if (savedInstanceState == null) {
            navigateToInitialScreen()
        }
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        handleBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun handleBackPressed() {
        if (shouldFinishActivity()) finish() else supportFragmentManager.popBackStack()
    }

    private fun shouldFinishActivity(): Boolean = supportFragmentManager.backStackEntryCount <= FINISH_ACTIVITY_BACK_STACK_COUNT

    private fun navigateToInitialScreen() {
        val movie = intent.getParcelableCompat<MovieUiModel>(BUNDLE_KEY_MOVIE)
        val theater = intent.getParcelableCompat<TheaterUiModel>(BUNDLE_KEY_THEATER)

        supportFragmentManager.commit {
            replace(
                R.id.fragment_container_view,
                ReservationDetailFragment::class.java,
                ReservationDetailFragment.newBundle(movie, theater),
            )
        }
    }

    companion object {
        private const val BUNDLE_KEY_MOVIE = "movie"
        private const val BUNDLE_KEY_THEATER = "theater"
        private const val FINISH_ACTIVITY_BACK_STACK_COUNT = 0

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
