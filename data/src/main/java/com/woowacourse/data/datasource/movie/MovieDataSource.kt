package com.woowacourse.data.datasource.movie

import com.woowacourse.data.model.DataMovie

interface MovieDataSource {
    interface Local {
        fun getMovies(size: Int): List<DataMovie>
    }
}
