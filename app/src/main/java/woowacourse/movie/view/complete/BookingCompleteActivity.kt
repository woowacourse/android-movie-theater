package woowacourse.movie.view.complete

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityBookingCompleteBinding
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.view.MainActivity
import woowacourse.movie.view.uiModel.toUiModel

class BookingCompleteActivity : AppCompatActivity(), BookingCompleteContract.View {
    private lateinit var binding: ActivityBookingCompleteBinding
    private lateinit var presenter: BookingCompleteContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking_complete)

        val ticketId = intent.getLongExtra(KEY_TICKET_ID, 0)
        presenter = BookingCompletePresenter.initialize(this, applicationContext)
        presenter.loadTicket(ticketId)
        initView()
    }

    private fun initView() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val intent =
                    Intent(this, MainActivity::class.java).apply {
                        addFlags(FLAG_ACTIVITY_CLEAR_TOP)
                        addFlags(FLAG_ACTIVITY_SINGLE_TOP)
                    }
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showTicket(ticket: Ticket) {
        binding.model =
            ticket.toUiModel(
                getString(R.string.formatter_booking_schedule),
                getString(R.string.formatter_text_seat_formatter),
                getString(R.string.formatter_general_people_count),
                getString(R.string.formatter_on_site_payment),
            )
    }

    companion object {
        const val KEY_TICKET_ID = "TICKET_ID"

        fun newIntent(
            context: Context,
            ticketId: Long,
        ) = Intent(context, BookingCompleteActivity::class.java).apply {
            putExtra(KEY_TICKET_ID, ticketId)
        }
    }
}
