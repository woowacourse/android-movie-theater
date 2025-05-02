package woowacourse.movie.presentation.result

import android.os.Bundle
import android.widget.Toast
import woowacourse.movie.R
import woowacourse.movie.common.BaseActivity
import woowacourse.movie.common.constant.IntentKeys
import woowacourse.movie.common.util.intentSerializable
import woowacourse.movie.databinding.ActivityBookingResultBinding
import woowacourse.movie.domain.model.movie.MovieTicket

class BookingResultActivity :
    BaseActivity<ActivityBookingResultBinding>(R.layout.activity_booking_result),
    BookingResultContract.View {
    private lateinit var presenter: BookingResultContract.Presenter
    private lateinit var ticket: MovieTicket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!fetchTicketFromIntent()) return
        presenter = BookingResultPresenter(this, ticket)
        presenter.onViewCreated()
    }

    override fun showTicket(ticket: MovieTicket) {
        binding.ticket = ticket
        binding.textviewNotice.text =
            String.format(getString(R.string.cancel_notice), CANCELABLE_TIME)
    }

    private fun fetchTicketFromIntent(): Boolean {
        val data = intent.intentSerializable(IntentKeys.TICKET, MovieTicket::class.java)
        if (data == null) {
            Toast.makeText(this, TICKET_INTENT_ERROR, Toast.LENGTH_SHORT).show()
            finish()
            return false
        }
        ticket = data
        return true
    }

    companion object {
        private const val CANCELABLE_TIME = 15
        private const val TICKET_INTENT_ERROR = "[ERROR] 예매 정보에 대한 키 값이 올바르지 않습니다."
    }
}
