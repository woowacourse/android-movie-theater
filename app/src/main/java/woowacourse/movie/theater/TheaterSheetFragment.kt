package woowacourse.movie.theater

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.databinding.FragmentTheaterSheetBinding
import woowacourse.movie.dto.movie.MovieDto
import woowacourse.movie.dto.theater.MovieTheaterDto
import woowacourse.movie.moviedetail.MovieDetailActivity
import woowacourse.movie.moviedetail.MovieDetailActivity.Companion.MOVIE_KEY
import woowacourse.movie.movielist.HomeFragment
import woowacourse.movie.utils.getParcelableCompat

class TheaterSheetFragment : BottomSheetDialogFragment(), TheaterContract.View {
    private lateinit var binding: FragmentTheaterSheetBinding
    override lateinit var presenter: TheaterContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setUpBinding()
        presenter = TheaterPresenter(this)
        initDetailData()
        return binding.root
    }

    private fun setUpBinding() {
        binding = FragmentTheaterSheetBinding.inflate(layoutInflater)
    }

    override fun initDetailData() {
        val movie =
            arguments?.getParcelableCompat<MovieDto>(MOVIE_KEY) ?: throw IllegalArgumentException()
        presenter.initData(movie)
    }

    override fun setUpTheaterData(movie: MovieDto) {
        binding.theaterRecyclerview.adapter = TheaterAdapter(
            movie.theaters.theaters,
            fun (item: MovieTheaterDto) {
                val intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra(MOVIE_KEY, movie)
                intent.putExtra(THEATER_KEY, item)
                dismiss()
                startActivity(intent)
            }
        )
    }

    companion object {
        const val THEATER_KEY = "theater_key"

        fun newInstance(item: MovieDto): TheaterSheetFragment {
            return TheaterSheetFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(HomeFragment.MOVIE_KEY, item)
                }
            }
        }
    }
}
