package woowacourse.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.FragmentTheaterBinding
import woowacourse.movie.list.adapter.TheaterAdapter
import woowacourse.movie.list.model.TheaterData.theaters
import woowacourse.movie.list.view.MovieListActivity.Companion.EXTRA_MOVIE_ID_KEY_TO_FRAGMENT
import woowacourse.movie.reservation.view.MovieReservationActivity

class TheaterFragment : DialogFragment() {
    private lateinit var binding: FragmentTheaterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentTheaterBinding.inflate(inflater, container, false)
        val movieId = arguments?.getLong(EXTRA_MOVIE_ID_KEY_TO_FRAGMENT)
        val recyclerView: RecyclerView = binding.theaterRecyclerView

        val theaterAdapter =
            TheaterAdapter(
                movieId = movieId ?: -99,
                theaters,
            )
        recyclerView.adapter = theaterAdapter

        theaterAdapter.setItemClickListener(
            object : TheaterAdapter.OnItemClickListener {
                override fun onClick(
                    movieId: Long,
                    theaterId: Long,
                ) {
                    val intent = Intent(activity, MovieReservationActivity::class.java)
                    intent.putExtra("movie_id_key", movieId)
                    intent.putExtra("theater_id_key", theaterId)
                    startActivity(intent)
                }
            },
        )

        recyclerView.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    companion object {
        const val EXTRA_THEATER_ID_KEY = "theater_id_key"
    }
}
