package io.seph.test

import android.app.Application
import io.seph.data.di.dataMapperModule
import io.seph.data.di.dataSourceModule
import io.seph.data.di.databaseModule
import io.seph.data.di.repositoryModule
import io.seph.domain.di.domainModule
import io.seph.presentation.di.mapperUiModule
import io.seph.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

/**
 *  [SephTestApp] is the main application class for the SephTest application.
 *
 *  It is responsible for initializing Koin for dependency injection.
 *
 *  @see Application
 */
class SephTestApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@SephTestApp)
            modules(
                listOf(
                    dataMapperModule,
                    dataSourceModule,
                    repositoryModule,
                    databaseModule,
                    domainModule,
                    mapperUiModule,
                    viewModelModule
                )
            )
        }
    }
}