package woowacourse.movie.ui.setting

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.preferences.MoviePreferencesUtil
import woowacourse.movie.databinding.FragmentMovieSettingBinding

class MovieSettingFragment : Fragment(), MovieSettingContract.View {
    private var _binding: FragmentMovieSettingBinding? = null
    private val binding: FragmentMovieSettingBinding
        get() = _binding!!
    private val moviePreferencesUtil by lazy { MoviePreferencesUtil(requireContext()) }
    private val movieSettingPresenter: MovieSettingPresenter by lazy {
        MovieSettingPresenter(this, moviePreferencesUtil)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_setting, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieSettingPresenter.loadInitialSetting()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun setInitialSetting(isEnabled: Boolean) {
        binding.apply {
            presenter = movieSettingPresenter
            tvSettingTitle.text = getString(R.string.setting_menu_title_notification)
            tvSettingDescription.text = getString(R.string.setting_menu_description_notification)
            isNotificationActive = isEnabled
            executePendingBindings()
        }
    }

    override fun updateReceiveNotificationStatus(isEnabled: Boolean) {
        binding.isNotificationActive = isEnabled
        binding.executePendingBindings()
    }

    override fun showError(throwable: Throwable) {
        Toast.makeText(requireContext(), R.string.toast_invalid_key, Toast.LENGTH_SHORT).show()
    }
}
