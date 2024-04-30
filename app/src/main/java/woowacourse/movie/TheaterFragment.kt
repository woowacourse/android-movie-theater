package woowacourse.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.FragmentTheaterBinding
import woowacourse.movie.list.adapter.TheaterAdapter
import woowacourse.movie.list.model.TheaterData.theaters

class TheaterFragment : Fragment() {
    private lateinit var binding: FragmentTheaterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
//        return super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentTheaterBinding.inflate(inflater, container, false)
        val movieId = arguments?.getLong("key")
        Log.d("얍", "$movieId")
        val recyclerView: RecyclerView = binding.movieRecyclerView
        recyclerView.adapter =
            TheaterAdapter(
                movieId = movieId ?: -1,
                theaters,
            )

        recyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }
}
