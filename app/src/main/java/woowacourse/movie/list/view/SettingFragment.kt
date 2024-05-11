package woowacourse.movie.list.view

import android.Manifest
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    private var sharedPreference = requireActivity().getSharedPreferences(SHARED_PREFERENCE_SETTING, AppCompatActivity.MODE_PRIVATE)
    private val sharedPreferenceEditor: SharedPreferences.Editor = sharedPreference.edit()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        val isPermissionGranted = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.POST_NOTIFICATIONS
        ) != -1
        sharedPreferenceEditor.putBoolean(KEY_NOTIFICATION, isPermissionGranted).apply()
        if (!isPermissionGranted) {
            binding.permissionSwitch.isClickable = false
        }
        binding.permissionSwitch.isChecked = isPermissionGranted
        storeNotificationIsGrantedOrNot()
        return binding.root
    }

    private fun storeNotificationIsGrantedOrNot() {
        binding.permissionSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sharedPreferenceEditor.putBoolean(KEY_NOTIFICATION, true).apply()
            } else {
                sharedPreferenceEditor.putBoolean(KEY_NOTIFICATION, false).apply()
            }
        }
    }

    companion object {
        const val SHARED_PREFERENCE_SETTING = "settings"
        const val KEY_NOTIFICATION = "notification"
    }
}
