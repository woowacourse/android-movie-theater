package woowacourse.movie.reservationresult

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.data.AlarmSharedPreferences
import woowacourse.movie.data.RoomMovieRepository
import woowacourse.movie.databinding.ActivityReservationResultBinding
import woowacourse.movie.reservationresult.uimodel.ReservationResultUiModel

class ReservationResultActivity : AppCompatActivity(), ReservationResultContract.View {
    private lateinit var binding: ActivityReservationResultBinding
    private lateinit var presenter: ReservationResultPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val reservationId = intent.getLongExtra(EXTRA_RESERVATION_ID, INVALID_RESERVATION_ID)

        presenter =
            ReservationResultPresenter(
                repository = RoomMovieRepository.instance(),
                view = this,
            )
        presenter.loadReservationResult(reservationId)

        val alarmSetting = AlarmSharedPreferences(this)
        if (alarmSetting.isReservationAlarm()) {
            presenter.setAlarm(reservationId, this)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showResult(reservationResult: ReservationResultUiModel) {
        with(binding) {
            tvResultCancelDeadline.text =
                getString(
                    R.string.reservation_result_cancel_deadline_message,
                    reservationResult.cancelDeadLine,
                )
            tvResultTitle.text = reservationResult.title
            tvResultRunningDate.text = reservationResult.dateTime
            tvResultBookingDetail.text =
                getString(R.string.booking_detail_info_format).format(
                    reservationResult.headCount,
                    reservationResult.seats.joinToString { it.showPosition },
                    reservationResult.theaterName,
                )
            tvResultTotalPrice.text =
                getString(R.string.reservation_total_price_format, reservationResult.totalPrice)
        }
    }

    companion object {
        const val EXTRA_RESERVATION_ID: String = "reservationId"
        const val INVALID_RESERVATION_ID: Long = -1

        fun getIntent(
            context: Context,
            reservationId: Long,
        ): Intent {
            return Intent(context, ReservationResultActivity::class.java).apply {
                putExtra(EXTRA_RESERVATION_ID, reservationId)
            }
        }
    }
}
