package woowacourse.movie.purchaseConfirmation

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import woowacourse.movie.databinding.PurchaseConfirmationBinding
import woowacourse.movie.model.Cinema

class PurchaseConfirmationActivity : AppCompatActivity() {

    private val binding: PurchaseConfirmationBinding by lazy {
        PurchaseConfirmationBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val ticketPrice = intent.getStringExtra("ticketPrice")
        val seatNumber = intent.getStringArrayExtra("seatNumber")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val cinema =
            IntentCompat.getSerializableExtra(intent, "Cinema", Cinema::class.java) ?: error("")
        val movie = cinema.theater.movie
        binding.movieTitleConfirmation.text = movie.title.toString()
        binding.purchaseMovieRunningTime.text = movie.runningTime.toString()
        binding.reservedInformation.text =
            "%s | %s | %s".format(seatNumber?.size, seatNumber?.joinToString(), cinema.cinemaName)
        binding.ticketCharge.text = ticketPrice
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}
