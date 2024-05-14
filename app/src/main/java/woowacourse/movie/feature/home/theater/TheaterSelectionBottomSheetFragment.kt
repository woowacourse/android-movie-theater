package woowacourse.movie.feature.home.theater

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.BottomSheetFragmentTheaterSelectionBinding
import woowacourse.movie.feature.detail.MovieDetailActivity
import woowacourse.movie.feature.home.theater.adapter.TheaterAdapter
import woowacourse.movie.model.Theater
import woowacourse.movie.util.MovieIntentConstant.INVALID_VALUE_MOVIE_ID
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_ID

class TheaterSelectionBottomSheetFragment :
    BottomSheetDialogFragment(),
    TheaterSelectionContract.View {
    private var _binding: BottomSheetFragmentTheaterSelectionBinding? = null
    private val binding get() = _binding!!

    private val presenter by lazy { TheaterSelectionPresenter(this) }
    private val movieId: Long by lazy { arguments?.getLong(KEY_MOVIE_ID) ?: INVALID_VALUE_MOVIE_ID }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = BottomSheetFragmentTheaterSelectionBinding.inflate(inflater)

        initializeView()
        return binding.root
    }

    private fun initializeView() {
        presenter.loadTheaters(movieId)
    }

    override fun setUpTheaterAdapter(theaters: List<Theater>) {
        binding.theaterRecyclerview.adapter =
            TheaterAdapter(theaters, onTheaterItemClick = { selectedTheaterIndex ->
                val intent = MovieDetailActivity.newIntent(requireContext(), movieId, selectedTheaterIndex)
                startActivity(intent)
                dismiss()
            })
    }

    override fun showToastInvalidMovieIdError(throwable: Throwable) {
        Log.e(TAG, "invalid movie id - ${throwable.message}")
        showToast(R.string.invalid_movie_id)
        dismiss()
    }

    private fun showToast(
        @StringRes stringResId: Int,
    ) {
        Toast.makeText(requireContext(), resources.getString(stringResId), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val TAG = TheaterSelectionBottomSheetFragment::class.simpleName

        fun newInstance(movieId: Long): TheaterSelectionBottomSheetFragment {
            return TheaterSelectionBottomSheetFragment().apply {
                val bundle = Bundle()
                bundle.putLong(KEY_MOVIE_ID, movieId)
                arguments = bundle
            }
        }
    }
}
