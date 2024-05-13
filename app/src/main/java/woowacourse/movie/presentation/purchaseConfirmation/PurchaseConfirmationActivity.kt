package woowacourse.movie.presentation.purchaseConfirmation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.addCallback
import woowacourse.movie.MovieReservationApp
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityPurchaseConfirmationBinding
import woowacourse.movie.model.Reservation
import woowacourse.movie.presentation.HomeActivity
import woowacourse.movie.presentation.base.BindingActivity
import woowacourse.movie.presentation.error.ErrorActivity
import kotlin.concurrent.thread

class PurchaseConfirmationActivity :
    BindingActivity<ActivityPurchaseConfirmationBinding>(R.layout.activity_purchase_confirmation),
    PurchaseConfirmationContract.View {
    private val presenter: PurchaseConfirmationContract.Presenter by lazy {
        PurchaseConfirmationPresenter(
            (applicationContext as MovieReservationApp).movieRepository,
            this,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initBackPressCallback()
        val reservationId = intent.getLongExtra(EXTRA_RESERVATION_ID, -1)
        thread {
            presenter.loadReservation(reservationId)
        }.join()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showReservation(reservation: Reservation) {
        binding.data = reservation
        binding.reservedInformation.text =
            getString(
                R.string.reserved_format,
                reservation.seats.size,
                reservation.seats.joinToString { it.row.toString() + it.number },
                reservation.cinemaName,
            )
    }

    override fun showError() {
        ErrorActivity.start(this)
        finish()
    }

    private fun initBackPressCallback() {
        onBackPressedDispatcher.addCallback {
            HomeActivity.start(this@PurchaseConfirmationActivity) {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        }
    }

    companion object {
        const val EXTRA_RESERVATION_ID = "reservationId"

        fun newIntent(
            context: Context,
            reservationId: Long,
        ): Intent {
            return Intent(context, PurchaseConfirmationActivity::class.java).apply {
                putExtra(EXTRA_RESERVATION_ID, reservationId)
            }
        }
    }
}
