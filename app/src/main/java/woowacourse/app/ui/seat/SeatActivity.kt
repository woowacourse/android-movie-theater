package woowacourse.app.ui.seat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import woowacourse.app.model.Mapper.toDomainModel
import woowacourse.app.model.Mapper.toUiModel
import woowacourse.app.model.SelectedSeatUiModel
import woowacourse.app.ui.completed.CompletedActivity
import woowacourse.app.util.getParcelable
import woowacourse.app.util.getParcelableBundle
import woowacourse.app.util.shortToast
import woowacourse.domain.SelectResult
import woowacourse.domain.SelectedSeat
import woowacourse.domain.movie.Movie
import woowacourse.domain.movie.MovieRepository
import woowacourse.domain.theater.Theater
import woowacourse.domain.theater.TheaterRepository
import woowacourse.domain.ticket.Seat
import woowacourse.movie.R

class SeatActivity : AppCompatActivity() {
    private val textPayment by lazy { findViewById<TextView>(R.id.textSeatPayment) }
    private val buttonConfirm by lazy { findViewById<TextView>(R.id.buttonSeatConfirm) }
    private val table by lazy { findViewById<SeatTableLayout>(R.id.seatTableLayout) }

    private lateinit var bookedMovie: woowacourse.app.model.BookedMovie
    private lateinit var movie: Movie
    private lateinit var theater: Theater
    private lateinit var selectedSeat: SelectedSeat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat)
        getData()
        initSeatTable()
        initView()
        clickConfirmButton()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable("SELECTED_SEAT", selectedSeat.toUiModel())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val selectedSeatUiModel =
            savedInstanceState.getParcelableBundle("SELECTED_SEAT", SelectedSeatUiModel::class.java)
        restoreSelectedSeat(selectedSeatUiModel)
    }

    private fun restoreSelectedSeat(selectedSeatUiModel: SelectedSeatUiModel) {
        selectedSeatUiModel.selectedSeat.forEach {
            setSeatState(it.toDomainModel())
        }
    }

    private fun getData() {
        bookedMovie =
            intent.getParcelable(BOOKED_MOVIE, woowacourse.app.model.BookedMovie::class.java)
                ?: return finish()
        movie = MovieRepository.getMovie(bookedMovie.movieId)
        theater = TheaterRepository.getTheater(bookedMovie.theaterId)
        selectedSeat = SelectedSeat(bookedMovie.ticketCount)
    }

    private fun initSeatTable() {
        table.setTable(theater.rowSize, theater.columnSize)
        table.setColorRange(
            mapOf(
                theater.sRankRange to R.color.green_400,
                theater.aRankRange to R.color.blue_500,
                theater.bRankRange to R.color.purple_400,
            ),
        )
        table.setClickListener { clickedPosition ->
            val seat = theater.selectSeat(clickedPosition)
            setSeatState(seat)
        }
    }

    private fun setSeatState(seat: Seat) {
        val view = table[seat.position.row][seat.position.column]
        val result = selectedSeat.clickSeat(seat)
        when (result) {
            SelectResult.Select.Full -> shortToast("더 이상 좌석을 선택할 수 없습니다.")
            SelectResult.Select.Success -> view.isSelected = !view.isSelected
            SelectResult.Unselect -> view.isSelected = !view.isSelected
        }
        setConfirmButtonEnable(selectedSeat.isSeatFull)
        setPayment()
    }

    private fun setConfirmButtonEnable(isSeatFull: Boolean) {
        buttonConfirm.isEnabled = isSeatFull

        if (buttonConfirm.isEnabled) {
            buttonConfirm.setBackgroundResource(R.color.purple_700)
            return
        }
        buttonConfirm.setBackgroundResource(R.color.gray_400)
    }

    private fun clickConfirmButton() {
        buttonConfirm.setOnClickListener {
            showDialog()
        }
    }

    private fun completeBooking() {
        val reservation = selectedSeat.reserve(movie, bookedMovie.bookedDateTime)
        ScreeningTimeReminder(this, reservation.toUiModel())
        startActivity(CompletedActivity.getIntent(this, reservation.toUiModel()))
        finish()
    }

    private fun showDialog() {
        AlertDialog.Builder(this)
            .setTitle("예매 확인")
            .setMessage("정말 예매하시겠습니까?")
            .setPositiveButton("예") { _, _ -> completeBooking() }
            .setNegativeButton("아니요") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun setPayment() {
        textPayment.text =
            selectedSeat.getPayment(movie, bookedMovie.bookedDateTime).toString() + "원"
    }

    private fun initView() {
        findViewById<TextView>(R.id.textSeatMovieTitle).text = movie.title
    }

    companion object {
        private const val BOOKED_MOVIE = "BOOKED_MOVIE"

        fun getIntent(context: Context, bookedMovie: woowacourse.app.model.BookedMovie): Intent {
            return Intent(context, SeatActivity::class.java).apply {
                putExtra(BOOKED_MOVIE, bookedMovie)
            }
        }
    }
}
