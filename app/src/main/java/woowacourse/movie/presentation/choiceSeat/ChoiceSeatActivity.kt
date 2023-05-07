package woowacourse.movie.presentation.choiceSeat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.movie.R
import woowacourse.movie.domain.model.tools.seat.Location
import woowacourse.movie.domain.model.tools.seat.SeatGrade
import woowacourse.movie.model.data.local.SettingPreference
import woowacourse.movie.model.data.remote.DummyMovieStorage
import woowacourse.movie.presentation.complete.CompleteActivity
import woowacourse.movie.presentation.mappers.toPresentation
import woowacourse.movie.presentation.model.ReservationModel
import woowacourse.movie.presentation.util.getParcelableExtraCompat
import woowacourse.movie.util.intentDataNullProcess

class ChoiceSeatActivity : AppCompatActivity(), ChoiceSeatContract.View {

    override lateinit var presenter: ChoiceSeatContract.Presenter

    private lateinit var reservation: ReservationModel
    private val confirmButton by lazy {
        findViewById<Button>(R.id.buttonChoiceConfirm)
    }
    private val textChoicePaymentAmount by lazy {
        findViewById<TextView>(R.id.textChoicePaymentAmount)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice_seat)
        initExtraData()
        initPresenter()
        setTheaterSeat()
        initView()
    }

    private fun initPresenter() {
        presenter = ChoiceSeatPresenter(
            view = this,
            alarmManager = MovieNoticeAlarmManager(this),
            settingStorage = SettingPreference(this),
            movieStorage = DummyMovieStorage(),
            reservation = reservation
        )
    }

    private fun initExtraData() {
        reservation = intent.getParcelableExtraCompat<ReservationModel>(RESERVATION)
            ?: return this.intentDataNullProcess(
                RESERVATION
            )
    }

    private fun initView() {
        val movie = presenter.getMovieById(reservation.movieId)
        setTitle(movie.title)
        setConfirmButtonClickListener()
    }

    private fun setTitle(title: String) {
        findViewById<TextView>(R.id.textChoiceTitle).text = title
    }

    private fun setConfirmButtonClickListener() {
        confirmButton.setOnClickListener {
            showConfirmCheckDialog()
        }
    }

    private fun showConfirmCheckDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_choice_confirm_title))
            .setMessage(getString(R.string.dialog_choice_confirm_message))
            .setPositiveButton(getString(R.string.dialog_choice_positive_button)) { _, _ ->
                confirmBookMovie()
            }
            .setNegativeButton(getString(R.string.dialog_choice_negative_button)) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun confirmBookMovie() {
        startActivity(
            CompleteActivity.getIntent(
                this,
                presenter.issueTicket().toPresentation(this)
            )
        )
        finishAffinity()
    }

    override fun updateTextChoicePaymentAmount(paymentAmount: Int) {
        textChoicePaymentAmount.text = getString(R.string.payment_amount, paymentAmount)
    }

    private fun setTheaterSeat() {
        findViewById<TableLayout>(R.id.tableSeats)
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<TextView>()
            .forEachIndexed { index, view ->
                setSeat(index, view)
            }
    }

    private fun setSeat(index: Int, view: TextView) {
        val column = index % ChoiceSeatPresenter.THEATER_COLUMN
        val location = presenter.getLocation(index, column)

        setSeatText(view, location)
        setSeatTextColor(view, presenter.getSeatGrade(location))
        view.setOnClickListener {
            clickSeat(index, column, view)
        }
    }

    private fun clickSeat(row: Int, column: Int, view: TextView) {
        when {
            view.isSelected -> unSelectSeat(row, column, view)
            presenter.checkReservationCountFull() -> return
            else -> selectSeat(row, column, view)
        }
    }

    private fun unSelectSeat(row: Int, column: Int, view: TextView) {
        presenter.removeSeat(row = row, column = column)
        view.isSelected = false
    }

    private fun selectSeat(row: Int, column: Int, view: TextView) {
        presenter.addSeat(row = row, column = column)
        view.isSelected = true
    }

    override fun updateConfirmButtonState(reservationCountFull: Boolean) {
        if (reservationCountFull) {
            confirmButton.isEnabled = true
            return
        }
        confirmButton.isEnabled = false
    }

    private fun setSeatText(view: TextView, location: Location) {
        view.text =
            getString(
                R.string.seat_format,
                location.row,
                (location.number + COLUMN_ADDITION).toString()
            )
    }

    private fun setSeatTextColor(view: TextView, seatGrade: SeatGrade) {
        when (seatGrade) {
            SeatGrade.GRADE_B -> view.setTextColor(getColor(R.color.purple_400))
            SeatGrade.GRADE_S -> view.setTextColor(getColor(R.color.green_300))
            SeatGrade.GRADE_A -> view.setTextColor(getColor(R.color.blue_700))
        }
    }

    companion object {
        private const val COLUMN_ADDITION = 1

        private const val RESERVATION = "RESERVATION"

        fun getIntent(context: Context, reservation: ReservationModel): Intent {
            return Intent(context, ChoiceSeatActivity::class.java).apply {
                putExtra(RESERVATION, reservation)
            }
        }
    }
}
