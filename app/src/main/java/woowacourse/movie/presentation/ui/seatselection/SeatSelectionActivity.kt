package woowacourse.movie.presentation.ui.seatselection

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivitySeatSelectionBinding
import woowacourse.movie.domain.db.AppDatabase
import woowacourse.movie.domain.db.reservationdb.ReservationDao
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.model.SeatModel
import woowacourse.movie.domain.repository.DummyScreens
import woowacourse.movie.domain.repository.ReservationRepositoryImpl
import woowacourse.movie.presentation.base.BaseActivity
import woowacourse.movie.presentation.model.MessageType
import woowacourse.movie.presentation.model.ReservationInfo
import woowacourse.movie.presentation.model.UserSeat
import woowacourse.movie.presentation.ui.reservation.ReservationActivity
import woowacourse.movie.presentation.ui.reservation.ReservationActivity.Companion.PUT_EXTRA_KEY_RESERVATION_ID
import woowacourse.movie.presentation.ui.seatselection.SeatSelectionContract.View
import woowacourse.movie.receiver.MyBroadCastReceiver
import woowacourse.movie.receiver.MyBroadCastReceiver.Companion.ACTION_NAME
import woowacourse.movie.receiver.MyBroadCastReceiver.Companion.PUT_EXTRA_KEY_ACTIVITY
import woowacourse.movie.receiver.MyBroadCastReceiver.Companion.PUT_EXTRA_KEY_MOVIE_TITLE
import java.io.Serializable
import java.time.LocalDateTime
import java.time.ZoneId

class SeatSelectionActivity : BaseActivity<ActivitySeatSelectionBinding>(), View {
    override val layoutResourceId: Int
        get() = R.layout.activity_seat_selection

    private lateinit var reservationDao: ReservationDao
    private lateinit var reservationRepository: ReservationRepositoryImpl

    val presenter: SeatSelectionPresenter by lazy {
        SeatSelectionPresenter(this, DummyScreens(), reservationRepository)
    }

    override fun initStartView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.handler = this
        initRepository()
        val reservationInfo = getReservationInfoFromIntent()
        val movieId = intent.getIntExtra(PUT_EXTRA_KEY_MOVIE_ID, -1)
        reservationInfo?.let { reservationInfo ->
            presenter.updateUiModel(reservationInfo, movieId)
        }
    }

    private fun initRepository() {
        reservationDao = AppDatabase.getDatabase(applicationContext)!!.reservationDao()
        reservationRepository = ReservationRepositoryImpl(reservationDao)
    }

    private fun getReservationInfoFromIntent() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(
                PUT_EXTRA_KEY_RESERVATION_INFO,
                ReservationInfo::class.java,
            )
        } else {
            intent.getSerializableExtra(PUT_EXTRA_KEY_RESERVATION_INFO) as ReservationInfo
        }

    override fun showSeatModel(seatModel: SeatSelectionUiModel) {
        binding.seatSelectionModel = seatModel
    }

    override fun onSeatClicked(seatModel: SeatModel) {
        presenter.clickSeat(seatModel)
    }

    override fun onReservationButtonClicked() {
        showReservationDialog()
    }

    override fun showReservationDialog() {
        val builder = AlertDialog.Builder(this)
        with(builder) {
            setCancelable(false)
            setTitle(getString(R.string.dialog_reservation_title))
            setMessage(getString(R.string.dialog_reservation_message))
            setPositiveButton(getString(R.string.reservation_done)) { _, _ ->
                presenter.reserve()
            }
            setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }
    }

    override fun setReservationNotification(reservation: Reservation) {
        val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val triggerTime = getNotificationTime(reservation)
        val intent = createAlarmIntent(reservation)
        val pendingIntent = getPendingIntent(intent, reservation.id)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            pendingIntent,
        )
    }

    private fun getNotificationTime(reservation: Reservation): Long {
//        val dateTime = reservation.dateTime.minusMinutes(30) // 미션 요구 사항에 맞는 코드 : 예약한 상영 시간의 30분 전에 알림이 온다
        val dateTime =
            LocalDateTime.now().plusSeconds(20) // 테스트를 위한 코드 : 예약 완료 시점으로부터 20초 뒤에 알림이 온다
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    private fun createAlarmIntent(reservation: Reservation): Intent {
        return Intent(this, MyBroadCastReceiver::class.java).apply {
            action = ACTION_NAME
            putExtra(PUT_EXTRA_KEY_MOVIE_TITLE, reservation.movieTitle)
            putExtra(PUT_EXTRA_KEY_RESERVATION_ID, reservation.id)
            putExtra(PUT_EXTRA_KEY_ACTIVITY, "ReservationActivity")
        }
    }

    private fun getPendingIntent(
        intent: Intent,
        reservationId: Long,
    ): PendingIntent {
        return PendingIntent.getBroadcast(
            this,
            reservationId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }

    override fun navigateToReservation(reservationId: Long) {
        showToastMessage(MessageType.ReservationSuccessMessage)
        ReservationActivity.startActivity(this, reservationId)
    }

    override fun terminateOnError(e: Throwable) {
        showToastMessage(e)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(PUT_STATE_KEY_USER_SEAT, presenter.seatSelectionModel.userSeat)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val savedUserSeat = savedInstanceState.getSerializable(PUT_STATE_KEY_USER_SEAT) as UserSeat?
        savedUserSeat?.let { userSeat ->
            userSeat.seatModels.forEach { seat ->
                presenter.clickSeat(seat)
            }
        }
    }

    companion object {
        private const val PUT_EXTRA_KEY_RESERVATION_INFO = "reservationInfo"
        private const val PUT_STATE_KEY_USER_SEAT = "userSeat"
        private const val PUT_EXTRA_KEY_MOVIE_ID = "movieId"

        fun startActivity(
            context: Context,
            reservationInfo: ReservationInfo,
            movieId: Int,
        ) {
            val intent = Intent(context, SeatSelectionActivity::class.java)
            intent.putExtra(PUT_EXTRA_KEY_RESERVATION_INFO, reservationInfo as Serializable)
            intent.putExtra(PUT_EXTRA_KEY_MOVIE_ID, movieId)
            context.startActivity(intent)
        }
    }
}
