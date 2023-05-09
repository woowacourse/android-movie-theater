package woowacourse.movie.presentation.complete

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.data.movie.MockMovieData
import woowacourse.movie.databinding.ActivityCompleteBinding
import woowacourse.movie.presentation.main.MainActivity
import woowacourse.movie.presentation.model.TicketModel
import woowacourse.movie.presentation.util.formatDotDateTimeColon
import woowacourse.movie.presentation.util.getParcelableExtraCompat
import woowacourse.movie.presentation.util.noIntentExceptionHandler

class CompleteActivity : AppCompatActivity(), CompleteContract.View {

    override val presenter: CompleteContract.Presenter by lazy {
        CompletePresenter(
            MockMovieData,
            this,
        )
    }

    private lateinit var binding: ActivityCompleteBinding

    private lateinit var ticketModel: TicketModel

    private fun initTicketModel() {
        ticketModel = intent.getParcelableExtraCompat<TicketModel>(TICKET)
            ?: return this.noIntentExceptionHandler(NO_TICKET_INFO_ERROR)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            startMainWithClearBackStack()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initView() {
        presenter.setMovieTitle(ticketModel.movieId)
        setMovieScreeningDate()
        setMovieTicketCount()
        setMoviePaymentAmount()
    }

    override fun setMovieTitle(movieTitle: String) {
        binding.textCompletedTitle.text = movieTitle
    }

    private fun setMovieScreeningDate() {
        binding.textCompletedScreeningDate.text =
            ticketModel.bookedDateTime.formatDotDateTimeColon()
    }

    private fun setMovieTicketCount() {
        binding.textCompletedTicketCount.text =
            getString(R.string.normal_ticket_count_seat_cinema).format(
                ticketModel.count,
                ticketModel.formatSeatsCombine(),
                ticketModel.cinemaName,
            )
    }

    private fun setMoviePaymentAmount() {
        binding.textCompletedPaymentAmount.text =
            getString(R.string.payment_on_site_amount).format(ticketModel.paymentMoney)
    }

    private fun startMainWithClearBackStack() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val TICKET = "TICKET"
        private const val NO_TICKET_INFO_ERROR = "티켓 정보가 없습니다."

        fun getIntent(context: Context, ticketModel: TicketModel): Intent {
            return Intent(context, CompleteActivity::class.java).apply {
                putExtra(TICKET, ticketModel)
            }
        }

        private fun TicketModel.formatSeatsCombine(): String {
            val stringBuilder = StringBuilder()
            this.seats.forEach { stringBuilder.append("$it ") }
            return stringBuilder.toString()
        }
    }
}
