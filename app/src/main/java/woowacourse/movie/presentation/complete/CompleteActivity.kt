package woowacourse.movie.presentation.complete

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityCompletedBinding
import woowacourse.movie.model.data.remote.DummyMovieStorage
import woowacourse.movie.presentation.main.MainActivity
import woowacourse.movie.presentation.model.TicketModel
import woowacourse.movie.presentation.util.getParcelableExtraCompat
import woowacourse.movie.util.intentDataNullProcess

class CompleteActivity : AppCompatActivity(), CompleteContract.View {

    private lateinit var binding: ActivityCompletedBinding

    override lateinit var presenter: CompleteContract.Presenter
    private lateinit var ticketModel: TicketModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_completed)

        getTicket()
        initPresenter()
        initBindingData(ticketModel)
    }

    private fun initPresenter() {
        presenter = CompletePresenter(this, DummyMovieStorage())
    }

    private fun getTicket() {
        ticketModel = intent.getParcelableExtraCompat<TicketModel>(TICKET)
            ?: return this.intentDataNullProcess(TICKET)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initBindingData(ticket: TicketModel) {
        binding.movie = presenter.getMovieById(ticket.movieId)
        binding.ticket = ticket
    }

    override fun finish() {
        super.finish()
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
    }

    companion object {
        const val TICKET = "TICKET"

        fun getIntent(context: Context, ticketModel: TicketModel): Intent {
            return Intent(context, CompleteActivity::class.java).apply {
                putExtra(TICKET, ticketModel)
            }
        }
    }
}
