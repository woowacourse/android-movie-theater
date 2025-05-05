package woowacourse.movie.booking.complete

import android.content.Context
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

    private lateinit var binding: ActivityBookingCompleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking_complete)
        setUpUi()

        val ticket = requireTicketOrFinish() ?: return
        presenter.initializeData(ticket)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUpUi() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun requireTicketOrFinish(): TicketUiModel? {
        val ticket =
            IntentCompat.getParcelableExtra(
                intent,
                KEY_BOOKING_RESULT,
                TicketUiModel::class.java,
            )

        if (ticket == null) {
            showToastErrorAndFinish(getString(R.string.booking_toast_message))
            return null
        }
        return ticket
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
        private const val KEY_BOOKING_RESULT = "bookingResult"

        fun createIntent(
            context: Context,
            ticket: TicketUiModel,
        ): Intent {
            return Intent(context, BookingCompleteActivity::class.java).apply {
                putExtra(KEY_BOOKING_RESULT, ticket)
            }
        }
    }
}
