package woowacourse.movie.view.complete

import android.content.Context
import android.content.Intent
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
import woowacourse.movie.view.core.ext.requireSerializable
import woowacourse.movie.view.uiModel.toUiModel

class BookingCompleteActivity : AppCompatActivity(), BookingCompleteContract.View {
    private lateinit var binding: ActivityBookingCompleteBinding
    private lateinit var presenter: BookingCompleteContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking_complete)

        intent.requireSerializable<Ticket>(KEY_TICKET).apply {
            presenter = BookingCompletePresenter(this@BookingCompleteActivity, this)
        }

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
                finish()
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
        const val KEY_TICKET = "BOOKING_TICKET"

        fun newIntent(
            context: Context,
            ticket: Ticket,
        ) = Intent(context, BookingCompleteActivity::class.java).apply {
            putExtra(KEY_TICKET, ticket)
        }
    }
}
