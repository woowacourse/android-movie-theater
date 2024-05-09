package woowacourse.movie.ui.setting

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMovieSettingBinding

class MovieSettingFragment : Fragment() {
    private lateinit var binding: FragmentMovieSettingBinding
    private val sharedPreference: SharedPreferences by lazy { initializeSharedPreference() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_movie_setting,
                container,
                false,
            )

        binding.swAlarmStatus.isChecked = getAlarmChecked()
        binding.swAlarmStatus.setOnCheckedChangeListener { _, isChecked ->
            setAlarmChecked(isChecked)
            if (!isChecked) {
                context?.let { MovieAlarmManager.cancelAlarm(it) }
            }
        }

        return binding.root
    }

    private fun initializeSharedPreference(): SharedPreferences =
        activity?.getSharedPreferences("settings", MODE_PRIVATE)
            ?: throw IllegalStateException()

    private fun setAlarmChecked(isChecked: Boolean) {
        with(sharedPreference.edit()) {
            putBoolean("settings", isChecked)
            apply()
        }
    }

    private fun getAlarmChecked(): Boolean {
        return sharedPreference.getBoolean("settings", false)
    }
}
