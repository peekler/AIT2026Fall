package hu.bme.ait.todoapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.bme.ait.todoapp.data.AppDatabase
import hu.bme.ait.todoapp.data.TodoDAO


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideTodoDao(appDatabase: AppDatabase) : TodoDAO {
        return appDatabase.todoDao()
    }

    @Provides
    fun provideTodoAppDatabase(
        @ApplicationContext appContext: Context
    ) : AppDatabase {
        return AppDatabase.getDatabase(appContext)
    }

}