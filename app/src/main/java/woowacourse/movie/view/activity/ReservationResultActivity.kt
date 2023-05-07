package woowacourse.movie.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.contract.ReservationResultContract
import woowacourse.movie.databinding.ActivityReservationResultBinding
import woowacourse.movie.getSerializableCompat
import woowacourse.movie.view.MovieView
import woowacourse.movie.model.mapper.TicketsMapper
import woowacourse.movie.model.mapper.TicketsMapper.toDomain
import woowacourse.movie.model.MovieUiModel
import woowacourse.movie.model.TicketsUiModel
import woowacourse.movie.presenter.ReservationResultPresenter
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class ReservationResultActivity() : AppCompatActivity(), ReservationResultContract.View {

    override val presenter: ReservationResultContract.Presenter by lazy {
        ReservationResultPresenter(
            this,
            ticketsUiModel
        )
    }
    private lateinit var binding: ActivityReservationResultBinding

    private val ticketsUiModel: TicketsUiModel by lazy { receiveTicketsUiModel() }
    private val movieUiModel: MovieUiModel by lazy { receiveMovieViewModel() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reservation_result)
        initView()
    }

    fun initView(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        renderMovieView()
        renderReservationDetailView()
    }

    private fun finishActivityWithMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        finish()
    }

    private fun renderMovieView() {
        MovieView(title = binding.movieReservationResultTitle).render(movieUiModel)
    }

    private fun renderReservationDetailView() {
        renderDate()
        renderPeopleCount()
        renderSeatInformation()
        renderTheaterInformation()
        presenter.updatePrice()
    }

    private fun renderDate() {
        val dateFormat =
            DateTimeFormatter.ofPattern(getString(R.string.reservation_datetime_format))
        binding.movieReservationResultDate.text =
            dateFormat.format(ticketsUiModel.list.first().date)
    }

    private fun renderPeopleCount() {
        binding.movieReservationResultPeopleCount.text =
            getString(R.string.reservation_people_count).format(ticketsUiModel.list.size)
    }

    private fun renderSeatInformation() {
        ticketsUiModel.list.forEachIndexed { index, ticket ->
            binding.movieReservationResultSeat.text =
                (binding.movieReservationResultSeat.text.toString() + ticket.seat.row + ticket.seat.col)
            if (index != ticketsUiModel.list.size - 1) binding.movieReservationResultSeat.text =
                binding.movieReservationResultSeat.text.toString() + ", "
        }
    }

    private fun renderTheaterInformation() {
        binding.movieReservationResultTheater.text = ticketsUiModel.list.first().theaterName
    }

    override fun setPriceTextView(price: Int) {
        val formattedPrice =
            NumberFormat.getNumberInstance(Locale.US).format(price)
        binding.movieReservationResultPrice.text =
            getString(R.string.reservation_price).format(formattedPrice)
    }

    private fun receiveTicketsUiModel(): TicketsUiModel {
        val ticketsUiModel = intent.extras?.getSerializableCompat<TicketsUiModel>(TICKETS_KEY_VALUE)
            ?: finishActivityWithMessage(getString(R.string.reservation_data_null_error))
        return ticketsUiModel as TicketsUiModel
    }

    private fun receiveMovieViewModel(): MovieUiModel {
        val movieUiModel = intent.extras?.getSerializableCompat<MovieUiModel>(MOVIE_KEY_VALUE)
            ?: finishActivityWithMessage(getString(R.string.movie_data_null_error))
        return movieUiModel as MovieUiModel
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val MOVIE_KEY_VALUE = "movie"
        private const val TICKETS_KEY_VALUE = "tickets"
        fun start(
            context: Context,
            movieUiModel: MovieUiModel,
            ticketsUiModel: TicketsUiModel
        ) {
            val intent = generateIntent(context, movieUiModel, ticketsUiModel)
            context.startActivity(intent)
        }

        fun generateIntent(
            context: Context,
            movieUiModel: MovieUiModel,
            ticketsUiModel: TicketsUiModel
        ): Intent {
            val intent = Intent(context, ReservationResultActivity::class.java)
            intent.putExtra(MOVIE_KEY_VALUE, movieUiModel)
            intent.putExtra(TICKETS_KEY_VALUE, ticketsUiModel)
            return intent
        }
    }
}
