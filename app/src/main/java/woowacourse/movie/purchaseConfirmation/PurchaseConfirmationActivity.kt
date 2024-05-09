package woowacourse.movie.purchaseConfirmation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import woowacourse.movie.R
import woowacourse.movie.base.BindingActivity
import woowacourse.movie.databinding.ActivityPurchaseConfirmationBinding
import woowacourse.movie.error.ErrorActivity
import woowacourse.movie.model.Cinema

class PurchaseConfirmationActivity :
    BindingActivity<ActivityPurchaseConfirmationBinding>(R.layout.activity_purchase_confirmation) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ticketPrice =
            intent.getStringExtra(EXTRA_TICKET_PRICE) ?: return ErrorActivity.start(this)
        val seatNumber =
            intent.getStringArrayExtra(EXTRA_SEAT_NUMBER) ?: return ErrorActivity.start(this)
        val timeDate = intent.getStringExtra(EXTRA_TIME_DATE) ?: return ErrorActivity.start(this)
        val title = intent.getStringExtra(EXTRA_MOVIE_TITLE)
        val runningTime = intent.getStringExtra(EXTRA_RUNNING_TIME)
        val cinemaName = intent.getStringExtra(EXTRA_CINEMA_NAME)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.movieTitleConfirmation.text = title
        binding.purchaseMovieRunningTime.text = runningTime
        binding.reservedInformation.text =
            "일반 %s명 | %s | %s".format(
                seatNumber.size,
                seatNumber.joinToString(),
                cinemaName,
            )
        binding.ticketCharge.text = ticketPrice
        binding.movieTimeDate.text = timeDate
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    companion object {
        const val EXTRA_TICKET_PRICE = "ticketPrice"
        const val EXTRA_SEAT_NUMBER = "seatNumber"
        const val EXTRA_CINEMA_NAME = "cinemaName"
        const val EXTRA_MOVIE_TITLE = "title"
        const val EXTRA_RUNNING_TIME = "runningTime"
        const val EXTRA_TIME_DATE = "timeDate"

        fun newIntent(
            context: Context,
            ticketPrice: String,
            seatNumber: Array<String>,
            cinemaName: String,
            movieTitle: String,
            runningTime: String,
            timeDate: String,
        ): Intent {
            return Intent(context, PurchaseConfirmationActivity::class.java).apply {
                putExtra(EXTRA_TICKET_PRICE, ticketPrice)
                putExtra(EXTRA_SEAT_NUMBER, seatNumber)
                putExtra(EXTRA_CINEMA_NAME, cinemaName)
                putExtra(EXTRA_RUNNING_TIME, runningTime)
                putExtra(EXTRA_MOVIE_TITLE, movieTitle)
                putExtra(EXTRA_TIME_DATE, timeDate)
            }
        }
    }
}
