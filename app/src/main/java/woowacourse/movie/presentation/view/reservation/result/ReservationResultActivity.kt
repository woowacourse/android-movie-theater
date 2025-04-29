package woowacourse.movie.presentation.view.reservation.result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import woowacourse.movie.R
import woowacourse.movie.presentation.base.BaseActivity
import woowacourse.movie.presentation.extension.getParcelableCompat
import woowacourse.movie.presentation.model.TicketBundleUiModel
import woowacourse.movie.presentation.view.movies.MoviesActivity

class ReservationResultActivity :
    BaseActivity(R.layout.activity_reservation_result),
    ReservationResultContract.View {
    private val views: ReservationResultViews by lazy { ReservationResultViews(this) }
    private val presenter: ReservationResultPresenter by lazy { ReservationResultPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionBar()
        setBackPressedDispatcher()

        val ticketBundle = intent.getParcelableCompat<TicketBundleUiModel>(BUNDLE_KEY_TICKET_BUNDLE)
        presenter.fetchDate(ticketBundle)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navigateToMoviesScreen()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun showScreen(
        ticketBundle: TicketBundleUiModel,
        cancellationTime: Int,
    ) {
        views.bindReservationResult(ticketBundle, cancellationTime)
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setBackPressedDispatcher() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navigateToMoviesScreen()
                }
            },
        )
    }

    private fun navigateToMoviesScreen() {
        val intent = Intent(this, MoviesActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    companion object {
        private const val BUNDLE_KEY_TICKET_BUNDLE = "ticket_bundle"

        fun newIntent(
            context: Context,
            ticketBundle: TicketBundleUiModel,
        ): Intent =
            Intent(context, ReservationResultActivity::class.java).putExtra(
                BUNDLE_KEY_TICKET_BUNDLE,
                ticketBundle,
            )
    }
}
