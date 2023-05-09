package woowacourse.movie.presentation.choiceSeat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.movie.R
import woowacourse.movie.data.bookedticket.TicketDBHelper
import woowacourse.movie.data.bookedticket.TicketsDBAdapter
import woowacourse.movie.data.movie.MockMovieData
import woowacourse.movie.data.settings.SettingsPreference
import woowacourse.movie.databinding.ActivityChoiceSeatBinding
import woowacourse.movie.presentation.allowance.PreferenceKey
import woowacourse.movie.presentation.complete.CompleteActivity
import woowacourse.movie.presentation.model.ReservationModel
import woowacourse.movie.presentation.model.SeatModel
import woowacourse.movie.presentation.model.TicketModel
import woowacourse.movie.presentation.util.getParcelableExtraCompat
import woowacourse.movie.presentation.util.noIntentExceptionHandler

class ChoiceSeatActivity : AppCompatActivity(), ChoiceSeatContract.View {

    override val presenter: ChoiceSeatContract.Presenter by lazy {
        val settingsPrefKey = PreferenceKey.NOTIFICATION_PREF_KEY
        ChoiceSeatPresenter(
            this,
            MockMovieData,
            SettingsPreference.getInstance(settingsPrefKey, this),
            TicketsDBAdapter(TicketDBHelper(this)),
        )
    }
    private lateinit var binding: ActivityChoiceSeatBinding
    private lateinit var reservation: ReservationModel
    private val confirmButton by lazy { binding.buttonChoiceConfirm }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChoiceSeatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheaterSeat()
        initReservation()
        initView()
    }

    private fun initReservation() {
        reservation = intent.getParcelableExtraCompat(RESERVATION)
            ?: return this.noIntentExceptionHandler(NO_RESERVATION_INFO_ERROR)
    }

    private fun setTheaterSeat() {
        val seatsTable = binding.tableSeats
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<TextView>()
            .forEachIndexed { index, view ->
                setSeat(index, view)
            }
    }

    private fun setSeat(index: Int, view: TextView) {
        val seatModel = presenter.getSeatModel(index)

        setSeatText(view, seatModel)
        setSeatTextColor(view, seatModel.seatGrade)
        view.setOnClickListener {
            clickSeat(index, view)
        }
    }

    private fun clickSeat(index: Int, view: TextView) {
        if (view.isSelected) {
            unSelectSeat(index, view)
            return
        }
        selectSeat(index, view)
    }

    private fun unSelectSeat(index: Int, view: TextView) {
        val result = presenter.subSeat(index, reservation)
        if (!result) return
        view.isSelected = false
    }

    private fun selectSeat(index: Int, view: TextView) {
        val result = presenter.addSeat(index, reservation)
        if (!result) return
        view.isSelected = true
    }

    override fun enableConfirm() {
        confirmButton.isEnabled = true
    }

    override fun disableConfirm() {
        confirmButton.isEnabled = false
    }

    private fun setSeatText(view: TextView, seatModel: SeatModel) {
        view.text =
            getString(
                R.string.seat_format,
                seatModel.row,
                seatModel.columns.toString(),
            )
    }

    private fun setSeatTextColor(view: TextView, seatGrade: String) {
        when (seatGrade) {
            GRADE_B -> view.setTextColor(getColor(R.color.purple_400))
            GRADE_S -> view.setTextColor(getColor(R.color.green_300))
            GRADE_A -> view.setTextColor(getColor(R.color.blue_700))
        }
    }

    private fun initView() {
        presenter.setMovieTitle(reservation.movieId)
        setPaymentAmount(INITIAL_PAYMENT_MONEY)
        setConfirmButton()
    }

    override fun setMovieTitle(title: String) {
        binding.textChoiceTitle.text = title
    }

    override fun setPaymentAmount(amount: Int) {
        binding.textChoicePaymentAmount.text =
            getString(R.string.payment_amount, amount)
    }

    private fun setConfirmButton() {
        confirmButton.setOnClickListener {
            showConfirmCheckDialog()
        }
        confirmButton.isEnabled = false
    }

    private fun showConfirmCheckDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_choice_confirm_title))
            .setMessage(getString(R.string.dialog_choice_confirm_message))
            .setPositiveButton(getString(R.string.dialog_choice_positive_button)) { _, _ ->
                presenter.reserveTicketModel(reservation)
            }
            .setNegativeButton(getString(R.string.dialog_choice_negative_button)) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    override fun confirmBookMovie(ticketModel: TicketModel) {
        if (presenter.isNotifiable) {
            MovieNoticeAlarmManager(this, ticketModel).setAlarm(ticketModel.bookedDateTime)
        }
        startActivity(CompleteActivity.getIntent(this, ticketModel))
        finishAffinity()
    }

    companion object {
        private const val RESERVATION = "RESERVATION"
        private const val GRADE_B = "GRADE_B"
        private const val GRADE_S = "GRADE_S"
        private const val GRADE_A = "GRADE_A"
        private const val INITIAL_PAYMENT_MONEY = 0
        private const val NO_RESERVATION_INFO_ERROR = "예약 정보가 없습니다."

        fun getIntent(context: Context, reservation: ReservationModel): Intent {
            return Intent(context, ChoiceSeatActivity::class.java).apply {
                putExtra(RESERVATION, reservation)
            }
        }
    }
}
