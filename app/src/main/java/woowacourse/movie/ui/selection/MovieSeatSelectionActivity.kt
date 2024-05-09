package woowacourse.movie.ui.selection

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieSeatSelectionBinding
import woowacourse.movie.model.data.ReservationsImpl
import woowacourse.movie.model.db.UserTicketRepositoryImpl
import woowacourse.movie.model.movie.Seat
import woowacourse.movie.ui.base.BaseActivity
import woowacourse.movie.ui.complete.MovieReservationCompleteActivity
import woowacourse.movie.ui.utils.positionToIndex

class MovieSeatSelectionActivity :
    BaseActivity<MovieSeatSelectionPresenter>(),
    MovieSeatSelectionContract.View {
    private lateinit var binding: ActivityMovieSeatSelectionBinding
    private val reservationId by lazy { reservationId() }
    private val selectedSeatInfo = mutableListOf<Int>()
    private val seats by lazy { binding.tlSeats.makeSeats() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_seat_selection)
        binding.presenter = presenter

        presenter.loadTheaterInfo(reservationId)
        presenter.updateSelectCompletion()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntegerArrayList(
            MovieSeatSelectionKey.SEAT_INFO,
            selectedSeatInfo as ArrayList<Int>,
        )
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val seats = savedInstanceState.getIntegerArrayList(MovieSeatSelectionKey.SEAT_INFO)
        seats?.let {
            it.forEach { seatIndex ->
                presenter.recoverSeatSelection(seatIndex)
                selectedSeatInfo.add(seatIndex)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initializePresenter(): MovieSeatSelectionPresenter =
        MovieSeatSelectionPresenter(this, ReservationsImpl, UserTicketRepositoryImpl.get())

    override fun showMovieTitle(movieTitle: String) {
        binding.movieTitle = movieTitle
    }

    override fun showTheater(
        rowSize: Int,
        colSize: Int,
    ) {
        repeat(rowSize) { row ->
            makeSeats(colSize, row)
        }
    }

    override fun showSelectedSeat(index: Int) {
        seats[index]
            .setBackgroundColor(
                ContextCompat.getColor(this, R.color.selected_seat),
            )
    }

    override fun showUnSelectedSeat(index: Int) {
        seats[index]
            .setBackgroundColor(
                ContextCompat.getColor(this, R.color.unSelected_seat),
            )
    }

    override fun showReservationTotalAmount(amount: Int) {
        binding.tvTotalSeatAmount.text =
            resources.getString(R.string.total_price)
                .format(amount)
    }

    override fun updateSelectCompletion(isComplete: Boolean) {
        binding.btnConfirm.apply {
            isEnabled = isComplete
            isClickable = isComplete
        }
    }

    override fun showSeatReservationConfirmation() {
        AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle(R.string.reservation_confirm)
            .setMessage(R.string.reservation_confirm_comment)
            .setPositiveButton(R.string.reservation_complete) { _, _ ->
                presenter.reservationSeat(this)
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun showReservationComplete(userTicketId: Long) {
        MovieReservationCompleteActivity.startActivity(this, userTicketId)
    }

    override fun showError(throwable: Throwable) {
        Log.e(TAG, throwable.message.toString())
        Toast.makeText(this, resources.getString(R.string.invalid_key), Toast.LENGTH_LONG).show()
        finish()
    }

    private fun TableLayout.makeSeats(): List<TextView> =
        children.filterIsInstance<TableRow>().flatMap { it.children }
            .filterIsInstance<TextView>().toList()

    private fun makeSeats(
        colSize: Int,
        row: Int,
    ) {
        repeat(colSize) { col ->
            seats[positionToIndex(row, col)].apply {
                setOnClickListener {
                    selectedSeatInfo.add(positionToIndex(row, col))
                }
            }
        }
    }

    private fun reservationId() =
        intent.getLongExtra(
            MovieSeatSelectionKey.RESERVATION_ID,
            MOVIE_CONTENT_ID_DEFAULT_VALUE,
        )

    companion object {
        private val TAG = MovieSeatSelectionActivity::class.simpleName
        private const val MOVIE_CONTENT_ID_DEFAULT_VALUE = -1L

        fun startActivity(
            context: Context,
            reservationId: Long,
        ) = Intent(context, MovieSeatSelectionActivity::class.java).run {
            putExtra(MovieSeatSelectionKey.RESERVATION_ID, reservationId)
            context.startActivity(this)
        }
    }
}

@BindingAdapter("row", "column")
fun setPosition(
    textView: TextView,
    row: Int,
    column: Int,
) {
    textView.text = Seat(row, column).toString()
}
