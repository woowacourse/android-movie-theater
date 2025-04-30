package woowacourse.movie

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.booking.complete.BookingCompleteContract
import woowacourse.movie.booking.complete.BookingCompletePresenter
import woowacourse.movie.booking.detail.TicketUiModel
import woowacourse.movie.databinding.ActivityBookingCompleteBinding
import woowacourse.movie.mapper.IntentCompat

class BookingCompleteActivity : AppCompatActivity(), BookingCompleteContract.View {
    private lateinit var presenter: BookingCompleteContract.Presenter
    private lateinit var binding: ActivityBookingCompleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking_complete)
        setUpUi()

        val bookingResult = requireResultOrFinish()
        presenter = BookingCompletePresenter(this, bookingResult)
        presenter.initializeData()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUpUi() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun requireResultOrFinish(): TicketUiModel {
        return IntentCompat.getParcelableExtra(
            intent,
            KEY_BOOKING_RESULT,
            TicketUiModel::class.java,
        )
            ?: run {
                Log.e(TAG, "인텐트에 영화 예매 정보(KEY_BOOKING_RESULT)가 없습니다.")
                showToastErrorAndFinish(getString(R.string.booking_toast_message))
                throw IllegalStateException("Movie 데이터가 없어서 Activity를 종료했습니다")
            }
    }

    override fun showBookingCompleteResult(ticketUiData: TicketUiModel) {
        binding.ticket = ticketUiData
    }

    override fun showToastErrorAndFinish(message: String) {
        Log.d(TAG, message)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    companion object {
        private const val TAG = "BookingCompleteActivity"
        const val KEY_BOOKING_RESULT = "bookingResult"
    }
}
