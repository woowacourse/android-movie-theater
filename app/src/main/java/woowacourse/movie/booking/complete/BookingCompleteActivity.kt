package woowacourse.movie.booking.complete

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.MainActivity
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityBookingCompleteBinding
import woowacourse.movie.mapper.IntentCompat
import woowacourse.movie.ui.model.TicketUiModel

class BookingCompleteActivity : AppCompatActivity(), BookingCompleteContract.View {
    private val presenter = BookingCompletePresenter(this)

//    private lateinit var presenter: BookingCompleteContract.Presenter
    private lateinit var binding: ActivityBookingCompleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking_complete)
        setUpUi()

        presenter.initializeData(requireTicketOrFinish())

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUpUi() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun requireTicketOrFinish(): TicketUiModel {
        return IntentCompat.getParcelableExtra(
            intent,
            KEY_BOOKING_RESULT,
            TicketUiModel::class.java,
        )
            ?: run {
                Log.e(TAG, ERROR_EMPTY_BOOKING_RESULT_DATA)
                showToastErrorAndFinish(getString(R.string.booking_toast_message))
                throw IllegalStateException(ERROR_FINISH_ACTIVITY.format(KEY_BOOKING_RESULT))
            }
    }

    override fun showBookingCompleteResult(ticket: TicketUiModel) {
        binding.ticket = ticket
    }

    override fun showToastErrorAndFinish(message: String) {
        Log.d(TAG, message)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val TAG = "BookingCompleteActivity"
        private const val ERROR_EMPTY_BOOKING_RESULT_DATA = "인텐트에 영화 예매 정보(KEY_BOOKING_RESULT)가 없습니다."
        private const val ERROR_FINISH_ACTIVITY = "%s 데이터가 없어서 Activity를 종료했습니다"
        const val KEY_BOOKING_RESULT = "bookingResult"
    }
}
