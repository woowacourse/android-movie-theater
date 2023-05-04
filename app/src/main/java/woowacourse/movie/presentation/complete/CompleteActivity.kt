package woowacourse.movie.presentation.complete

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.presentation.main.MainActivity
import woowacourse.movie.presentation.model.MovieModel
import woowacourse.movie.presentation.model.TicketModel
import woowacourse.movie.presentation.util.formatDotDateTimeColon

class CompleteActivity : AppCompatActivity(), CompleteContract.View {

    override val presenter: CompleteContract.Presenter by lazy { CompletePresenter(this) }

    override val ticketModel by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(TICKET, TicketModel::class.java)
                ?: throw IllegalArgumentException()
        } else {
            intent.getParcelableExtra(TICKET) ?: throw IllegalArgumentException()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_completed)

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
        val movie = presenter.requireMovieModel()
        setMovieTitle(movie)
        setMovieScreeningDate()
        setMovieTicketCount()
        setMoviePaymentAmount()
    }

    private fun setMovieTitle(movieModel: MovieModel) {
        findViewById<TextView>(R.id.textCompletedTitle).text = movieModel.title
    }

    private fun setMovieScreeningDate() {
        findViewById<TextView>(R.id.textCompletedScreeningDate).text =
            ticketModel.bookedDateTime.formatDotDateTimeColon()
    }

    private fun setMovieTicketCount() {
        findViewById<TextView>(R.id.textCompletedTicketCount).text =
            getString(R.string.normal_ticket_count_seat).format(
                ticketModel.count,
                ticketModel.formatSeatsCombine(),
            )
    }

    private fun setMoviePaymentAmount() {
        findViewById<TextView>(R.id.textCompletedPaymentAmount).text =
            getString(R.string.payment_on_site_amount).format(ticketModel.paymentMoney)
    }

    private fun startMainWithClearBackStack() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }

    companion object {
        const val TICKET = "TICKET"

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
