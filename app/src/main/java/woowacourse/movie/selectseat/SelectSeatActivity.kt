package woowacourse.movie.selectseat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.data.DummyMovies
import woowacourse.movie.databinding.ActivitySelectSeatBinding
import woowacourse.movie.moviereservation.uimodel.BookingInfo
import woowacourse.movie.reservationresult.ReservationResultActivity
import woowacourse.movie.selectseat.uimodel.SeatUiModel
import woowacourse.movie.selectseat.uimodel.SelectState
import woowacourse.movie.selectseat.uimodel.SelectedSeatsUiModel
import woowacourse.movie.util.bundleParcelable
import woowacourse.movie.util.intentParcelable
import woowacourse.movie.util.showAlertDialog
import woowacourse.movie.util.showErrorToastMessage

class SelectSeatActivity : AppCompatActivity(), SelectSeatContract.View {
    private lateinit var presenter: SelectSeatContract.Presenter
    private lateinit var selectedSeats: SelectedSeatsUiModel
    private lateinit var bookingInfoUiModel: BookingInfo
    private var state: SelectState = SelectState.NONE

    private lateinit var binding: ActivitySelectSeatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectSeatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        bookingInfoUiModel =
            intent.intentParcelable(EXTRA_BOOKING_ID, BookingInfo::class.java)
                ?: error("bookingInfo에 대한 정보가 없습니다.")
        presenter = SelectSeatPresenter(this, DummyMovies)

        initView()
        clickReserveButton()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable(EXTRA_SEATS_ID, selectedSeats)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val selectedSeats =
            savedInstanceState.bundleParcelable(EXTRA_SEATS_ID, SelectedSeatsUiModel::class.java)
        selectedSeats?.let {
            this.selectedSeats = selectedSeats
            this.selectedSeats.seats.forEach {
                val seatView = tableChildView(it.row, it.col)
                seatView.isChecked = true
                presenter.changeSeatState(it)
            }
        }
    }

    private fun initView() {
        presenter.loadSeat(bookingInfoUiModel.screenMovieId, bookingInfoUiModel.count)
        presenter.loadReservationInfo(bookingInfoUiModel.screenMovieId)
        binding.state = state
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showSeat(theaterSeats: List<SeatUiModel>) {
        selectedSeats = SelectedSeatsUiModel()
        theaterSeats.forEach { seatUiModel ->
            val seatView: CheckBox = tableChildView(seatUiModel.row, seatUiModel.col)
            seatView.setTextColor(getColor(seatUiModel.rateColor.color))
            seatView.text = seatUiModel.showPosition
            seatView.setOnClickListener {
                presenter.changeSeatState(seatUiModel)
            }
        }
    }

    override fun showMovieInfo(
        title: String,
        price: Int,
    ) {
        binding.tvSelectSeatTitle.text = title
        binding.tvSelectSeatPrice.text = getString(R.string.select_seat_price_format).format(price)
    }

    override fun updatePrice(updatedPrice: Int) {
        binding.tvSelectSeatPrice.text =
            getString(R.string.select_seat_price_format).format(updatedPrice)
    }

    override fun updateSeatState(
        selectedSeats: List<SeatUiModel>,
        selectState: SelectState,
    ) {
        this.selectedSeats = SelectedSeatsUiModel(selectedSeats)
        state = selectState
        binding.selectedSeats = this.selectedSeats
        binding.state = state
    }

    override fun navigateToResult(reservationId: Long) {
        startActivity(ReservationResultActivity.getIntent(this, reservationId))
    }

    private fun tableChildView(
        row: Int,
        col: Int,
    ): CheckBox {
        val tableRow = binding.tlSelectSeat.getChildAt(row) as TableRow
        return tableRow.getChildAt(col) as CheckBox
    }

    private fun clickReserveButton() {
        binding.btnSelectSeatReserve.setOnClickListener {
            showClickResult()
        }
    }

    private fun showClickResult() {
        when (state) {
            SelectState.EXCEED ->
                showErrorToastMessage(
                    this,
                    getString(R.string.select_more_seat_error_message),
                )

            SelectState.LESS ->
                showErrorToastMessage(
                    this,
                    getString(R.string.select_less_seat_error_message),
                )

            SelectState.NONE ->
                showErrorToastMessage(
                    this,
                    getString(R.string.select_no_seat_error_message),
                )

            SelectState.SUCCESS -> confirmAlertDialog()
        }
    }

    private fun confirmAlertDialog() =
        showAlertDialog(
            this,
            "예매 확인",
            "정말 예매하시겠습니까?",
            "예매 완료",
            onPositiveButtonClicked = {
                presenter.completeReservation(
                    bookingInfoUiModel,
                )
            },
            "취소",
        )

    companion object {
        private const val EXTRA_BOOKING_ID: String = "bookingId"
        private const val EXTRA_SEATS_ID: String = "seatsId"

        fun getIntent(
            context: Context,
            bookingInfoUiModel: BookingInfo,
        ): Intent {
            return Intent(context, SelectSeatActivity::class.java).apply {
                putExtra(EXTRA_BOOKING_ID, bookingInfoUiModel)
            }
        }
    }
}
