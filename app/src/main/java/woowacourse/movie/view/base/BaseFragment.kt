package woowacourse.movie.view.base

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.AlarmManagerCompat.canScheduleExactAlarms
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import woowacourse.movie.view.extension.alarmManager

abstract class BaseFragment<T : ViewBinding>(val layoutId: Int) : Fragment() {
    protected lateinit var binding: T
    private var _binding: T? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding =
            DataBindingUtil.inflate(
                inflater,
                layoutId,
                container,
                false,
            )
        binding = _binding!!
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun requestExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= SCHEDULE_EXACT_ALARM_SDK_VERSION) {
            startActivity(Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
        }
    }

    protected fun hasAllPermissions(): Boolean {
        return hasNotificationPermission() && hasExactAlarmPermission()
    }

    protected fun hasExactAlarmPermission(): Boolean = canScheduleExactAlarms(requireContext().alarmManager())

    protected fun hasNotificationPermission(): Boolean =
        ContextCompat.checkSelfPermission(requireContext(), POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED

    companion object {
        protected const val POST_NOTIFICATIONS = "android.permission.POST_NOTIFICATIONS"
        protected const val SCHEDULE_EXACT_ALARM_SDK_VERSION = 31
    }
}
