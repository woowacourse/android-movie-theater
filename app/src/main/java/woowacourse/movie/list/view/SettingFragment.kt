package woowacourse.movie.list.view

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.database.NotificationSharedPreference
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.list.contract.SettingContract
import woowacourse.movie.list.presenter.SettingPresenter

class SettingFragment : Fragment(), SettingContract.View {
    private lateinit var binding: FragmentSettingBinding
    private lateinit var presenter: SettingContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        presenter = SettingPresenter(this, NotificationSharedPreference(requireContext().applicationContext))
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        setInitialSwitch()
        setOnSettingChangeListener()
        return binding.root
    }

    private fun setInitialSwitch() {
        val isPermissionGranted = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.POST_NOTIFICATIONS
        ) != -1
        if (!isPermissionGranted) {
            binding.permissionSwitch.isClickable = false
        }
        binding.permissionSwitch.isChecked = isPermissionGranted
    }

    private fun setOnSettingChangeListener() {
        binding.permissionSwitch.setOnCheckedChangeListener { _, isChecked ->
            presenter.saveNotificationGranted(isChecked)
        }
    }
}
