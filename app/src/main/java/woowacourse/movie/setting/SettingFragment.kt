package woowacourse.movie.setting

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding

class SettingFragment : Fragment(), SettingContract.View {
    private lateinit var presenter: SettingPresenter
    private lateinit var binding: FragmentSettingBinding
    private lateinit var sharedPreference: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        sharedPreference = requireContext().getSharedPreferences("settings", MODE_PRIVATE)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        presenter = SettingPresenter(this)

        showAlarm()
        binding.switchAlarm.setOnCheckedChangeListener { _, isChecked ->
            updateAlarm(isChecked)
        }
    }

    private fun updateAlarm(isChecked: Boolean) {
        sharedPreference.edit(commit = true) {
            putBoolean("notification", isChecked)
        }
    }

    override fun showAlarm() {
        val saved = sharedPreference.getBoolean("notification", false)
        binding.switchAlarm.isChecked = saved
    }
}
