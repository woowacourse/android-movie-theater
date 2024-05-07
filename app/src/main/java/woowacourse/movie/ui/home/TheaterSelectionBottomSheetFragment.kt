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
import woowacourse.movie.model.data.TheatersImpl
import woowacourse.movie.model.movie.Theater
import woowacourse.movie.ui.home.adapter.TheaterAdapter
import woowacourse.movie.ui.reservation.MovieReservationActivity

class TheaterSelectionBottomSheetFragment :
    BottomSheetDialogFragment(),
    TheaterSelectionContract.View,
    TheaterAdapter.TheaterClickListener {
    private var movieContentId: Long = DEFAULT_MOVIE_CONTENT_ID
    private lateinit var binding: FragmentTheaterSelectionBottomSheetBinding
    private lateinit var theaters: List<Theater>
    private val adapter: TheaterAdapter by lazy { generateTheaterAdapter() }
    private val presenter: TheaterSelectionPresenter by lazy { generatePresenter() }

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
        setFragmentResultListener(MovieHomeKey.FRAGMENT_REQUEST_KEY) { _, bundle ->
            presenter.loadTheaters(
                bundle.getLong(MovieHomeKey.MOVIE_CONTENT_ID),
            )
        }
    }

    override fun showTheaters(
        movieContentId: Long,
        theaters: List<Theater>,
    ) {
        this.theaters = theaters
        this.movieContentId = movieContentId
        binding.theaterList.adapter = adapter
    }

    override fun showError(throwable: Throwable) {
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.toast_invalid_key),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onTheaterClick(movieContentId: Long, theaterId: Long) {
        Intent(requireContext(), MovieReservationActivity::class.java)
            .putExtra(MovieHomeKey.MOVIE_CONTENT_ID, movieContentId)
            .putExtra(MovieHomeKey.THEATER_ID, theaterId)
            .also(::startActivity)
    }

    private fun generateTheaterAdapter() =
        TheaterAdapter(theaters, movieContentId, this)

    private fun generatePresenter() =
        TheaterSelectionPresenter(this, MovieContentsImpl, TheatersImpl)

    companion object {
        private const val DEFAULT_MOVIE_CONTENT_ID: Long = -1L
    }
}
