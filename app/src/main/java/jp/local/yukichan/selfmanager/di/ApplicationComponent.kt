package jp.local.yukichan.selfmanager.di

import dagger.Component
import jp.local.yukichan.selfmanager.activities.MainActivity
import jp.local.yukichan.selfmanager.application.CustomApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: CustomApplication)

    fun inject(activity: MainActivity)
}

