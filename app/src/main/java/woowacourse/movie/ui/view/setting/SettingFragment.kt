package woowacourse.movie.ui.view.setting

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import woowacourse.movie.MovieApplication
import woowacourse.movie.data.local.database.MovieDatabase.Companion.getMovieDatabase
import woowacourse.movie.data.local.datasource.TicketDataSourceImpl
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.domain.datasource.SettingsDataSource
import woowacourse.movie.domain.datasource.TicketDataSource
import woowacourse.movie.domain.ticket.TicketHistory
import woowacourse.movie.ui.alarm.Alarm

class SettingFragment : Fragment(), SettingContract.View {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: SettingContract.Presenter
    private lateinit var alarm: Alarm

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val ticketDataSource: TicketDataSource =
            TicketDataSourceImpl(getMovieDatabase(requireContext()).ticketDao())
        val settingDataSource: SettingsDataSource =
            (requireActivity().applicationContext as MovieApplication).settingDataSource
        presenter = SettingPresenter(ticketDataSource, settingDataSource, this)
        alarm = Alarm(requireActivity().applicationContext)
    }

    override fun scheduleAllAlarms(ticketHistories: List<TicketHistory>) {
        ticketHistories.forEach { ticket ->
            alarm.scheduleTicketAlarm(ticket)
        }
    }

    override fun cancelAllAlarms(ticketHistories: List<TicketHistory>) {
        ticketHistories.forEach { ticket ->
            alarm.cancelTicketAlarm(ticket.id)
        }
    }

    override fun switchAlarmSetting(isTicketAlarmChecked: Boolean) {
        binding.switchAlarm.isChecked = isTicketAlarmChecked
        binding.switchAlarm.setOnCheckedChangeListener { _, isChecked ->
            presenter.setIsTicketAlarmChecked(isChecked)
            if (isChecked) {
                handleExactAlarmPermission()
            } else {
                presenter.deleteNotification()
            }
        }
    }

    private fun handleExactAlarmPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            presenter.scheduleAlarms()
            return
        }

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        requestExactAlarmPermission(alarmManager)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun requestExactAlarmPermission(alarmManager: AlarmManager) {
        if (alarmManager.canScheduleExactAlarms()) {
            presenter.scheduleAlarms()
        } else {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
