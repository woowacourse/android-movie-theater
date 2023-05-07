package woowacourse.movie.feature.reservationList

import com.example.domain.repository.TicketsRepository
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import org.junit.Before
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.feature.reservationList.itemModel.TicketsItemModel
import woowacourse.movie.model.MoneyState
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.TicketsState
import java.time.LocalDate
import java.time.LocalDateTime

internal class ReservationPresenterTest {
    private lateinit var view: ReservationListContract.View
    private lateinit var presenter: ReservationListContract.Presenter
    private lateinit var ticketsRepository: TicketsRepository

    @Before
    fun init() {
        view = mockk()
        ticketsRepository = mockk()
        presenter = ReservationPresenter(view, ticketsRepository)
    }

    @Test
    fun 예약목록을_불러와서_뷰의_아이템을_업데이트한다() {
        every { ticketsRepository.allTickets() } returns mockTicketsReservation
        val slot = slot<List<TicketsItemModel>>()
        every { view.updateItems(capture(slot)) } just Runs

        // 실행
        presenter.loadTicketsItemList()

        val actual = slot.captured.map { it.ticketsState }
        val expected = mockTicketsReservation
        assert(actual == expected)
    }

    private val mockTicketsReservation = listOf<TicketsState>(
        TicketsState(
            "선릉 극장",
            MovieState(
                R.drawable.ga_oh_galaxy_poster,
                1,
                "가디언즈 오브 갤럭시: Volume 3",
                LocalDate.of(2023, 5, 3),
                LocalDate.of(2023, 7, 20),
                150,
                "‘가모라’를 잃고 슬픔에 빠져 있던 ‘피터 퀼’이 위기에 처한 은하계와 동료를 지키기 위해 다시 한번 가디언즈 팀과 힘을 모으고, 성공하지 못할 경우 그들의 마지막이 될지도 모르는 미션에 나서는 이야기"
            ),
            LocalDateTime.now(),
            MoneyState(10000),
            listOf()
        ),
        TicketsState(
            "강남 극장",
            MovieState(
                R.drawable.imitation_game_poster,
                2,
                "이미테이션 게임",
                LocalDate.of(2023, 5, 1),
                LocalDate.of(2023, 8, 20),
                114,
                "매 순간 3명이 죽는 사상 최악의 위기에 처한 제 2차 세계대전. 절대 해독이 불가능한 암호 ‘에니그마’로 인해 연합군은 속수무책으로 당하게 된다. 결국 각 분야의 수재들을 모아 기밀 프로젝트 암호 해독팀을 가동한다. 천재 수학자 앨런 튜링(베네딕트 컴버배치)은 암호 해독을 위한 특별한 기계를 발명하지만 24시간 마다 바뀌는 완벽한 암호 체계 때문에 번번히 좌절하고 마는데... 과연, 앨런 튜링과 암호 해독팀은 암호를 풀고 전쟁의 승리를 끌어낼 수 있을까…?"
            ),
            LocalDateTime.now(),
            MoneyState(13000),
            listOf()
        )
    )
}
