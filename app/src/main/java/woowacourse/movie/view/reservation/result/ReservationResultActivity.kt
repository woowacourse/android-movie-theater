package woowacourse.movie.view.reservation.result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationResultBinding
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.view.base.BaseActivity
import woowacourse.movie.view.extension.getParcelableCompat
import woowacourse.movie.view.movies.MoviesFragment

class ReservationResultActivity :
    BaseActivity<ActivityReservationResultBinding>(R.layout.activity_reservation_result),
    ReservationResultContract.View {
    val presenter = ReservationResultPresenter(this)
    lateinit var ticket: Ticket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        ticket = intent.getParcelableCompat<Ticket>(BUNDLE_KEY_RESERVATION_INFO)

        presenter.loadReservationInfo(ticket)

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val intent = Intent(this@ReservationResultActivity, MoviesFragment::class.java)
                    startActivity(intent)
                    finish()
                }
            },
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> startActivity(Intent(this, MoviesFragment::class.java))
        }

        return super.onOptionsItemSelected(item)
    }

    override fun showReservationResult(ticket: Ticket) {
        binding.ticket = ticket
        setupCancelDescription()
    }

    private fun setupCancelDescription() {
        binding.tvCancelDescription.text =
            getString(R.string.reservation_result_cancel_time_description, CANCELLATION_TIME)
    }

    companion object {
        private const val CANCELLATION_TIME = 15
        private const val BUNDLE_KEY_RESERVATION_INFO = "reservation_info"

        fun newIntent(
            context: Context,
            ticket: Ticket,
        ): Intent =
            Intent(context, ReservationResultActivity::class.java).apply {
                putExtra(
                    BUNDLE_KEY_RESERVATION_INFO,
                    ticket,
                )
            }
    }
}
