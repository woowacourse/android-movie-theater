package woowacourse.movie.seats.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivitySeatsBinding
import woowacourse.movie.detail.view.DetailActivity
import woowacourse.movie.seats.contract.SeatsContract
import woowacourse.movie.seats.model.SeatGrade
import woowacourse.movie.seats.model.Seats
import woowacourse.movie.seats.presenter.SeatsPresenter
import woowacourse.movie.ticket.view.MovieTicketActivity

class SeatsActivity : AppCompatActivity(), SeatsContract.View {
    lateinit var presenter: SeatsContract.Presenter
    private lateinit var binding: ActivitySeatsBinding
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_seats)
        setContentView(binding.root)

        val theaterId = intent.getLongExtra(DetailActivity.EXTRA_THEATER_ID_KEY, -1)
        val movieId = intent.getLongExtra(DetailActivity.EXTRA_MOVIE_ID_KEY, -1)
        val screeningSchedule = intent.getStringExtra(DetailActivity.EXTRA_SCREENING_SCHEDULE_KEY) ?: ""
        val seatsCount = intent.getIntExtra(DetailActivity.EXTRA_COUNT_KEY, 0)

        presenter = SeatsPresenter(this, theaterId, movieId, screeningSchedule, seatsCount)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val selectedIndices = presenter.selectedSeats()
        outState.putIntegerArrayList(EXTRA_SELECTED_SEATS, selectedIndices)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val selectedIndices =
            savedInstanceState.getIntegerArrayList(EXTRA_SELECTED_SEATS) ?: arrayListOf()
        selectedIndices.forEach {
            presenter.onSeatClicked(it)
        }
    }

    override fun initClickListener() {
        binding.confirmButton.setOnClickListener {
            showDialog()
        }
    }

    override fun showMovieTitle(movieTitle: String) {
        binding.title.text = movieTitle
    }

    override fun showSeats(seats: Seats) {
        val seatsTable =
            binding.seatBoardLayout.children.filterIsInstance<TableRow>().flatMap { it.children }
                .filterIsInstance<Button>().toList()
        seatsTable.forEachIndexed { index, button ->
            val seatUIModel = seats.seats[index]
            button.text = seatUIModel.seat.toString()
            button.setTextColor(loadSeatColor(seatUIModel.seat.seatGrade))
            button.isSelected = seatUIModel.isSelected
            button.setOnClickListener {
                presenter.onSeatClicked(index)
            }
        }
    }

    override fun showTotalPrice(total: Int) {
        binding.totalPrice.text = getString(R.string.total_price_format, total)
    }

    override fun updateConfirmButton(enabled: Boolean) {
        binding.confirmButton.isEnabled = enabled
        binding.confirmButton.isSelected = enabled
    }

    override fun moveToReservationResult(movieTicketId: Int) {
        val intent = Intent(this, MovieTicketActivity::class.java)
        intent.putExtra(EXTRA_MOVIE_TICKET_ID, movieTicketId)
        startActivity(intent)
    }

    override fun showMessage(message: String) {
        toast?.cancel()
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.reservation_confirm_title))
        builder.setMessage(getString(R.string.reservation_confirm_message))
        builder.setCancelable(false)

        builder.setPositiveButton(getString(R.string.reservation_finish_text)) { _, _ ->
            presenter.requestReservationResult()
        }

        builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    private fun loadSeatColor(grade: SeatGrade): Int {
        return when (grade) {
            SeatGrade.B -> getColor(R.color.purple_500)
            SeatGrade.A -> getColor(R.color.teal_700)
            SeatGrade.S -> getColor(R.color.blue_500)
        }
    }

    companion object {
        const val EXTRA_SELECTED_SEATS = "selected_seats"
        const val EXTRA_MOVIE_TICKET_ID = "movie_ticket_Id"
    }
}
