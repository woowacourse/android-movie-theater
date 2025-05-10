package woowacourse.movie.setting

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.SettingPreference
import woowacourse.movie.databinding.FragmentSettingBinding

class SettingFragment : Fragment(), SettingContract.View {
    private val presenter: SettingPresenter by lazy {
        SettingPresenter(
            this,
            SettingPreference(requireContext()),
        )
    }
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        presenter.setPermissionState(requireContext())
    }

    override fun initAlarmState(isGrant: Boolean) {
        setAlarmState(isGrant)
        binding.switchAlarm.setOnCheckedChangeListener { _, isChecked ->
            presenter.checkPermission(isChecked, requireContext())
        }
    }

    private fun setAlarmState(isGrant: Boolean) {
        binding.switchAlarm.isChecked = isGrant
    }

    override fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireContext(), "알림 설정 완료", Toast.LENGTH_SHORT).show()
                presenter.updatePermission(true)
                setAlarmState(true)
            } else {
                Toast.makeText(requireContext(), "알림 거부됨", Toast.LENGTH_SHORT).show()
                setAlarmState(false)
            }
        }
}
