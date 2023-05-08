package woowacourse.movie.presentation.views.main.fragments.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.presentation.model.movieitem.Ad
import woowacourse.movie.presentation.model.movieitem.Movie
import woowacourse.movie.presentation.views.main.fragments.home.contract.HomeContract
import woowacourse.movie.presentation.views.main.fragments.home.contract.presenter.HomePresenter
import woowacourse.movie.presentation.views.main.fragments.theater.TheaterPickerDialog

class HomeFragment : Fragment(R.layout.fragment_home), HomeContract.View {
    override val presenter: HomeContract.Presenter by lazy { HomePresenter(view = this) }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        binding.presenter = presenter
        return binding.root
    }

    override fun showTheaterPickerScreen(item: Movie) {
        val theaterPickerDialog = TheaterPickerDialog.getInstance(item)
        theaterPickerDialog.show(childFragmentManager, TheaterPickerDialog.TAG)
    }

    override fun showAdWebSite(item: Ad) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
        startActivity(intent)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        internal const val TAG = "HomeFragment"
    }
}
