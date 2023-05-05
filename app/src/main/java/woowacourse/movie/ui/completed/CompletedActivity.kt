package woowacourse.movie.ui.completed

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityCompletedBinding
import woowacourse.movie.model.ReservationUiModel
import woowacourse.movie.movie.MovieRepository
import woowacourse.movie.util.formatScreenDateTime
import woowacourse.movie.util.getParcelable

// todo mvp 적용하기
class CompletedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCompletedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCompletedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getResult()?.let {
            initView(it)
        } ?: finish()
    }

    private fun getResult(): ReservationUiModel? {
        return intent.getParcelable(RESERVATION, ReservationUiModel::class.java)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initView(reservation: ReservationUiModel) {
        val movie = MovieRepository.getMovie(reservation.movieId)

        binding.textCompletedTitle.text = movie.title
        binding.textCompletedScreeningDate.text =
            reservation.bookedDateTime.formatScreenDateTime()
        binding.textCompletedTicketCount.text =
            getString(R.string.ticket_count_seat_info, reservation.count, reservation.seatPosition)
        binding.textCompletedPaymentAmount.text =
            getString(R.string.payment_amount, reservation.payment)

        showBackButton()
    }

    private fun showBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        private const val RESERVATION = "RESERVATION"

        fun getIntent(context: Context, reservation: ReservationUiModel): Intent {
            return Intent(context, CompletedActivity::class.java).apply {
                putExtra(RESERVATION, reservation)
            }
        }
    }
}
