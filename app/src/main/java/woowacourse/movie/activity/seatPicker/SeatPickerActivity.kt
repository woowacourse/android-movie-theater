package woowacourse.movie.activity.seatPicker

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import com.woowacourse.domain.movie.MovieBookingSeatInfo
import com.woowacourse.domain.seat.Seat
import woowacourse.movie.R
import woowacourse.movie.activity.BackButtonActivity
import woowacourse.movie.activity.bookComplete.BookCompleteActivity
import woowacourse.movie.data.BookingHistoryDBHelper
import woowacourse.movie.databinding.ActivitySeatPickerBinding
import woowacourse.movie.getSerializableCompat
import woowacourse.movie.mapper.toDomain
import woowacourse.movie.model.MovieBookingInfoUiModel
import woowacourse.movie.model.SeatGroupModel
import woowacourse.movie.model.toDomain
import woowacourse.movie.model.toHistoryData
import woowacourse.movie.model.toPresentation
import woowacourse.movie.service.AlarmSetting

class SeatPickerActivity : BackButtonActivity(), SeatPickerContract.View {
    override lateinit var presenter: SeatPickerContract.Presenter
    private lateinit var binding: ActivitySeatPickerBinding
    private val seatTableLayout: TableLayout by lazy { binding.tlSeats }
    private val pickDoneButton: Button by lazy { binding.btSeatPickerDone }
    private val seatPickerTicketPrice: TextView by lazy { binding.tvSeatPickerTicketPrice }
    private val movieTitle: TextView by lazy { binding.tvSeatPickerMovie }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = SeatPickerPresenter(this, getMovieBookingInfo().toDomain())
        binding = DataBindingUtil.setContentView(this, R.layout.activity_seat_picker)

        presenter.initMovieTitle()
        initSeatName()
        initSeatNameColor()
        reloadData(savedInstanceState)

        setPickDoneButtonClickListener()
        setSeatsOnClickListener()
    }

    private fun getMovieBookingInfo(): MovieBookingInfoUiModel {
        return intent.getSerializableCompat(MOVIE_BOOKING_INFO_KEY)
            ?: MovieBookingInfoUiModel.dummyData
    }

    private fun initSeatName() {
        getRowSeats().forEachIndexed { index, rowSeat ->
            rowSeat.children
                .filterIsInstance<TextView>()
                .forEachIndexed { rowIndex, seat ->
                    seat.text = formatSeatName(index, rowIndex)
                }
        }
    }

    private fun initSeatNameColor() {
        getSeats().forEachIndexed { index, seat ->
            when (index) {
                in SEAT_B -> seat.setTextColor(getColor(R.color.seat_color_b))
                in SEAT_S -> seat.setTextColor(getColor(R.color.seat_color_s))
                in SEAT_A -> seat.setTextColor(getColor(R.color.seat_color_a))
                else -> throw IllegalArgumentException("잘못된 값: $index 분류될 수 없는 값입니다.")
            }
        }
    }

    private fun getRowSeats() =
        seatTableLayout.children
            .filterIsInstance<TableRow>()
            .toList()

    private fun getSeats() =
        seatTableLayout.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<TextView>()
            .toList()

    private fun reloadData(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            movieTitle.text = savedInstanceState.getString(MOVIE_TITLE)
            seatPickerTicketPrice.text = savedInstanceState.getString(TICKET_PRICE)
            presenter.setSeatGroup((savedInstanceState.getSerializable(PICKED_SEAT) as SeatGroupModel).toDomain())
        }
    }

    private fun setPickDoneButtonClickListener() {
        pickDoneButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.alert_dialog_book_confirm))
                .setMessage(getString(R.string.alert_dialog_book_re_confirm))
                .setPositiveButton(
                    getString(R.string.alert_dialog_book_done)
                ) { _, _ ->
                    presenter.getMovieBookingSeatInfo(seatPickerTicketPrice.text.toString())
                }.setNegativeButton(
                    getString(R.string.alert_dialog_book_cancel)
                ) { dialog, _ ->
                    dialog.dismiss()
                }
                .setCancelable(false)
                .show()
        }
    }

    private fun setSeatsOnClickListener() {
        getSeats().forEachIndexed { index, seat ->
            seat.setOnClickListener {
                presenter.onClickSeat(index, seat)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.save(outState)
    }

    override fun initMovieTitle(title: String) {
        movieTitle.text = title
    }

    override fun onClickDoneBtn(movieBookingSeatInfo: MovieBookingSeatInfo) {
        val movieBookingSeatInfoUIModel = movieBookingSeatInfo.toPresentation()
        val db = BookingHistoryDBHelper(this)
        val intent =
            BookCompleteActivity.getIntent(this, movieBookingSeatInfoUIModel)

        AlarmSetting().setAlarm(this, movieBookingSeatInfo)
        db.insertData(movieBookingSeatInfoUIModel.toHistoryData())

        startActivity(intent)
        finish()
    }

    override fun setSeatGroup(seatNames: List<String>) {
        setPickDoneButtonColor()
        getSeats().forEach {
            if (seatNames.contains(it.text)) it.setBackgroundColor(getColor(R.color.picked_seat_color))
        }
    }

    private fun setPickDoneButtonColor() {
        pickDoneButton.isEnabled = presenter.isEnoughTicketNum
    }

    override fun progressRemoveSeat(
        newSeat: Seat,
        seat: TextView,
    ) {
        seat.setBackgroundColor(getColor(R.color.unpicked_seat_color))
        setPickDoneButtonColor()
    }

    override fun progressAddSeat(
        newSeat: Seat,
        seat: TextView,
    ) {
        seat.setBackgroundColor(getColor(R.color.picked_seat_color))
        setPickDoneButtonColor()
    }

    override fun setPriceText(price: Int) {
        seatPickerTicketPrice.text =
            getString(
                R.string.ticket_price_format,
                price
            )
    }

    companion object {
        private val ALPHABET = ('A'..'Z').toList()
        private val SEAT_B = 0..7
        private val SEAT_S = 8..15
        private val SEAT_A = 16..19
        const val MOVIE_TITLE = "movieTitle"
        const val TICKET_PRICE = "ticketPrice"
        const val PICKED_SEAT = "pickedSeat"
        private const val MOVIE_BOOKING_INFO_KEY = "movieBookingInfo"
        private const val SEAT_NAME = "%c%d"

        fun formatSeatName(index: Int, rowIndex: Int) =
            SEAT_NAME.format(ALPHABET[index], rowIndex + 1)

        fun getIntent(context: Context, movieBookingInfo: MovieBookingInfoUiModel): Intent {
            val intent = Intent(context, SeatPickerActivity::class.java)
            intent.putExtra(MOVIE_BOOKING_INFO_KEY, movieBookingInfo)
            return intent
        }
    }
}
