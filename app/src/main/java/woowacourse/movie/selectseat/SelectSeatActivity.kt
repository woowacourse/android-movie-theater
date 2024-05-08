package woowacourse.movie.selectseat

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.data.DummyMovieRepository
import woowacourse.movie.databinding.ActivitySelectSeatBinding
import woowacourse.movie.moviedetail.uimodel.BookingInfoUiModel
import woowacourse.movie.moviedetail.uimodel.toHeadCount
import woowacourse.movie.purchaseconfirmation.PurchaseConfirmationActivity
import woowacourse.movie.selectseat.parcelable.SeatsInstanceState
import woowacourse.movie.selectseat.uimodel.Position
import woowacourse.movie.selectseat.uimodel.PriceUiModel
import woowacourse.movie.selectseat.uimodel.SeatUiModel
import woowacourse.movie.selectseat.uimodel.toParcelable
import woowacourse.movie.selectseat.uimodel.toUiModel
import woowacourse.movie.util.intentParcelable
import woowacourse.movie.util.showAlertDialog

class SelectSeatActivity : AppCompatActivity(), SelectSeatContract.View {
    private lateinit var presenter: SelectSeatContract.Presenter
    private lateinit var binding: ActivitySelectSeatBinding

    private var seatsInstanceState: SeatsInstanceState? = null
    private lateinit var bookingInfoUiModel: BookingInfoUiModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectSeatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        bookingInfoUiModel =
            intent.intentParcelable(EXTRA_BOOKING_ID, BookingInfoUiModel::class.java)
                ?: error("bookingInfo에 대한 정보가 없습니다.")
        presenter = SelectSeatPresenter(this, DummyMovieRepository)

        initView(bookingInfoUiModel)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.saveSeats()
        outState.putParcelable(EXTRA_SEATS_ID, seatsInstanceState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        seatsInstanceState =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                savedInstanceState.getParcelable(EXTRA_SEATS_ID, SeatsInstanceState::class.java)
            } else {
                savedInstanceState.getParcelable(EXTRA_SEATS_ID) as SeatsInstanceState?
            }
        presenter.loadSeats(seatsInstanceState!!.seatMap.toUiModel())
    }

    private fun initView(bookingInfoUiModel: BookingInfoUiModel) {
        presenter.initSeats(bookingInfoUiModel.screeningId)
        presenter.loadReservationInfo(bookingInfoUiModel.screeningId)
        presenter.initMaxCount(bookingInfoUiModel.count.toHeadCount())
        binding.btnSelectSeatReserve.setOnClickListener {
            confirmAlertDialog()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initSeats(seatUiModels: Map<Position, SeatUiModel>) {
        binding.seats = seatUiModels
        seatUiModels.forEach { (position, _) ->
            val seatView: TextView = tableChildView(position.row, position.col)
            seatView.setOnClickListener {
                presenter.selectSeat(position)
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

    override fun activatePurchase() {
        binding.btnSelectSeatReserve.isEnabled = true
    }

    override fun deActivatePurchase() {
        binding.btnSelectSeatReserve.isEnabled = false
    }

    override fun showSeats(seatUiModels: Map<Position, SeatUiModel>) {
        binding.seats = seatUiModels
    }

    override fun showPrice(updatedPrice: PriceUiModel) {
        binding.tvSelectSeatPrice.text = updatedPrice.price
    }

    override fun navigateToResult(reservationId: Long) {
        startActivity(PurchaseConfirmationActivity.getIntent(this, reservationId))
    }

    override fun onSaveSeats(seatUiModels: Map<Position, SeatUiModel>) {
        seatsInstanceState = SeatsInstanceState(seatUiModels.toParcelable())
    }

    private fun tableChildView(
        row: Int,
        col: Int,
    ): TextView {
        val tableRow = binding.tlSelectSeat.getChildAt(row) as TableRow
        return tableRow.getChildAt(col) as TextView
    }

    private fun confirmAlertDialog() =
        showAlertDialog(
            this,
            "예매 확인",
            "정말 예매하시겠습니까?",
            "예매 완료",
            onPositiveButtonClicked = {
                presenter.completeReservation(bookingInfoUiModel)
            },
            "취소",
        )

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
