package woowacourse.movie.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.data.settings.SettingsPreference
import woowacourse.movie.databinding.FragmentSettingsBinding
import woowacourse.movie.presentation.allowance.SettingsAllowance

class SettingsFragment : Fragment(), SettingsContract.View {

    override lateinit var presenter: SettingsContract.Presenter

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefKey = SettingsAllowance.NOTIFICATION_PREF_KEY
        presenter = SettingsPresenter(
            this,
            SettingsPreference.getInstance(prefKey, requireContext()),
        )
    }

    override fun initNotificationSwitch(isNotifiable: Boolean) {
        val notificationSwitch =
            binding.switchPushPermission

        notificationSwitch.isChecked = isNotifiable

        notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            presenter.isNotifiable = isChecked
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
