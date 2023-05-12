package com.woowacourse.data.datasource.movie

import com.woowacourse.data.R
import com.woowacourse.data.model.DataMovie
import java.time.LocalDate

class LocalMovieDataSource : MovieDataSource.Local {
    private var loadedSize = 0
    private val dummy: List<DataMovie> = List(10_000) { id ->
        DataMovie(
            id,
            "해리 포터와 마법사의 돌 $id",
            LocalDate.of(2023, 4, 1),
            LocalDate.of(2023, 4, 30),
            152,
            "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
            R.drawable.img_sample_movie_thumbnail1
        )
    }

    override fun getMovies(size: Int): List<DataMovie> = provideDummy(size)

    private fun provideDummy(loadSize: Int): List<DataMovie> {
        val temp: MutableList<DataMovie> = mutableListOf()

        while (temp.size < loadSize) {
            if (isAllLoaded()) return temp
            addDummy(temp)
        }
        return temp
    }

    private fun isAllLoaded() = loadedSize >= dummy.size

    private fun addDummy(tempDummy: MutableList<DataMovie>) {
        tempDummy.add(dummy[loadedSize])
        loadedSize++
    }
}
