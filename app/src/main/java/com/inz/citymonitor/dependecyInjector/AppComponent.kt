package com.inz.citymonitor.dependecyInjector

import com.inz.citymonitor.data.rest.RetrofitRepository
import com.inz.citymonitor.data.rest.TokenInterceptor
import com.inz.citymonitor.dependecyInjector.modules.AppModule
import com.inz.citymonitor.dependecyInjector.modules.RestModule
import com.inz.citymonitor.presentation.activities.MainActivity
import com.inz.citymonitor.presentation.activities.MainViewModel
import com.inz.citymonitor.presentation.customViews.navigation.MenuAdapter
import com.inz.citymonitor.presentation.pages.account.AccountDetailsViewModel
import com.inz.citymonitor.presentation.pages.account.changePassword.ChangePasswordViewModel
import com.inz.citymonitor.presentation.pages.account.editProfile.EditProfileViewModel
import com.inz.citymonitor.presentation.pages.account.resetPassword.ResetPasswordViewModel
import com.inz.citymonitor.presentation.pages.history.HistoryViewModel
import com.inz.citymonitor.presentation.pages.history.historyDetails.HistoryDetailsViewModel
import com.inz.citymonitor.presentation.pages.map.MapFragment
import com.inz.citymonitor.presentation.pages.map.MapViewModel
import com.inz.citymonitor.presentation.pages.ranking.RankViewModel
import com.inz.citymonitor.presentation.pages.report.reportCreator.ReportCreatorViewModel
import com.inz.citymonitor.presentation.pages.signIn.SignInViewModel
import com.inz.citymonitor.presentation.pages.signUp.SignUpViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        RestModule::class
    ]
)

interface AppComponent {
    //Activities
    fun inject(into: MainActivity)

    fun inject(inti: MainViewModel)

    //retrofit
    fun inject(into: RetrofitRepository)

    fun inject(into: TokenInterceptor)

    //pages
    fun inject(into: SignUpViewModel)

    fun inject(into: SignInViewModel)

    fun inject(into: MenuAdapter)

    fun inject(into: AccountDetailsViewModel)

    fun inject(into:EditProfileViewModel)

    fun inject(into:ChangePasswordViewModel)

    fun inject(into:ResetPasswordViewModel)

    fun inject(into:HistoryViewModel)

    fun inject(into : HistoryDetailsViewModel)

    fun inject(into: RankViewModel)

    fun inject(into: MapViewModel)

    fun inject(into:ReportCreatorViewModel)

}