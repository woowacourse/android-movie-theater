package woowacourse.movie.purchaseConfirmation

import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.IntentCompat
import woowacourse.movie.R
import woowacourse.movie.common.BindingActivity
import woowacourse.movie.common.ui.redirectToErrorActivity
import woowacourse.movie.databinding.ActivityPurchaseConfirmationBinding
import woowacourse.movie.model.Cinema

class PurchaseConfirmationActivity :
    BindingActivity<ActivityPurchaseConfirmationBinding>(R.layout.activity_purchase_confirmation) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ticketPrice = intent.getStringExtra("ticketPrice")
        val seatNumber = intent.getStringArrayExtra("seatNumber")
        val timeDate = intent.getStringExtra("timeDate")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val cinema =
            IntentCompat.getSerializableExtra(intent, "Cinema", Cinema::class.java)
                ?: return redirectToErrorActivity()
        val movie = cinema.theater.movie
        binding.movieTitleConfirmation.text = movie.title.toString()
        binding.purchaseMovieRunningTime.text = movie.runningTime.toString()
        binding.reservedInformation.text =
            "일반 %s명 | %s | %s".format(
                seatNumber?.size,
                seatNumber?.joinToString(),
                cinema.cinemaName,
            )
        binding.ticketCharge.text = ticketPrice
        binding.movieTimeDate.text = timeDate
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}
