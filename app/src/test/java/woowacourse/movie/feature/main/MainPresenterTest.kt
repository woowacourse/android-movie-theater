package woowacourse.movie.feature.main

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

internal class MainPresenterTest {
    private lateinit var mainPresenter: MainContract.Presenter
    private lateinit var view: MainContract.View

    @Before
    fun init() {
        view = mockk() // 가짜 목 객체 생성
        mainPresenter = MainPresenter(view) // 목 객체를 활용해서 실제 테스트하고자 하는 프레젠터 테스트 객체 생성
    }

    @Test
    fun 영화_목록_탭을_클릭하면_영화_목록_화면으로_교체된다() {
        // 목 객체의 showMovieList() 메소드는 단순히 실행되기만 하는 메소드임을 정의
        every { view.showMoviePage() } just Runs

        // 실행
        mainPresenter.clickMovieTab()

        // 검사
        verify { view.showMoviePage() } // 뷰의 해당 메소드가 실행됐는지 검사한다
    }

    @Test
    fun 예약_목록_탭을_클릭하면_영화_목록_화면으로_교체된다() {
        // 목 객체의 showMovieList() 메소드는 단순히 실행되기만 하는 메소드임을 정의
        every { view.showReservationPage() } just Runs

        // 실행
        mainPresenter.clickReservationTab()

        // 검사
        verify { view.showReservationPage() } // 뷰의 해당 메소드가 실행됐는지 검사한다
    }

    @Test
    fun 설정_탭을_클릭하면_영화_목록_화면으로_교체된다() {
        // 목 객체의 showMovieList() 메소드는 단순히 실행되기만 하는 메소드임을 정의
        every { view.showSettingPage() } just Runs

        // 실행
        mainPresenter.clickSettingTab()

        // 검사
        verify { view.showSettingPage() } // 뷰의 해당 메소드가 실행됐는지 검사한다
    }

    @Test
    fun 알림_권한이_있으면_권한있음을_나타내는_토스트를_띄운다() {
        // 목 객체의 showMovieList() 메소드는 단순히 실행되기만 하는 메소드임을 정의
        every { view.showPermissionApproveMessage() } just Runs

        // 실행
        mainPresenter.permissionApproveResult(true)

        // 검사
        verify { view.showPermissionApproveMessage() } // 뷰의 해당 메소드가 실행됐는지 검사한다
    }

    @Test
    fun 알림_권한이_없으면_권한없음을_나타내는_토스트를_띄운다() {
        // 목 객체의 showMovieList() 메소드는 단순히 실행되기만 하는 메소드임을 정의
        every { view.showPermissionRejectMessage() } just Runs

        // 실행
        mainPresenter.permissionApproveResult(false)

        // 검사
        verify { view.showPermissionRejectMessage() } // 뷰의 해당 메소드가 실행됐는지 검사한다
    }
}
