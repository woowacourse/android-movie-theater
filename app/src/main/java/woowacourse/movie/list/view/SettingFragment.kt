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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        val isPermissionGranted = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.POST_NOTIFICATIONS
        ) != -1
        if (!isPermissionGranted) {
            binding.permissionSwitch.isClickable = false
        }
        binding.permissionSwitch.isChecked = isPermissionGranted
        val sharedPreference = requireActivity().getSharedPreferences("notification", AppCompatActivity.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreference.edit()
        binding.permissionSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                editor.putBoolean("notification", true).commit()
            } else {
                editor.putBoolean("notification", false).commit()
            }
        }
        return binding.root
    }
}
