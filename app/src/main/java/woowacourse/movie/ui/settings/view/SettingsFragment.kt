package woowacourse.movie.ui.settings.view

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import java.time.ZoneId
import woowacourse.movie.R
import woowacourse.movie.data.BookedTicketDatabase
import woowacourse.movie.databinding.FragmentSettingsBinding
import woowacourse.movie.domain.model.BookedTicket
import woowacourse.movie.notification.MovieReminderReceiver
import woowacourse.movie.utils.AlarmManagerCompat

class SettingsFragment : Fragment(), SettingsContract.View {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val presenter: SettingsContract.Presenter by lazy { SettingsPresenter(this) }
    private val sharedPrefs: SharedPreferences by lazy {
        requireContext().getSharedPreferences(getString(R.string.preference_key), MODE_PRIVATE)
    }
    private val isEnablePostNotification: Boolean
        get() = sharedPrefs.getBoolean(getString(R.string.preference_post_notification), true)
    private val bookedTicketDatabase by lazy { BookedTicketDatabase.getInstance(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadDatabase(bookedTicketDatabase)
        syncNotificationPermissionWithPrefsAndUI()
        binding.switchPushNotification.setOnClickListener {
            updateNotificationSetting(!isEnablePostNotification)
        }
    }

    override fun onResume() {
        super.onResume()
        syncNotificationPermissionWithPrefsAndUI()
    }

    override fun notifyMovieReminderRegistered(availableNotificationTickets: List<BookedTicket>) {
        availableNotificationTickets.forEach { bookedTicket ->
            scheduleNotification(bookedTicket)
        }
    }

    override fun notifyMovieReminderCleared() {
        /*
         * 설정 변경 시 기존 알림 설정한 데이터 제거 필요
         * PendingIntent 정보를 room에 저장해야 정확히 비교 후 제거할 수 있을텐데 일단 진행
         * */
    }

    private fun syncNotificationPermissionWithPrefsAndUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val isGranted =
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS,
                ) == PackageManager.PERMISSION_GRANTED

            if (isEnablePostNotification != isGranted) {
                presenter.handleMovieNotificationByToggle(isGranted)
            }

            sharedPrefs.edit {
                putBoolean(getString(R.string.preference_post_notification), isGranted)
            }

            binding.isEnablePostNotification = isEnablePostNotification
        }
    }

    private fun updateNotificationSetting(isEnabled: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val intent =
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", requireContext().packageName, null)
                }
            startActivity(intent)
            return
        }

        sharedPrefs.edit {
            putBoolean(getString(R.string.preference_post_notification), isEnabled)
        }
        binding.isEnablePostNotification = isEnabled
        presenter.handleMovieNotificationByToggle(isEnabled)
    }

    private fun scheduleNotification(bookedTicket: BookedTicket) {
        val triggerTime = bookedTicket.movieSchedule.screeningDateTime.minusMinutes(30L)
        val triggerAtMillis =
            triggerTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        val requestCode = bookedTicket.hashCode()
        val intent = MovieReminderReceiver.newIntent(requireContext(), bookedTicket)
        val pendingIntent =
            PendingIntent.getBroadcast(
                requireContext(),
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        AlarmManagerCompat.setExact(requireContext(), triggerAtMillis, pendingIntent)
    }
}
