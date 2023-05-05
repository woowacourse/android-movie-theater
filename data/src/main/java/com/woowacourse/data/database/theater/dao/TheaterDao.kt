package com.woowacourse.data.database.theater.dao

import android.content.Context
import com.woowacourse.data.model.DataMovieDate
import com.woowacourse.data.model.DataMovieTime
import com.woowacourse.data.model.DataTheater

class TheaterDao(context: Context) {
    // TODO(데이터베이스에서 가져오도록 변경)
    // private val database = TheaterDatabase(context)

    fun getAllByMovieId(id: Int, date: DataMovieDate): List<DataTheater> = listOf(
        DataTheater(
            id = 0,
            movieId = id,
            name = "CGV 강남",
            date = date,
            runningTimes = listOf(
                DataMovieTime(10, 0),
                DataMovieTime(12, 0),
                DataMovieTime(14, 0),
            )
        ),
        DataTheater(
            id = 1,
            movieId = id,
            name = "CGV 역삼",
            date = date,
            runningTimes = listOf(
                DataMovieTime(10, 0),
                DataMovieTime(13, 30),
                DataMovieTime(15, 0),
                DataMovieTime(16, 0),
                DataMovieTime(17, 0),
            )
        ),
        DataTheater(
            id = 2,
            movieId = id,
            name = "CGV 행신",
            date = date,
            runningTimes = listOf(
                DataMovieTime(10, 0),
                DataMovieTime(12, 0),
                DataMovieTime(14, 30),
                DataMovieTime(16, 50),
                DataMovieTime(22, 0),
                DataMovieTime(22, 0),
            )
        ),
        DataTheater(
            id = 3,
            movieId = id,
            name = "완전긴영화제목이지롱엘맆시스로짤려랏",
            date = date,
            runningTimes = listOf(
                DataMovieTime(10, 0),
                DataMovieTime(12, 0),
                DataMovieTime(14, 30),
                DataMovieTime(16, 50),
                DataMovieTime(22, 0),
                DataMovieTime(22, 0),
            )
        )
    )
}
