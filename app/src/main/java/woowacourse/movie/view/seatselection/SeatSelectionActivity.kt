package woowacourse.movie.view.seatselection

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toolbar.LayoutParams
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.children
import woowacourse.movie.AlarmPreference
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivitySeatSelectionBinding
import woowacourse.movie.util.getParcelableCompat
import woowacourse.movie.view.alarm.ReservationAlarmManager
import woowacourse.movie.view.model.MovieListModel.MovieUiModel
import woowacourse.movie.view.model.ReservationOptions
import woowacourse.movie.view.model.ReservationUiModel
import woowacourse.movie.view.model.SeatUiModel
import woowacourse.movie.view.moviemain.setting.SettingFragment
import woowacourse.movie.view.reservationcompleted.ReservationCompletedActivity
import java.text.DecimalFormat

class SeatSelectionActivity : AppCompatActivity(), SeatSelectionContract.View {

    private lateinit var binding: ActivitySeatSelectionBinding
    override lateinit var presenter: SeatSelectionContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeatSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = SeatSelectionPresenter(this)

        presenter.setUp()
        initConfirmReservationButton()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun getReservationOptions(): ReservationOptions? =
        intent.getParcelableCompat(RESERVATION_OPTIONS)

    override fun initReserveLayout(reservationOptions: ReservationOptions) {
        binding.apply {
            movieTitleTextview.text = reservationOptions.title
            reservationFeeTextview.text = getString(R.string.reservation_fee_format).format(
                DECIMAL_FORMAT.format(0),
            )
            confirmReservationButton.isEnabled = false
        }
    }

    override fun initSeatButtons(rowRange: IntRange, colRange: IntRange) {
        for (row in rowRange) {
            val tableRow = TableRow(this).apply {
                layoutParams = TableLayout.LayoutParams(0, 0, 1f)
            }
            for (col in colRange) {
                val seat = presenter.createSeat(row, col)
                tableRow.addView(createSeatButton(this, seat))
            }
            binding.seatTablelayout.addView(tableRow)
        }
    }

    private fun createSeatButton(context: Context, seatUi: SeatUiModel): AppCompatButton =
        AppCompatButton(context).apply {
            text = seatUi.name
            setTextColor(getColor(seatUi.color))
            setOnClickListener { onSeatClick(this) }
            background =
                AppCompatResources.getDrawable(this@SeatSelectionActivity, R.drawable.selector_seat)
            layoutParams = TableRow.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f)
        }

    private fun onSeatClick(seat: Button) {
        if (seat.isSelected) {
            seat.isSelected = false
            presenter.deselectSeat()
            return
        }
        seat.isSelected = true
        if (!presenter.selectSeat()) seat.isSelected = false
    }

    override fun findSelectedSeatsIndex(): List<Int> {
        val seats = binding.seatTablelayout.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<Button>()
            .toList()

        val selectedIndex = mutableListOf<Int>()
        seats.forEachIndexed { index, button ->
            if (!button.isSelected) return@forEachIndexed
            selectedIndex.add(index)
        }
        return selectedIndex
    }

    override fun enableReservation(reservationFee: Int) {
        binding.confirmReservationButton.isEnabled = true
        binding.reservationFeeTextview.text = getString(R.string.reservation_fee_format).format(
            DECIMAL_FORMAT.format(reservationFee),
        )
    }

    override fun disableReservation() {
        binding.confirmReservationButton.isEnabled = false
        binding.reservationFeeTextview.text = getString(R.string.reservation_fee_format).format(
            DECIMAL_FORMAT.format(0),
        )
    }

    override fun getMovie(): MovieUiModel? = intent.getParcelableCompat(MOVIE)

    private fun initConfirmReservationButton() {
        binding.confirmReservationButton.setOnClickListener {
            val alertDialog: AlertDialog = AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.reservation_dialog_title))
                setMessage(getString(R.string.reservation_dialog_message))
                setPositiveButton(getString(R.string.confirm_reservation)) { _, _ ->
                    presenter.reserveSeats()
                }
                setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                setCancelable(false)
            }.create()
            alertDialog.show()
        }
    }

    override fun registerReservationAlarm(reservation: ReservationUiModel) {
        val isAlarmOn = AlarmPreference.getInstance(applicationContext).isAlarmOn(false)
        val reservationAlarmManager = ReservationAlarmManager(this)

        if (isAlarmOn) reservationAlarmManager.registerAlarm(
            reservation,
            SettingFragment.ALARM_MINUTE_INTERVAL
        )
        startActivity(ReservationCompletedActivity.newIntent(this, reservation))
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
        private val DECIMAL_FORMAT = DecimalFormat("#,###")

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
