package woowacourse.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.activity.moviedetail.MovieDetailActivity
import woowacourse.movie.bottomsheat.TheaterRecyclerViewAdapter
import woowacourse.movie.databinding.BottomSheetTheatersBinding
import woowacourse.movie.movie.MovieMockData

class BottomSheetTheaterPicker(
    private val pickedMoviePosition: Int
) : BottomSheetDialogFragment() {
    private var _binding: BottomSheetTheatersBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_theaters, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTheaterRecyclerView()
    }

    private fun setTheaterRecyclerView() {
        val theaterRecyclerViewAdapter = TheaterRecyclerViewAdapter(
            MovieMockData.movies10000[pickedMoviePosition],
            TheaterMockData.dummyTheaters,
            onTheaterClicked()
        )
        binding.rvTheaterList.adapter = theaterRecyclerViewAdapter
        theaterRecyclerViewAdapter.notifyDataSetChanged()
    }

    private fun onTheaterClicked(): (position: Int) -> Unit = { position ->
        val intent = MovieDetailActivity.intent(requireContext())
        intent.putExtra(BundleKeys.MOVIE_DATA_KEY, MovieMockData.movies10000[pickedMoviePosition])
        intent.putExtra(BundleKeys.THEATER_DATA_KEY, TheaterMockData.dummyTheaters[position])
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
