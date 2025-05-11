package woowacourse.movie.view.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.storage.DefaultNotificationPermissionStorage
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.presenter.setting.SettingContracts
import woowacourse.movie.presenter.setting.SettingPresenter

class SettingFragment :
    Fragment(R.layout.fragment_setting),
    SettingContracts.View {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private val presenter: SettingContracts.Presenter by lazy {
        SettingPresenter(
            this,
            DefaultNotificationPermissionStorage(requireContext()),
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSettingBinding.bind(view)
        presenter.updateNotificationPermission()
        setupClickListener()
    }

    private fun setupClickListener() {
        binding.clNotificationPermissionButton.setOnClickListener {
            presenter.updateNotificationPermission(!binding.scSettingNotificationPermission.isChecked)
        }
    }

    override fun showNotificationPermission(isGranted: Boolean) {
        binding.scSettingNotificationPermission.isChecked = isGranted
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
