package com.ipcoding.einkaufsliste.di

import android.app.Application
import androidx.room.Room
import com.ipcoding.einkaufsliste.feature_item.data.data_source.ItemDatabase
import com.ipcoding.einkaufsliste.feature_item.data.repository.ItemRepositoryImpl
import com.ipcoding.einkaufsliste.feature_item.domain.repository.ItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideItemDatabase(app: Application): ItemDatabase {
        return Room.databaseBuilder(
            app,
            ItemDatabase::class.java,
            ItemDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideItemRepository(db: ItemDatabase): ItemRepository {
        return ItemRepositoryImpl(db.itemDao)
    }
}