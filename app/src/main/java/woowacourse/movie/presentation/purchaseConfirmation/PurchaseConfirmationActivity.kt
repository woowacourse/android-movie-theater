package woowacourse.movie.presentation.purchaseConfirmation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityPurchaseConfirmationBinding
import woowacourse.movie.presentation.base.BindingActivity

class PurchaseConfirmationActivity :
    BindingActivity<ActivityPurchaseConfirmationBinding>(R.layout.activity_purchase_confirmation) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val reservationId = intent.getLongExtra(EXTRA_RESERVATION_ID, -1)
//        val cinema =
//            IntentCompat.getSerializableExtra(intent, EXTRA_CINEMA, Cinema::class.java)
//                ?: return ErrorActivity.start(this)
//        val movie = cinema.theater.movie
//        binding.movieTitleConfirmation.text = movie.title.toString()
//        binding.purchaseMovieRunningTime.text = movie.runningTime.toString()
//        binding.reservedInformation.text =
//            "일반 %s명 | %s | %s".format(
//                seatNumber?.size,
//                seatNumber?.joinToString(),
//                cinema.cinemaName,
//            )
//        binding.ticketCharge.text = ticketPrice
//        binding.movieTimeDate.text = timeDate
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
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
