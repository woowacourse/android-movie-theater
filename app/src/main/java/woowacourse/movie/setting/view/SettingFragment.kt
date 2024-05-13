package woowacourse.movie.setting.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.MovieMainActivity.Companion.sharedPrefs
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.setting.presenter.SettingPresenter
import woowacourse.movie.setting.presenter.contract.SettingContract

class SettingFragment : Fragment(), SettingContract.View {
    private lateinit var binding: FragmentSettingBinding
    private lateinit var settingPresenter: SettingPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            FragmentSettingBinding.inflate(inflater, container, false)

        settingPresenter = SettingPresenter(this)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        settingPresenter.loadAlarmSwitch()
    }

    override fun setUpAlarmSwitch(savedAlarmSetting: Boolean) {
        var isGranted = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            isGranted = ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED
        }

        val isChecked = judgeChecked(isGranted, savedAlarmSetting)
        binding.isGranted = isGranted
        binding.isChecked = isChecked
        binding.switchSettingAlarm.setOnCheckedChangeListener { _, onSwitch ->
            sharedPrefs.saveAlarmSetting(onSwitch)
        }
    }

    private fun judgeChecked(
        isGranted: Boolean,
        savedAlarmSetting: Boolean,
    ): Boolean {
        if (isGranted) return savedAlarmSetting
        sharedPrefs.saveAlarmSetting(false)
        return false
    }
}
