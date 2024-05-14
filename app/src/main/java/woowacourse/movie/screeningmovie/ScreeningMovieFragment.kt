package woowacourse.movie.screeningmovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.data.RoomMovieRepository
import woowacourse.movie.databinding.FragmentScreeningMovieBinding
import woowacourse.movie.screeningmovie.theaters.TheaterBottomSheetDialogFragment

class ScreeningMovieFragment : Fragment(), ScreeningMovieContract.View, AdapterClickListener {
    private lateinit var presenter: ScreenMoviePresenter
    private var _binding: FragmentScreeningMovieBinding? = null
    val binding: FragmentScreeningMovieBinding
        get() = requireNotNull(_binding) { "${this::class.java.simpleName}에서 에러가 발생했습니다." }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentScreeningMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ScreenMoviePresenter(this, RoomMovieRepository.instance())
        presenter.loadScreeningMovies()
    }

    override fun showMovies(movies: List<ScreeningItem>) {
        binding.rcvScreeningMovie.adapter =
            MovieAdapter(this).apply { this.submitList(movies) }
    }

    override fun onClick(screeningMovieId: Long) {
        val fragment = TheaterBottomSheetDialogFragment()
        val bundle = TheaterBottomSheetDialogFragment.getBundle(screeningMovieId)
        fragment.arguments = bundle
        fragment.show(parentFragmentManager, "theaterBottomSheet")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
