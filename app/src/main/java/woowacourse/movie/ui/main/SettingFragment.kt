package woowacourse.movie.ui.main

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.model.SettingSharedPreference
import woowacourse.movie.permission.getPermissionLauncher
import woowacourse.movie.permission.requestPermission

class SettingFragment : Fragment(), SettingContract.View {

    private val settingSharedPreference by lazy {
        SettingSharedPreference(requireContext())
    }
    private val switch: Switch by lazy {
        requireActivity().findViewById(R.id.switchPushAlarm)
    }
    private val settingPresenter: SettingPresenter by lazy {
        SettingPresenter(
            view = this,
            settingRepository = settingSharedPreference
        )
    }
    private val permissionLauncher: ActivityResultLauncher<String> =
        getPermissionLauncher(
            deniedCase = {
                settingPresenter.saveSetting(false)
            },
            allowedCase = {
                settingPresenter.saveSetting(true)
            }
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingPresenter.loadSetting()
        initSwitch()
    }

    private fun initSwitch() {
        setCheckedChangedListener(switch)
    }

    override fun setSwitch(isChecked: Boolean) {
        switch.isChecked = isChecked
    }

    private fun setCheckedChangedListener(switch: Switch) {
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                requestPermission(
                    switch.context,
                    permissionLauncher,
                    Manifest.permission.POST_NOTIFICATIONS
                )
                settingPresenter.saveSetting(true)
            } else {
                settingPresenter.saveSetting(false)
            }
        }
    }
}
