package woowacourse.movie.presentation.homefragments.setting

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentSettingBinding

class SettingFragment : Fragment(), SettingContract.View {
    private var _binding: FragmentSettingBinding? = null
    val binding get() = _binding!!
    private val presenter by lazy { SettingPresenter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = activity?.getPreferences(MODE_PRIVATE)
        val savedValue = sharedPref?.getBoolean(SHARED_PREF_KEY_NOTIFICATION, false)
        presenter.loadNotificationState(savedValue)

        binding.switchSetting.setOnCheckedChangeListener { _, isChecked ->
            saveNotificationState(isChecked)
        }
    }

    private fun saveNotificationState(state: Boolean) {
        val sharedPref = activity?.getPreferences(MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putBoolean(SHARED_PREF_KEY_NOTIFICATION, state)
            apply()
        }
    }

    override fun setNotificationState(isChecked: Boolean) {
        binding.switchSetting.isChecked = isChecked
    }

    companion object {
        const val SHARED_PREF_KEY_NOTIFICATION = "notification"
    }
}
