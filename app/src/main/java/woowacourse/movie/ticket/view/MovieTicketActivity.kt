package woowacourse.movie.ticket.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieTicketBinding
import woowacourse.movie.seats.view.SeatsActivity.Companion.EXTRA_MOVIE_TICKET_ID
import woowacourse.movie.ticket.contract.MovieTicketContract
import woowacourse.movie.ticket.model.MovieTicket
import woowacourse.movie.ticket.model.MovieTicketRepositoryImpl
import woowacourse.movie.ticket.presenter.MovieTicketPresenter

class MovieTicketActivity : AppCompatActivity(), MovieTicketContract.View {
    private lateinit var presenter: MovieTicketPresenter
    private lateinit var binding: ActivityMovieTicketBinding
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_ticket)
        setContentView(binding.root)
        val movieTicketId = intent.getIntExtra(EXTRA_MOVIE_TICKET_ID, -1)
        presenter = MovieTicketPresenter(this, MovieTicketRepositoryImpl, movieTicketId)
    }

    override fun showTicketData(movieTicket: MovieTicket) {
        binding.movieTicket = movieTicket
        binding.executePendingBindings()
    }

    override fun showMessage(message: String) {
        toast?.cancel()
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast?.show()
    }
}
