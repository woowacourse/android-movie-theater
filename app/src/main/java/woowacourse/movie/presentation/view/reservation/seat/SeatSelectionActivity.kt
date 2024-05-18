package woowacourse.movie.presentation.view.reservation.seat

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.database.ReservationDatabase
import woowacourse.movie.databinding.ActivitySeatSelectionBinding
import woowacourse.movie.databinding.ReservationConfirmDialogBinding
import woowacourse.movie.presentation.base.BaseActivity
import woowacourse.movie.presentation.uimodel.MovieTicketUiModel
import woowacourse.movie.presentation.view.reservation.detail.MovieDetailActivity.Companion.RESERVATION_COUNT_KEY
import woowacourse.movie.presentation.view.reservation.detail.MovieDetailActivity.Companion.TITLE_KEY
import woowacourse.movie.presentation.view.reservation.result.ReservationResultActivity

class SeatSelectionActivity : BaseActivity(), SeatSelectionContract.View, SeatSelectionContract.ViewActions {
    private lateinit var reservationDatabase: ReservationDatabase
    private lateinit var seatSelectionPresenter: SeatSelectionContract.Presenter
    private lateinit var activitySeatSelectionBinding: ActivitySeatSelectionBinding
    private lateinit var dialogBinding: ReservationConfirmDialogBinding
    private val dialog: Dialog by lazy {
        Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(dialogBinding.root)
            setCancelable(false)
        }
    }

    override fun getLayoutResId(): Int = R.layout.activity_seat_selection

    override fun onCreateSetup(savedInstanceState: Bundle?) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        reservationDatabase = ReservationDatabase.getDatabase(applicationContext)

        activitySeatSelectionBinding = DataBindingUtil.setContentView(this, R.layout.activity_seat_selection)
        activitySeatSelectionBinding.listener = this
        dialogBinding = ReservationConfirmDialogBinding.inflate(LayoutInflater.from(this))
        dialogBinding.listener = this

        activitySeatSelectionBinding.movieTitle.text = intent.getStringExtra(TITLE_KEY)
        val reservationCount = intent.getIntExtra(RESERVATION_COUNT_KEY, DEFAULT_COUNT)

        seatSelectionPresenter = SeatSelectionPresenterImpl(reservationCount, reservationDatabase.reservationDao())
        seatSelectionPresenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        seatSelectionPresenter.detachView()
    }

    override fun showSeatingChart(
        rowCount: Int,
        colCount: Int,
        seatRankInfo: List<IntRange>,
    ) {
        drawEachRowSeats(rowCount, colCount, seatRankInfo)
    }

    private fun drawEachRowSeats(
        rowCount: Int,
        colCount: Int,
        seatRankInfo: List<IntRange>,
    ) {
        repeat(rowCount) { row ->
            val rowSeats =
                TableRow(this).apply {
                    layoutParams =
                        TableLayout.LayoutParams(
                            TableLayout.LayoutParams.WRAP_CONTENT,
                            TableLayout.LayoutParams.WRAP_CONTENT,
                        )
                }
            rowSeats.setPadding(10, 10, 10, 10)
            val color = getColorByRank(seatRankInfo, row)
            drawEachColumnSeats(rowSeats, row, colCount, color)
            activitySeatSelectionBinding.seatingChartLayout.addView(rowSeats)
        }
    }

    private fun getColorByRank(
        seatRankInfo: List<IntRange>,
        row: Int,
    ): Int {
        return when (row) {
            in seatRankInfo[0] -> B_RANK_COLOR
            in seatRankInfo[1] -> S_RANK_COLOR
            in seatRankInfo[2] -> A_RANK_COLOR
            else -> Color.BLACK
        }
    }

    private fun drawEachColumnSeats(
        rowSeats: TableRow,
        row: Int,
        colCount: Int,
        color: Int,
    ) {
        repeat(colCount) { col ->
            val seatView =
                TextView(this).apply {
                    layoutParams =
                        TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT,
                        )
                    gravity = Gravity.CENTER
                    setPadding(15, 30, 15, 30)
                    textSize = 18f
                    text =
                        String.format(
                            SEAT_POSITION_TEXT_FORMAT,
                            SEAT_ROW_START_VALUE + row,
                            SEAT_COL_START_VALUE + col,
                        )
                    setSeatClickListener(this, row, col)
                }
            seatView.setBackgroundColor(WHITE_COLOR)
            seatView.setTextColor(color)
            rowSeats.addView(seatView)
        }
    }

    private fun setSeatClickListener(
        seatView: TextView,
        row: Int,
        col: Int,
    ) {
        seatView.setOnClickListener {
            seatSelectionPresenter.selectSeat(row, col)
        }
    }

    override fun changeSeatColor(
        row: Int,
        col: Int,
    ) {
        val seatsRow = activitySeatSelectionBinding.seatingChartLayout.getChildAt(row) as TableRow
        val seatView = seatsRow.getChildAt(col) as TextView
        val currentColor = (seatView.background as? ColorDrawable)?.color
        val newColor =
            when (currentColor) {
                WHITE_COLOR -> YELLOW_COLOR
                YELLOW_COLOR -> WHITE_COLOR
                else -> WHITE_COLOR
            }
        seatView.setBackgroundColor(newColor)
    }

    override fun updateTotalPrice(price: Int) {
        activitySeatSelectionBinding.totalPrice.text = this.getString(R.string.seat_total_price_format, price)
    }

    override fun changeConfirmClickable(hasMatchedCount: Boolean) {
        activitySeatSelectionBinding.confirmButton.isEnabled = hasMatchedCount
    }

    override fun showAlreadyFilledSeatsSelectionMessage() {
        viewToastMessage(FILLED_SEATS_COUNT_MESSAGE, SHORT_DURATION)
    }

    override fun moveToReservationResult(movieTicketUiModel: MovieTicketUiModel) {
        val intent = Intent(this, ReservationResultActivity::class.java)
        intent.putExtra(ReservationResultActivity.INTENT_TICKET, movieTicketUiModel)
        intent.putExtra(ReservationResultActivity.INTENT_SET_ALARM, true)
        startActivity(intent)
    }

    private fun viewToastMessage(
        message: String?,
        duration: Int,
    ) {
        runOnUiThread {
            Toast.makeText(this, message, duration).show()
        }
    }

    override fun onConfirmButtonClicked() {
        dialog.show()
    }

    override fun onDialogAcceptButtonClicked() {
        seatSelectionPresenter.onAcceptButtonClicked()
        dialog.dismiss()
    }

    override fun onDialogCancelButtonClicked() {
        dialog.dismiss()
    }

    companion object {
        const val DEFAULT_COUNT = 1
        const val SEAT_POSITION_TEXT_FORMAT = "%c%d"
        const val SEAT_ROW_START_VALUE = 'A'
        const val SEAT_COL_START_VALUE = 1
        const val FILLED_SEATS_COUNT_MESSAGE = "예매할 좌석 개수를 모두 선택했습니다."
        const val SHORT_DURATION = 5
        val B_RANK_COLOR = Color.argb(255, 142, 19, 236)
        val A_RANK_COLOR = Color.argb(255, 25, 211, 88)
        val S_RANK_COLOR = Color.argb(255, 27, 72, 233)
        const val WHITE_COLOR = Color.WHITE
        const val YELLOW_COLOR = Color.YELLOW
    }
}
