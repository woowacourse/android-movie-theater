package woowacourse.movie.view.home.movies.bottomsheet

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentTheaterBottomSheetBinding
import woowacourse.movie.view.ext.getParcelableArrayListOrNull
import woowacourse.movie.view.home.movies.adapter.TheaterAdapter
import woowacourse.movie.view.home.movies.model.TheaterRvItem

class TheaterBottomSheet() : BottomSheetDialogFragment(R.layout.fragment_theater_bottom_sheet) {
    private val theaters: List<TheaterRvItem> by lazy {
        arguments?.getParcelableArrayListOrNull(ARG_THEATERS) ?: emptyList()
    }

    private val movieId: Int by lazy {
        arguments?.getInt(ARG_MOVIE_ID) ?: NO_MOVIE_ID
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentTheaterBottomSheetBinding.bind(view)

        initAdapter(binding)
    }

    private fun initAdapter(binding: FragmentTheaterBottomSheetBinding) {
        val theaterAdapter =
            TheaterAdapter(
                theaters,
                object : TheaterAdapter.Handler {
                    override fun onSelectTheater(theaterName: String) {
                        parentFragmentManager.setFragmentResult(
                            KEY_REQUEST,
                            Bundle().apply {
                                putParcelable(KEY_RESULT, Result(theaterName, movieId))
                            },
                        )
                        dismiss()
                    }
                },
            )
        binding.rv.adapter = theaterAdapter
    }

    companion object {
        private const val NO_MOVIE_ID = -1
        private const val ARG_THEATERS = "ARG_THEATERS"
        private const val ARG_MOVIE_ID = "ARG_MOVIE_ID"
        const val KEY_RESULT = "KEY_RESULT"
        const val KEY_REQUEST = "KEY_REQUEST"

        fun newInstance(
            theaters: List<TheaterRvItem>,
            movieId: Int,
        ): TheaterBottomSheet {
            return TheaterBottomSheet().apply {
                arguments =
                    Bundle().apply {
                        putParcelableArrayList(ARG_THEATERS, ArrayList(theaters))
                        putInt(ARG_MOVIE_ID, movieId)
                    }
            }
        }
    }
}
