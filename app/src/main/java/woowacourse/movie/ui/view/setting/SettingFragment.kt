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
import androidx.fragment.app.Fragment
import woowacourse.movie.data.local.adapter.TicketData
import woowacourse.movie.data.local.database.MovieDatabase.Companion.getMovieDatabase
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.domain.ticket.Ticket
import woowacourse.movie.ui.view.alarm.Alarm

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
        val ticketDataAdapter = TicketData(getMovieDatabase(requireContext()).ticketDao())
        presenter = SettingPresenter(ticketDataAdapter, this)
        alarm = Alarm(requireActivity().applicationContext)
        presenter.presentScreen()
    }

    override fun scheduleAllAlarms(tickets: List<Ticket>) {
        tickets.forEach { ticket ->
            alarm.scheduleTicketAlarm(ticket)
        }
    }

    override fun cancelAllAlarms(tickets: List<Ticket>) {
        tickets.forEach { ticket ->
            alarm.cancelTicketAlarm(ticket.id ?: return)
        }
    }

    override fun switchAlarmSetting() {
        val sharedPreferences =
            requireActivity().getSharedPreferences("settings", Context.MODE_PRIVATE)
        val isTicketAlarmChecked = sharedPreferences.getBoolean("isTicketAlarm", false)
        binding.switchAlarm.isChecked = isTicketAlarmChecked
        binding.switchAlarm.setOnCheckedChangeListener { _, isChecked ->
            with(sharedPreferences.edit()) {
                putBoolean("isTicketAlarm", isChecked)
                apply()
            }
            if (isChecked) {
                handleExactAlarmPermission()
            } else {
                presenter.deleteNotification()
            }
        }
    }

    private fun handleExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager =
                requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                startActivity(intent)
            } else {
                presenter.setNotification()
            }
        } else {
            presenter.setNotification()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
