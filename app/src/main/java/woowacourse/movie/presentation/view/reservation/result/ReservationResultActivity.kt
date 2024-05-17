package woowacourse.movie.presentation.view.reservation.result

import android.content.IntentFilter
import android.os.Bundle
import androidx.core.content.IntentCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationResultBinding
import woowacourse.movie.presentation.base.BaseActivity
import woowacourse.movie.presentation.uimodel.MovieTicketUiModel
import woowacourse.movie.alarm.AlarmReceiver
import woowacourse.movie.alarm.MovieStartAlarmManager

class ReservationResultActivity : BaseActivity() {
    private lateinit var binding: ActivityReservationResultBinding
    private lateinit var ticket: MovieTicketUiModel
    private lateinit var alarmManager: MovieStartAlarmManager

    override fun getLayoutResId(): Int = R.layout.activity_reservation_result

    override fun onCreateSetup(savedInstanceState: Bundle?) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reservation_result)
        setUpFromIntent()
        if (intent.getBooleanExtra(INTENT_SET_ALARM, false)) {
            setAlarmManager()
            setAlarmReceiver()
        }
    }

    private fun setUpFromIntent() {
        ticket = IntentCompat.getParcelableExtra(intent, INTENT_TICKET, MovieTicketUiModel::class.java)!!
        binding.data = ticket
    }

    private fun setAlarmManager() {
        alarmManager = MovieStartAlarmManager(this, ticket)
        alarmManager.setAlarm()
    }

    private fun setAlarmReceiver() {
        val alarmReceiver = AlarmReceiver()
        val intentFilter = IntentFilter().apply {
            addAction(getString(R.string.notification_action))
        }
        registerReceiver(alarmReceiver, intentFilter, RECEIVER_NOT_EXPORTED)
    }

    companion object {
        const val INTENT_TICKET = "ticket"
        const val INTENT_SET_ALARM = "setAlarm"
    }
}
