package woowacourse.movie.view.reservation.result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationResultBinding
import woowacourse.movie.domain.model.ReservationInfo
import woowacourse.movie.view.MainActivity
import woowacourse.movie.view.base.BaseActivity
import woowacourse.movie.view.extension.getParcelableCompat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ReservationResultActivity :
    BaseActivity<ActivityReservationResultBinding>(R.layout.activity_reservation_result),
    ReservationResultContract.View {
    val presenter = ReservationResultPresenter(this)
    lateinit var reservationInfo: ReservationInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        reservationInfo = intent.getParcelableCompat<ReservationInfo>(BUNDLE_KEY_RESERVATION_INFO)
        binding.result = reservationInfo

        presenter.loadReservationInfo(reservationInfo)

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val intent = Intent(this@ReservationResultActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            },
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> startActivity(Intent(this, MainActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }

    override fun showReservationResult(reservationInfo: ReservationInfo) {
        displayReservationResult(reservationInfo)
    }

    private fun displayReservationResult(reservationInfo: ReservationInfo) {
        setupCancelDescription()
        setupMovieDate(reservationInfo.reservationDateTime)
        setupSeats()
    }

    private fun setupSeats() {
        val tvReservationSeats = findViewById<TextView>(R.id.tv_reservation_seats)
        tvReservationSeats.text =
            getString(
                R.string.seat_split_line,
                reservationInfo.seats.joinToString(", ") { "${'A' + it.row}${it.column + 1}" },
            )
    }

    private fun setupCancelDescription() {
        val tvCancelDescription = findViewById<TextView>(R.id.tv_cancel_description)
        tvCancelDescription?.text =
            getString(R.string.reservation_result_cancel_time_description, CANCELLATION_TIME)
    }

    private fun setupMovieDate(reservationDateTime: LocalDateTime?) {
        val tvMovieDate = findViewById<TextView>(R.id.tv_movie_date)
        tvMovieDate.text =
            reservationDateTime?.format(
                DateTimeFormatter.ofPattern(
                    getString(R.string.reservation_datetime_format),
                ),
            )
    }

    companion object {
        private const val CANCELLATION_TIME = 15
        private const val BUNDLE_KEY_RESERVATION_INFO = "reservation_info"

        fun newIntent(
            context: Context,
            reservationInfo: ReservationInfo,
        ): Intent =
            Intent(context, ReservationResultActivity::class.java).apply {
                putExtra(
                    BUNDLE_KEY_RESERVATION_INFO,
                    reservationInfo,
                )
            }
    }
}
