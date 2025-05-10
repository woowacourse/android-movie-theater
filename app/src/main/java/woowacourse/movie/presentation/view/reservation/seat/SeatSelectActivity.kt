package woowacourse.movie.presentation.view.reservation.seat

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.data.ReservationProviderImpl
import woowacourse.movie.data.db.ReservationDatabase
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
    private val presenter: SeatSelectContract.Presenter by lazy {
        val alarmScheduler = AlarmScheduler(this)
        val dao = ReservationDatabase.getInstance(this).reservationDao()
        val provider = ReservationProviderImpl(dao)
        SeatSelectPresenter(this, provider, alarmScheduler)
    }
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

    override fun showExactAlarmSettingDialog() {
        AlertDialog
            .Builder(this)
            .setTitle(getString(R.string.setting_request_permission_dialog_title))
            .setMessage(getString(R.string.setting_request_reminder_permission_dialog_message))
            .setPositiveButton(R.string.setting_request_permission_dialog_positive) { _, _ ->
                navigateToReminderSettings()
            }.setNegativeButton(R.string.setting_request_permission_dialog_negative, null)
            .show()
    }

    override fun navigateToReminderSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            startActivity(intent)
        }
    }

    override fun navigateToComplete(reservationInfoUiModel: ReservationInfoUiModel) {
        val intent =
            Intent(this, ReservationCompleteActivity::class.java).apply {
                putExtra(Extras.ReservationInfoData.RESERVATION_KEY, reservationInfoUiModel)
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
            savedInstanceState?.getStringArrayList(Extras.SeatsData.SEATS_KEY)
                ?: emptyList<String>()
        presenter.restoreSelectedSeats(savedSeats)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putStringArrayList(
            Extras.SeatsData.SEATS_KEY,
            ArrayList(presenter.getSelectedSeatIds()),
        )
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupSavedData(savedInstanceState)
        presenter.restoreButtonState()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
