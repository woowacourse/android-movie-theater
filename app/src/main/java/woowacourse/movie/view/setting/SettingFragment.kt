package woowacourse.movie.view.setting

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.app.AlarmManagerCompat.canScheduleExactAlarms
import androidx.core.content.edit
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.view.base.BaseFragment
import woowacourse.movie.view.receiver.NotificationReceiver
import java.time.ZoneId

class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting), SettingContract.View {
    private val presenter: SettingContract.Presenter by lazy { SettingPresenter(this) }
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requestPermissionLauncher =
            registerForActivityResult(
                RequestPermission(),
            ) { isGranted: Boolean ->
                if (isGranted) {
                    presenter.setNotification()
                }
            }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setPermissionSwitch()
    }

    override fun setNotification(tickets: List<Ticket>) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (alarmManager.canScheduleExactAlarms()) {
                tickets.forEach {
                    val intent =
                        Intent(context, NotificationReceiver::class.java).apply {
                            putExtra("notification_title", "예매 알림")
                            putExtra("notification_text", it.title)
                        }

                    val pendingIntent =
                        PendingIntent.getBroadcast(
                            context,
                            0,
                            intent,
                            PendingIntent.FLAG_UPDATE_CURRENT,
                        )

                    alarmManager.set(
                        AlarmManager.RTC,
                        it.showTime.minusMinutes(30)
                            .atZone(ZoneId.of("Asia/Seoul"))
                            .toInstant()
                            .toEpochMilli(),
                        pendingIntent,
                    )
                }
            }
        }
    }

    override fun setPermissionSwitch() {
        val preference = requireActivity().getSharedPreferences(requireActivity().packageName, Context.MODE_PRIVATE)
        if (preference.getString("FIRST_RUN", "true") == "true") {
            preference.edit {
                putString("FIRST_RUN", "false")
                    .putString("FIRST_REQUEST", "true")
            }
        }

        binding.switchSettingPushAlarm.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}
