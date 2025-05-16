package woowacourse.movie.presentation.view.reservation.seat

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.MovieApplication
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivitySeatSelectBinding
import woowacourse.movie.presentation.Extras
import woowacourse.movie.presentation.alarm.AlarmScheduler
import woowacourse.movie.presentation.getParcelableExtraCompat
import woowacourse.movie.presentation.model.ReservationInfoUiModel
import woowacourse.movie.presentation.view.reservation.complete.ReservationCompleteActivity
import woowacourse.movie.presentation.view.reservation.detail.ReservationDetailDialog

class SeatSelectActivity :
    AppCompatActivity(),
    SeatSelectContract.View {
    private lateinit var binding: ActivitySeatSelectBinding
    private val alarmScheduler = AlarmScheduler(this)
    private val presenter = MovieApplication.provideSeatSelectPresenter(this)
    private val reservationDialog by lazy { ReservationDetailDialog() }
    private val seatViews: MutableMap<String, TextView> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_seat_select)
        ViewCompat.setOnApplyWindowInsetsListener(binding.svSeatSelect) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupSeatView(binding.tlSeat)

        val reservationInfo =
            intent?.getParcelableExtraCompat<ReservationInfoUiModel>(Extras.ReservationInfoData.RESERVATION_KEY)
        presenter.fetchData(reservationInfo)

        setupConfirmButton()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        setupSavedData(savedInstanceState)
        presenter.restoreButtonState()
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        val fromAlarmSettings =
            intent?.getBooleanExtra(Extras.ReservationInfoData.ALARM_SETTING_KEY, false) ?: false
        val reservationInfo =
            intent?.getParcelableExtraCompat<ReservationInfoUiModel>(Extras.ReservationInfoData.RESERVATION_KEY)

        if (fromAlarmSettings && reservationInfo != null) {
            presenter.saveReservation(reservationInfo)
        }
    }

    override fun showErrorDialog() {
    }

    override fun showReservationInfo(
        title: String,
        price: Int,
    ) {
        binding.movieTitle = title
        binding.totalPrice = price
    }

    override fun showSeatCountError(count: Int) {
        showToast(getString(R.string.seat_select_error_count, count))
    }

    override fun showSelectedSeat(seatId: String) {
        seatViews[seatId]?.setBackgroundResource(R.color.yellow)
    }

    override fun showDeselectedSeat(seatId: String) {
        seatViews[seatId]?.setBackgroundResource(R.color.white)
    }

    override fun showTotalPrice(totalPrice: Int) {
        binding.totalPrice = totalPrice
    }

    override fun updateConfirmButtonEnabled(isEnabled: Boolean) {
        binding.isConfirmButtonEnabled = isEnabled
    }

    override fun showReservationDialog(
        title: String,
        message: String,
    ) {
        reservationDialog.show(
            this,
            title,
            message,
            { dialog -> dialog.dismiss() },
            { _ -> presenter.reservationConfirmed() },
        )
    }

    override fun showExactAlarmSettingDialog(reservationInfo: ReservationInfoUiModel) {
        AlertDialog
            .Builder(this)
            .setTitle(getString(R.string.setting_request_permission_dialog_title))
            .setMessage(getString(R.string.setting_request_reminder_permission_dialog_message))
            .setPositiveButton(R.string.setting_request_permission_dialog_positive) { _, _ ->
                intent.putExtra(Extras.ReservationInfoData.RESERVATION_KEY, reservationInfo)
                intent.putExtra(Extras.ReservationInfoData.ALARM_SETTING_KEY, true)
                alarmScheduler.requestExactAlarmPermission()
            }.setNegativeButton(R.string.setting_request_permission_dialog_negative) { _, _ ->
                showToast(getString(R.string.reservation_dialog_no_alarm_complete))
                presenter.saveReservation(reservationInfo)
            }.show()
    }

    override fun navigateCompleteWithAlarmCheck(reservationInfoUiModel: ReservationInfoUiModel) {
        if (!alarmScheduler.canScheduleAlarm()) {
            showExactAlarmSettingDialog(reservationInfoUiModel)
            return
        }

        presenter.saveReservation(reservationInfoUiModel)
    }

    override fun navigateToComplete(reservationInfo: ReservationInfoUiModel) {
        alarmScheduler.scheduleAlarm(reservationInfo)

        val intent =
            Intent(this, ReservationCompleteActivity::class.java).apply {
                putExtra(Extras.ReservationInfoData.RESERVATION_KEY, reservationInfo)
            }
        startActivity(intent)
        finish()
    }

    private fun setupSeatView(tableLayout: TableLayout) {
        for (i in 0 until tableLayout.childCount) {
            val row = tableLayout.getChildAt(i)
            if (row is TableRow) {
                for (j in 0 until row.childCount) {
                    val seatView = row.getChildAt(j)
                    if (seatView is TextView) {
                        val seatId = seatView.text.toString()
                        seatViews[seatId] = seatView
                        seatView.setOnClickListener {
                            presenter.seatSelect(seatId)
                        }
                    }
                }
            }
        }
    }

    private fun setupConfirmButton() {
        binding.btnSeatSelectConfirm.apply {
            isClickable = false
            alpha = 0.1f
            setOnClickListener {
                presenter.confirmRequested(
                    getString(R.string.reservation_dialog_title),
                    getString(R.string.reservation_dialog_message),
                )
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupSavedData(savedInstanceState: Bundle?) {
        val savedSeats =
            savedInstanceState?.getStringArrayList(Extras.SeatsData.SEATS_KEY) ?: emptyList()
        presenter.restoreSelectedSeats(savedSeats)
        savedSeats.forEach { seatViews[it]?.setBackgroundResource(R.color.yellow) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        presenter.saveSelectedSeats(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
