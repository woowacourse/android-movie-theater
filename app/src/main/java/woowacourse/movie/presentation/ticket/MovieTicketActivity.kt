package woowacourse.movie.presentation.ticket

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.data.repository.MovieTicketRepositoryImpl
import woowacourse.movie.databinding.ActivityMovieTicketBinding
import woowacourse.movie.domain.model.movieticket.MovieTicket
import woowacourse.movie.presentation.seats.SeatsActivity.Companion.EXTRA_MOVIE_TICKET_ID

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
