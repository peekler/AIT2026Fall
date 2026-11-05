package hu.ait.hiltdemo.analytics

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import hu.ait.hiltdemo.Detail
import hu.ait.hiltdemo.analytics.AnalyticsEngine
import hu.ait.hiltdemo.analytics.RealAnalyitics
import hu.ait.hiltdemo.logging.LogService
import hu.ait.hiltdemo.logging.MainLogger
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BmeDetail

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ElteDetail

@Module
@InstallIn(SingletonComponent::class)
object DetailModule {

    @BmeDetail
    @Provides
    @Singleton
    fun provideDetailBme(
    ): Detail {
        return Detail()
    }

    @ElteDetail
    @Provides
    @Singleton
    fun provideDetailElte(
    ): Detail {
        return Detail()
    }

}