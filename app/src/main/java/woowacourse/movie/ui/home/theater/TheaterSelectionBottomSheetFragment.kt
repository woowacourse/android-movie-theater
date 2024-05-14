package woowacourse.movie.ui.home.theater

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentTheaterSelectionBottomSheetBinding
import woowacourse.movie.model.data.MovieContentsImpl
import woowacourse.movie.model.data.TheatersImpl
import woowacourse.movie.model.movie.Theater
import woowacourse.movie.ui.home.MovieHomeKey
import woowacourse.movie.ui.reservation.MovieReservationActivity

class TheaterSelectionBottomSheetFragment :
    BottomSheetDialogFragment(),
    TheaterSelectionContract.View {
    private lateinit var binding: FragmentTheaterSelectionBottomSheetBinding
    private lateinit var adapter: TheaterAdapter
    private val movieId: Long by lazy {
        arguments?.getLong(MovieHomeKey.MOVIE_CONTENT_ID) ?: DEFAULT_VALUE
    }
    private val presenter: TheaterSelectionPresenter by lazy {
        TheaterSelectionPresenter(
            this,
            MovieContentsImpl,
            TheatersImpl,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentTheaterSelectionBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadTheaters(movieId)
    }

    override fun showTheaters(
        movieContentId: Long,
        theaters: List<Theater>,
    ) {
        adapter =
            TheaterAdapter(theaters) { theaterId ->
                MovieReservationActivity.startActivity(requireContext(), movieContentId, theaterId)
            }
        binding.theaterList.adapter = adapter
    }

    override fun navigateToMovieReservation(
        movieContentId: Long,
        theaterId: Long,
    ) {
        Intent(requireContext(), MovieReservationActivity::class.java).apply {
            putExtra(MovieHomeKey.MOVIE_CONTENT_ID, movieContentId)
            putExtra(MovieHomeKey.THEATER_ID, theaterId)
            startActivity(this)
        }
    }

    override fun showError() {
        val context = requireContext()
        Toast.makeText(
            context,
            context.getString(R.string.invalid_key),
            Toast.LENGTH_SHORT,
        ).show()
    }

    override fun dismissErrorDialog() {
        dismiss()
    }

    companion object {
        private const val DEFAULT_VALUE = -1L
    }
}
