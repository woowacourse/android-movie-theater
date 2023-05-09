package woowacourse.movie.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.data.settings.SettingsPreference
import woowacourse.movie.databinding.FragmentSettingsBinding
import woowacourse.movie.presentation.allowance.PreferenceKey

class SettingsFragment : Fragment(), SettingsContract.View {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private var _presenter: SettingsContract.Presenter? = null
    override val presenter: SettingsContract.Presenter = _presenter!!

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

        initPresenter()
        presenter.initNotifiable()
    }

    private fun initPresenter() {
        val prefKey = PreferenceKey.NOTIFICATION_PREF_KEY
        _presenter = SettingsPresenter(
            this,
            SettingsPreference.getInstance(prefKey, requireContext()),
        )
    }

    override fun initNotifiable(isNotifiable: Boolean) {
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
        _presenter = null
    }
}
