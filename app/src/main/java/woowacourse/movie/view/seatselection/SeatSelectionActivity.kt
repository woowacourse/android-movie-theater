package woowacourse.movie.view.seatselection

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar.LayoutParams
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.children
import woowacourse.movie.R
import woowacourse.movie.data.TheaterMockRepository
import woowacourse.movie.databinding.ActivitySeatSelectionBinding
import woowacourse.movie.domain.system.Seat
import woowacourse.movie.util.getParcelableCompat
import woowacourse.movie.view.mapper.toUiModel
import woowacourse.movie.view.model.MovieUiModel
import woowacourse.movie.view.model.ReservationOptions
import woowacourse.movie.view.model.ReservationUiModel
import woowacourse.movie.view.model.SeatInfoUiModel
import woowacourse.movie.view.model.SeatUiModel
import woowacourse.movie.view.reservationcompleted.ReservationCompletedActivity

class SeatSelectionActivity : AppCompatActivity(), SeatSelectionContract.View {

    private lateinit var binding: ActivitySeatSelectionBinding

    override lateinit var presenter: SeatSelectionContract.Presenter

    private val seats: List<TextView> by lazy {
        binding.layoutSeats.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<TextView>().toList()
    }

    private lateinit var reserveOptions: ReservationOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeatSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val options = intent.getParcelableCompat<ReservationOptions>(RESERVATION_OPTIONS)
        if (options == null) {
            Toast.makeText(this, DATA_LOADING_ERROR_MESSAGE, Toast.LENGTH_LONG).show()
            finish()
            return
        }
        reserveOptions = options

        presenter = SeatSelectionPresenter(this, options, TheaterMockRepository)

        createRows(presenter.getSeatInfoUiModel(TheaterMockRepository.gradeColor))
        setTitle(reserveOptions.title)
        setNextButton()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun createRows(seatInfoUiModel: SeatInfoUiModel) {
        for (row in 0 until seatInfoUiModel.row) {
            val tableRow = TableRow(this).apply {
                layoutParams = TableLayout.LayoutParams(0, 0, 1f)
            }
            for (col in 0 until seatInfoUiModel.col) {
                val seat = Seat(row, col)
                tableRow.addView(createSeat(seat.toUiModel(), seatInfoUiModel.colorOfRow))
            }
            binding.layoutSeats.addView(tableRow)
        }
    }

    private fun createSeat(seatUi: SeatUiModel, colorOfRow: Map<Int, Int>): TextView =
        TextView(this).apply {
            text = seatUi.seatId
            colorOfRow[seatUi.row]?.let {
                setTextColor(getColor(it))
            }
            setTypeface(null, Typeface.BOLD)
            textSize = 22F
            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            gravity = Gravity.CENTER
            setOnClickListener { presenter.onSeatClick(seatUi.row, seatUi.col) }
            background =
                AppCompatResources.getDrawable(this@SeatSelectionActivity, R.drawable.seat_selector)
            layoutParams = TableRow.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f)
        }

    private fun setTitle(title: String) {
        binding.textTitle.text = title
    }

    private fun setNextButton() {
        binding.btnNext.setOnClickListener {
            presenter.onReserveClick()
        }
    }

    override fun showSubmitDialog(reservation: ReservationUiModel) {
        AlertDialog.Builder(this).run {
            setTitle(context.getString(R.string.reserve_dialog_title))
            setMessage(context.getString(R.string.reserve_dialog_detail))
            setPositiveButton(context.getString(R.string.reserve_dialog_submit)) { _, _ ->
                onReserveClick(reservation)
            }
            setNegativeButton(context.getString(R.string.reserve_dialog_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            setCancelable(false)
        }.show()
    }

    private fun onReserveClick(model: ReservationUiModel) {
        val intent = ReservationCompletedActivity.newIntent(this, model)
        startActivity(intent)
    }

    override fun setSelectionSeat(index: Int, isClickableButton: Boolean) {
        val textView = seats[index]
        textView.isSelected = true
        if (isClickableButton) binding.btnNext.isEnabled = true
    }

    override fun setDeselectionSeat(index: Int) {
        val textView = seats[index]
        binding.btnNext.isEnabled = false
        textView.isSelected = false
    }

    override fun maxSelectionToast() {
        showToast(SELECT_ALL_SEAT_MESSAGE)
    }

    override fun wrongInputToast() {
        showToast(SELECT_WRONG_SEAT_MESSAGE)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun setPrice(price: String) {
        binding.textPrice.text = getString(R.string.reservation_fee_format, price)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val RESERVATION_OPTIONS = "RESERVATION_OPTIONS"
        private const val MOVIE = "MOVIE"
        private const val DATA_LOADING_ERROR_MESSAGE = "데이터가 로딩되지 않았습니다. 다시 시도해주세요."
        private const val SELECT_ALL_SEAT_MESSAGE = "좌석을 이미 다 선택하셨습니다."
        private const val SELECT_WRONG_SEAT_MESSAGE = "잘못된 접근입니다."

        fun newIntent(
            context: Context,
            reservationOptions: ReservationOptions,
            movie: MovieUiModel,
        ): Intent {
            val intent = Intent(context, SeatSelectionActivity::class.java)
            intent.putExtra(RESERVATION_OPTIONS, reservationOptions)
            intent.putExtra(MOVIE, movie)
            return intent
        }
    }
}
