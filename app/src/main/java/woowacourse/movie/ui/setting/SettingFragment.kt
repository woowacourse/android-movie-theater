package woowacourse.movie.ui.setting

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.permission.getPermissionLauncher
import woowacourse.movie.permission.requestPermission

class SettingFragment : Fragment(), SettingContract.View {

    private var _binding: FragmentSettingBinding? = null
    private val binding: FragmentSettingBinding
        get() = _binding!!

    private val settingPresenter: SettingPresenter by lazy {
        SettingPresenter(
            view = this,
            settingRepository = SettingSharedPreference(requireContext())
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
    ): View {
        _binding = FragmentSettingBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingPresenter.loadSetting()
        initCheckedChangedListener()
    }

    override fun setSwitch(isChecked: Boolean) {
        binding.switchPushAlarm.isChecked = isChecked
    }

    private fun initCheckedChangedListener() {
        binding.switchPushAlarm.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                requestPermission(
                    binding.switchPushAlarm.context,
                    permissionLauncher,
                    Manifest.permission.POST_NOTIFICATIONS
                )
                settingPresenter.saveSetting(true)
            } else {
                settingPresenter.saveSetting(false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}
