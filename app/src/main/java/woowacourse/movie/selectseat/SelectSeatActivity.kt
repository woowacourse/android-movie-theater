package woowacourse.movie.selectseat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.data.DummyMovies
import woowacourse.movie.databinding.ActivitySelectSeatBinding
import woowacourse.movie.moviereservation.uimodel.BookingInfoUiModel
import woowacourse.movie.reservationresult.ReservationResultActivity
import woowacourse.movie.selectseat.uimodel.PriceUiModel
import woowacourse.movie.selectseat.uimodel.SeatUiModel
import woowacourse.movie.selectseat.uimodel.SelectResult
import woowacourse.movie.selectseat.uimodel.SelectedSeatsUiModel
import woowacourse.movie.util.bundleParcelable
import woowacourse.movie.util.intentParcelable
import woowacourse.movie.util.showAlertDialog

class SelectSeatActivity : AppCompatActivity(), SelectSeatContract.View {
    private lateinit var presenter: SelectSeatContract.Presenter
    private lateinit var selectedSeats: SelectedSeatsUiModel
    private lateinit var bookingInfoUiModel: BookingInfoUiModel

    private lateinit var binding: ActivitySelectSeatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectSeatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        bookingInfoUiModel =
            intent.intentParcelable(EXTRA_BOOKING_ID, BookingInfoUiModel::class.java)
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
        binding.btnSelectSeatReserve.isEnabled = this.selectedSeats.seats.isNotEmpty()
    }

    private fun initView() {
        binding.btnSelectSeatReserve.setOnClickListener {
            presenter.completeReservation(bookingInfoUiModel)
        }

        presenter.loadSeat(bookingInfoUiModel.screenMovieId)
        presenter.loadReservationInfo(bookingInfoUiModel.screenMovieId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showSeat(theaterSeats: List<SeatUiModel>) {
        selectedSeats = SelectedSeatsUiModel()
        binding.selectedSeats = selectedSeats
        theaterSeats.forEach { seatUiModel ->
            val seatView: CheckBox = tableChildView(seatUiModel.row, seatUiModel.col)
            seatView.setTextColor(getColor(seatUiModel.rateColor.color))
            seatView.text = seatUiModel.showPosition
            seatView.setOnClickListener {
                presenter.changeSeatState(seatUiModel)
                binding.btnSelectSeatReserve.isEnabled = selectedSeats.seats.isNotEmpty()
                binding.selectedSeats = selectedSeats
            }
        }
    }

    override fun showMovieInfo(
        title: String,
        priceUiModel: PriceUiModel,
    ) {
        binding.tvSelectSeatTitle.text = title
        binding.tvSelectSeatPrice.text = priceUiModel.price
    }

    override fun updatePrice(updatedPrice: PriceUiModel) {
        binding.tvSelectSeatPrice.text = updatedPrice.price
    }

    override fun updateSeatState(selectedSeats: List<SeatUiModel>) {
        this.selectedSeats = SelectedSeatsUiModel(selectedSeats)
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
        when (val selectResult = selectResult()) {
            is SelectResult.Exceed -> showErrorToastMessage(selectResult.message)
            is SelectResult.LessSelect -> showErrorToastMessage(selectResult.message)
            is SelectResult.Success -> confirmAlertDialog()
        }
    }

    private fun showErrorToastMessage(messageRes: String) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
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

    private fun selectResult(): SelectResult =
        when {
            bookingInfoUiModel.maxSelectSize() < selectedSeats.seats.size ->
                SelectResult.Exceed(
                    getString(R.string.select_more_seat_error_message),
                )

            bookingInfoUiModel.maxSelectSize() > selectedSeats.seats.size ->
                SelectResult.LessSelect(
                    getString(R.string.select_less_seat_error_message),
                )

            else -> SelectResult.Success
        }

    companion object {
        private const val EXTRA_BOOKING_ID: String = "bookingId"
        private const val EXTRA_SEATS_ID: String = "seatsId"

        fun getIntent(
            context: Context,
            bookingInfoUiModel: BookingInfoUiModel,
        ): Intent {
            return Intent(context, SelectSeatActivity::class.java).apply {
                putExtra(EXTRA_BOOKING_ID, bookingInfoUiModel)
            }
        }
    }
}
