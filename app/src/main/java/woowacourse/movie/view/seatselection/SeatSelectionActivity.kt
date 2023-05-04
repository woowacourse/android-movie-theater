package woowacourse.movie.view.seatselection

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar.LayoutParams
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import woowacourse.movie.R
import woowacourse.movie.data.Theater
import woowacourse.movie.databinding.ActivitySeatSelectionBinding
import woowacourse.movie.domain.price.Price
import woowacourse.movie.domain.price.PriceCalculator
import woowacourse.movie.domain.reservation.Reservation
import woowacourse.movie.domain.system.PriceSystem
import woowacourse.movie.domain.system.Seat
import woowacourse.movie.domain.system.SeatSelectSystem
import woowacourse.movie.domain.system.SelectResult
import woowacourse.movie.util.getParcelableCompat
import woowacourse.movie.view.ReservationCompletedActivity
import woowacourse.movie.view.mapper.toUiModel
import woowacourse.movie.view.model.MovieListModel.MovieUiModel
import woowacourse.movie.view.model.ReservationOptions
import woowacourse.movie.view.model.ReservationUiModel
import woowacourse.movie.view.model.SeatUiModel
import java.time.LocalDateTime

class SeatSelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySeatSelectionBinding
    private lateinit var seatSystem: SeatSelectSystem
    private lateinit var priceSystem: PriceSystem
    private val theater = Theater
    private var price: Price = Price(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeatSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val reserveOptions = intent.getParcelableCompat<ReservationOptions>(RESERVATION_OPTIONS)
        if (reserveOptions == null) {
            Toast.makeText(this, DATA_LOADING_ERROR_MESSAGE, Toast.LENGTH_LONG).show()
            finish()
            return
        }

        seatSystem = SeatSelectSystem(Theater.info, reserveOptions.peopleCount)
        priceSystem =
            PriceSystem(PriceCalculator(Theater.policies), reserveOptions.screeningDateTime)

        createRows()
        setTitle(reserveOptions.title)
        setNextButton(reserveOptions.title, reserveOptions.screeningDateTime)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun createRows() {
        for (row in 0 until theater.row) {
            val tableRow = TableRow(this).apply {
                layoutParams = TableLayout.LayoutParams(0, 0, 1f)
            }
            for (col in 0 until theater.col) {
                val seat = Seat(row, col)
                tableRow.addView(createSeat(seat.toUiModel()))
            }
            binding.layoutSeats.addView(tableRow)
        }
    }

    private fun createSeat(seatUi: SeatUiModel): TextView =
        TextView(this).apply {
            text = seatUi.seatId
            val grade = Theater.info.getRowGrade(seatUi.row)
            if (grade != null) {
                Theater.gradeColor[grade]?.let {
                    setTextColor(getColor(it))
                }
            }
            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            gravity = Gravity.CENTER
            setOnClickListener { setSeatView(seatUi.row, seatUi.col, it) }
            background =
                AppCompatResources.getDrawable(this@SeatSelectionActivity, R.drawable.selector_seat)
            layoutParams = TableRow.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f)
        }

    private fun setTitle(title: String) {
        binding.textTitle.text = title
    }

    private fun setNextButton(title: String, dateTime: LocalDateTime) {
        binding.btnNext.setOnClickListener {
            with(getDialog(Reservation(title, dateTime, seatSystem.seats, price).toUiModel())) {
                setCanceledOnTouchOutside(false)
                show()
            }
        }
    }

    private fun getDialog(ticketModel: ReservationUiModel): AlertDialog {
        return AlertDialog.Builder(this).run {
            setTitle(context.getString(R.string.reserve_dialog_title))
            setMessage(context.getString(R.string.reserve_dialog_detail))
            setPositiveButton(context.getString(R.string.reserve_dialog_submit)) { _, _ ->
                onNextClick(ticketModel)
            }
            setNegativeButton(context.getString(R.string.reserve_dialog_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            create()
        }
    }

    private fun onNextClick(model: ReservationUiModel) {
        val intent = ReservationCompletedActivity.newIntent(this, model)
        startActivity(intent)
    }

    private fun setPrice(price: String) {
        binding.textPrice.text = getString(R.string.reservation_fee_format, price)
    }

    private fun setSeatView(row: Int, col: Int, textView: View) {
        val result = seatSystem.select(row, col)
        when (result) {
            is SelectResult.Success.Selection -> {
                textView.setBackgroundColor(textView.context.getColor(R.color.select_seat))
                textView.isSelected = true
                if (result.isSelectAll) {
                    binding.btnNext.isEnabled = true
                }
            }
            is SelectResult.Success.Deselection -> {
                textView.setBackgroundColor(textView.context.getColor(R.color.white))
                binding.btnNext.isEnabled = false
                textView.isSelected = false
                setPrice(result.seatPrice.toUiModel())
            }
            is SelectResult.MaxSelection -> {
                Toast.makeText(
                    this,
                    SELECT_ALL_SEAT_MESSAGE,
                    Toast.LENGTH_LONG,
                )
                    .show()
            }
            is SelectResult.WrongInput -> {
                Toast.makeText(
                    this,
                    SELECT_WRONG_SEAT_MESSAGE,
                    Toast.LENGTH_LONG,
                )
                    .show()
            }
        }
        val newPrice = priceSystem.getCurrentPrice(price, result)
        price = newPrice
        setPrice(newPrice.toUiModel())
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
