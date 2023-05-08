package woowacourse.movie.view.seatselection

import android.annotation.SuppressLint
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
import woowacourse.movie.data.reservation.ReservationDbRepository
import woowacourse.movie.data.theater.TheaterMockRepository
import woowacourse.movie.databinding.ActivitySeatSelectionBinding
import woowacourse.movie.domain.theater.Grade
import woowacourse.movie.util.DECIMAL_FORMAT
import woowacourse.movie.util.getParcelableCompat
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
        binding.options = options
        presenter = SeatSelectionPresenter(
            this,
            options,
            ReservationDbRepository(this),
            TheaterMockRepository,
        )

        presenter.fetchSeatsData(
            mapOf(
                Grade.B to R.color.seat_rank_b,
                Grade.S to R.color.seat_rank_s,
                Grade.A to R.color.seat_rank_a,
            ),
        )
        setNextButton()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun createRow(seatInfo: SeatInfoUiModel) {
        val tableRow = TableRow(this).apply {
            layoutParams = TableLayout.LayoutParams(0, 0, 1f)
        }
        binding.layoutSeats.addView(tableRow)
    }

    @SuppressLint("ResourceType")
    override fun createSeat(seat: SeatUiModel) {
        val textView = TextView(this).apply {
            text = seat.seatId
            println("- ${seat.color}")
            setTextColor(resources.getColor(seat.color))
            setTypeface(null, Typeface.BOLD)
            textSize = 22F
            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            gravity = Gravity.CENTER
            setOnClickListener { presenter.updateSeat(seat.row, seat.col) }
            background =
                AppCompatResources.getDrawable(this@SeatSelectionActivity, R.drawable.seat_selector)
            layoutParams = TableRow.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f)
        }
        binding.layoutSeats.children.filterIsInstance<TableRow>().toList()[seat.row].addView(
            textView,
        )
    }

    private fun setNextButton() {
        binding.btnNext.setOnClickListener {
            showSubmitDialog()
        }
    }

    private fun showSubmitDialog() {
        AlertDialog.Builder(this).run {
            setTitle(context.getString(R.string.reserve_dialog_title))
            setMessage(context.getString(R.string.reserve_dialog_detail))
            setPositiveButton(context.getString(R.string.reserve_dialog_submit)) { _, _ ->
                presenter.reserve()
            }
            setNegativeButton(context.getString(R.string.reserve_dialog_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            setCancelable(false)
        }.show()
    }

    override fun onReserveClick(model: ReservationUiModel) {
        val intent = ReservationCompletedActivity.newIntent(this, model)
        startActivity(intent)
    }

    override fun onSeatSelectedByIndex(index: Int, isClickableButton: Boolean) {
        val textView = seats[index]
        textView.isSelected = true
        if (isClickableButton) binding.btnNext.isEnabled = true
    }

    override fun onSeatDeselectedByIndex(index: Int) {
        val textView = seats[index]
        binding.btnNext.isEnabled = false
        textView.isSelected = false
    }

    override fun showSeatMaxSelectionToast() {
        showToast(SELECT_ALL_SEAT_MESSAGE)
    }

    override fun showWrongInputToast() {
        showToast(SELECT_WRONG_SEAT_MESSAGE)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun setPrice(price: Int) {
        binding.textPrice.text =
            getString(R.string.reservation_fee_format, DECIMAL_FORMAT.format(price))
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
