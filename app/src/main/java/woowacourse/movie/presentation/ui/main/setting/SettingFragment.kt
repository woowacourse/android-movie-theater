package woowacourse.movie.presentation.ui.main.setting

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null

    private val binding
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        activity?.let {
            val pref = getNotificationPreferences()
            val isPermissionGranted = checkNotificationPermission()
            val isReservationNotifiable = pref.getBoolean(PREF_KEY_RESERVATION_NOTIFICATION, false)
            initSwitch(isPermissionGranted)
            binding.isGranted = isReservationNotifiable
            initButton()
        }
    }

    private fun checkNotificationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireActivity(),
            POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun initSwitch(isPermissionGranted: Boolean) {
        binding.switchSetting.isEnabled = isPermissionGranted
        binding.switchSetting.setOnCheckedChangeListener { _, isChecked ->
            val pref = getNotificationPreferences()
            val edit = pref.edit()
            edit.putBoolean(PREF_KEY_RESERVATION_NOTIFICATION, isChecked && isPermissionGranted)
                ?.apply()
        }
    }

    private fun initButton() {
        binding.btnSetting.setOnClickListener {
            moveToSetting()
        }
    }

    private fun moveToSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val packageName = context?.packageName ?: return
        intent.data = Uri.parse("package:$packageName")
        context?.startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getNotificationPreferences(): SharedPreferences {
        return requireActivity().getSharedPreferences(
            PREF_KEY_RESERVATION_NOTIFICATION_SETTINGS,
            Context.MODE_PRIVATE,
        )
    }

    companion object {
        const val PREF_KEY_RESERVATION_NOTIFICATION_SETTINGS = "ReservationNotificationSettings"
        const val PREF_KEY_RESERVATION_NOTIFICATION = "reservation_notification"
    }
}
