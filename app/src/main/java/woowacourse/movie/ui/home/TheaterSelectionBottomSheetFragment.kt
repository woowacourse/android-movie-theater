package woowacourse.movie.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentTheaterSelectionBottomSheetBinding
import woowacourse.movie.model.data.MovieContentsImpl
import woowacourse.movie.model.movie.Theater
import woowacourse.movie.ui.home.adapter.TheaterAdapter
import woowacourse.movie.ui.reservation.MovieReservationActivity

class TheaterSelectionBottomSheetFragment :
    BottomSheetDialogFragment(),
    TheaterSelectionContract.View,
    TheaterAdapter.TheaterItemClickListener {
    private lateinit var binding: FragmentTheaterSelectionBottomSheetBinding
    private lateinit var adapter: TheaterAdapter
    private var movieContentId: Long = 0L
    private val presenter: TheaterSelectionPresenter by lazy {
        TheaterSelectionPresenter(
            this,
            MovieContentsImpl,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(MovieHomeKey.FRAGMENT_REQUEST_KEY) { _, bundle ->
            initializeMovieContentId(bundle)
        }
    }

    private fun initializeMovieContentId(bundle: Bundle): Long {
        return bundle.getLong(MovieHomeKey.MOVIE_CONTENT_ID, DEFAULT_MOVIE_CONTENT_ID)
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
        presenter.loadTheaters(movieContentId)
    }

    override fun showTheaters(theaters: List<Theater>) {
        adapter = TheaterAdapter(theaters, this)
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

    override fun dismissDialog() {
        val context = requireContext()
        Toast.makeText(
            context,
            context.getString(R.string.invalid_key),
            Toast.LENGTH_SHORT,
        ).show()
        dismiss()
    }

    override fun onTheaterItemClick(theaterId: Long) {
        presenter.startReservation(movieContentId, theaterId)
    }

    companion object {
        private const val DEFAULT_MOVIE_CONTENT_ID = -1L
    }
}
