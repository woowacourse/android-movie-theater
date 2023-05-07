package woowacourse.movie.feature.movieList

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.data.AdvRepository
import woowacourse.movie.data.MovieRepository
import woowacourse.movie.feature.common.itemModel.CommonItemModel
import woowacourse.movie.feature.movieList.itemModel.AdvItemModel
import woowacourse.movie.feature.movieList.itemModel.MovieItemModel
import woowacourse.movie.model.AdvState
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.TheaterMovieState
import java.time.LocalDate
import java.time.LocalTime

internal class MoviesPresenterTest {
    private lateinit var view: MovieListContract.View
    private lateinit var presenter: MovieListContract.Presenter
    private lateinit var movieRepository: MovieRepository
    private lateinit var advRepository: AdvRepository

    @Before
    fun init() {
        view = mockk()
        movieRepository = mockk()
        advRepository = mockk()
        presenter = MoviesPresenter(view, movieRepository, advRepository)
    }

    @Test
    fun 영화와_광고_아이템_목록을_불러들이고_영화3개당_광고1개가_나오도록_뷰에_설정한다() {
        every { movieRepository.allMovies() } returns mockMovies // 레포지토리가 목 영화목록을 반환하도록 정의
        every { advRepository.allAdv() } returns mockAdvs // 목 광고목록을 반환하도록 정의

        val itemsSlot = slot<List<CommonItemModel>>()
        every { view.updateItems(capture(itemsSlot)) } just Runs

        // 실행
        presenter.loadMovieAndAdvItemList()

        // 뷰에 설정되는 메소드가 호출됐는지 검사
        verify { view.updateItems(any()) }
        // 캡쳐한 내용 검사
        val actual = itemsSlot.captured
        val advIndexed = (3 until actual.size step 4)
        actual.forEachIndexed { index, commonItemModel ->
            if (index in advIndexed) {
                assert(commonItemModel is AdvItemModel)
            } else {
                assert(commonItemModel is MovieItemModel)
            }
        }
    }

    @Test
    fun 바텀시트에서_극장이_선택되면_영화_액티비티로_이동한다() {
        every { view.navigateMovieDetail(any()) } just Runs // 뷰의 영화_상세_액티비티 이동 메소드 정의

        presenter.receiveTheaterInfo(mockTheater) // 실행. 여기서 가짜 객체로 실행

        verify { view.navigateMovieDetail(mockTheater) } // 실행됐는지 여부 검사
    }

    private val mockMovies = listOf<MovieState>(
        MovieState(
            R.drawable.slamdunk_poster,
            0,
            "더 퍼스트 슬램덩크",
            LocalDate.of(2023, 4, 29),
            LocalDate.of(2023, 5, 20),
            124,
            "북산고 농구부는 전국 대회에 출전해 라이벌 산왕공고와 맞붙는다. 멤버 각자가 쌓아온 성과, 그들이 짊어진 과거, 다양한 생각들이 뜨거운 코트 위에서 다시 한번 격렬하게 충돌한다." +
                "북산고 농구부는 전국 대회에 출전해 라이벌 산왕공고와 맞붙는다. 멤버 각자가 쌓아온 성과, 그들이 짊어진 과거, 다양한 생각들이 뜨거운 코트 위에서 다시 한번 격렬하게 충돌한다." +
                "북산고 농구부는 전국 대회에 출전해 라이벌 산왕공고와 맞붙는다. 멤버 각자가 쌓아온 성과, 그들이 짊어진 과거, 다양한 생각들이 뜨거운 코트 위에서 다시 한번 격렬하게 충돌한다."
        ),
        MovieState(
            R.drawable.ga_oh_galaxy_poster,
            1,
            "가디언즈 오브 갤럭시: Volume 3",
            LocalDate.of(2023, 5, 3),
            LocalDate.of(2023, 7, 20),
            150,
            "‘가모라’를 잃고 슬픔에 빠져 있던 ‘피터 퀼’이 위기에 처한 은하계와 동료를 지키기 위해 다시 한번 가디언즈 팀과 힘을 모으고, 성공하지 못할 경우 그들의 마지막이 될지도 모르는 미션에 나서는 이야기"
        ),
        MovieState(
            R.drawable.imitation_game_poster,
            2,
            "이미테이션 게임",
            LocalDate.of(2023, 5, 1),
            LocalDate.of(2023, 8, 20),
            114,
            "매 순간 3명이 죽는 사상 최악의 위기에 처한 제 2차 세계대전. 절대 해독이 불가능한 암호 ‘에니그마’로 인해 연합군은 속수무책으로 당하게 된다. 결국 각 분야의 수재들을 모아 기밀 프로젝트 암호 해독팀을 가동한다. 천재 수학자 앨런 튜링(베네딕트 컴버배치)은 암호 해독을 위한 특별한 기계를 발명하지만 24시간 마다 바뀌는 완벽한 암호 체계 때문에 번번히 좌절하고 마는데... 과연, 앨런 튜링과 암호 해독팀은 암호를 풀고 전쟁의 승리를 끌어낼 수 있을까…?"
        ),
        MovieState(
            R.drawable.slamdunk_poster,
            0,
            "더 퍼스트 슬램덩크",
            LocalDate.of(2023, 4, 29),
            LocalDate.of(2023, 5, 20),
            124,
            "북산고 농구부는 전국 대회에 출전해 라이벌 산왕공고와 맞붙는다. 멤버 각자가 쌓아온 성과, 그들이 짊어진 과거, 다양한 생각들이 뜨거운 코트 위에서 다시 한번 격렬하게 충돌한다." +
                "북산고 농구부는 전국 대회에 출전해 라이벌 산왕공고와 맞붙는다. 멤버 각자가 쌓아온 성과, 그들이 짊어진 과거, 다양한 생각들이 뜨거운 코트 위에서 다시 한번 격렬하게 충돌한다." +
                "북산고 농구부는 전국 대회에 출전해 라이벌 산왕공고와 맞붙는다. 멤버 각자가 쌓아온 성과, 그들이 짊어진 과거, 다양한 생각들이 뜨거운 코트 위에서 다시 한번 격렬하게 충돌한다."
        ),
        MovieState(
            R.drawable.ga_oh_galaxy_poster,
            1,
            "가디언즈 오브 갤럭시: Volume 3",
            LocalDate.of(2023, 5, 3),
            LocalDate.of(2023, 7, 20),
            150,
            "‘가모라’를 잃고 슬픔에 빠져 있던 ‘피터 퀼’이 위기에 처한 은하계와 동료를 지키기 위해 다시 한번 가디언즈 팀과 힘을 모으고, 성공하지 못할 경우 그들의 마지막이 될지도 모르는 미션에 나서는 이야기"
        ),
        MovieState(
            R.drawable.imitation_game_poster,
            2,
            "이미테이션 게임",
            LocalDate.of(2023, 5, 1),
            LocalDate.of(2023, 8, 20),
            114,
            "매 순간 3명이 죽는 사상 최악의 위기에 처한 제 2차 세계대전. 절대 해독이 불가능한 암호 ‘에니그마’로 인해 연합군은 속수무책으로 당하게 된다. 결국 각 분야의 수재들을 모아 기밀 프로젝트 암호 해독팀을 가동한다. 천재 수학자 앨런 튜링(베네딕트 컴버배치)은 암호 해독을 위한 특별한 기계를 발명하지만 24시간 마다 바뀌는 완벽한 암호 체계 때문에 번번히 좌절하고 마는데... 과연, 앨런 튜링과 암호 해독팀은 암호를 풀고 전쟁의 승리를 끌어낼 수 있을까…?"
        )
    )

    private val mockAdvs = listOf<AdvState>(
        AdvState(
            R.drawable.adv_wooteco,
            "배달의민족 운영사인 우아한형제들에서 운영하는 개발자 교육 프로그램.\n" +
                "\n" +
                "NHN NEXT 교수 출신인 박재성(자바지기)의 주도로 설립되었으며, 삼성 청년 SW 아카데미와 같이 실질적으로는 무료로 교육을 제공하나, 명목상으로 각 레벨당 100만원의 강의료가 책정되어 있으며 중도 포기시 납부해야 한다.\n" +
                "\n" +
                "운영 비용은 기존에는 우아한형제들 측에서 전액 부담하였으나 2021년부터 K-Digital Training 사업의 지원을 받아 일정 부분 국비 지원을 받고 있다.\n" +
                "\n" +
                "프론트, 백엔드, 모바일 안드로이드 교육 과정이 있다.\n" +
                "\n" +
                "삼성 청년 SW 아카데미 (SAFFY) 와 차별화되는 점은 매년 전국에서 50명 내외(백엔드 기준, 3기 기준 프론트엔드는 절반인 25명)를 선발하여 운영하는 소수정예식이라는 점 이다. 3기 기준으로 경쟁률이 약 20:1 가량으로 알려져있다. 4기 (2022년) 부터 모집인원이 2배로 증가하여 백엔드 기준 100명 내외, 프론트엔드 기준 50명 내외로 선발 인원이 확대되었다.\n" +
                "\n" +
                "수료자들중 약 40%는 우아한형제들 입사하고, 나머지 인원은 대부분 네카라쿠배로 일컬어지는 IT 대기업이나 유수의 스타트업에 입사한다."
        )
    )

    private val mockTheater = TheaterMovieState(
        "선릉 극장",
        mockMovies[0],
        listOf(LocalTime.of(10, 0), LocalTime.of(12, 0))
    )
}
