package com.ipcoding.einkaufsliste.di

import android.app.Application
import androidx.room.Room
import com.ipcoding.einkaufsliste.feature_item.data.data_source.ItemDatabase
import com.ipcoding.einkaufsliste.feature_item.data.repository.ItemRepositoryImpl
import com.ipcoding.einkaufsliste.feature_item.domain.repository.ItemRepository
import com.ipcoding.einkaufsliste.feature_item.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): ItemDatabase {
        return Room.inMemoryDatabaseBuilder(
            app,
            ItemDatabase::class.java,
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: ItemDatabase): ItemRepository {
        return ItemRepositoryImpl(db.itemDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: ItemRepository): ItemUseCases {
        return ItemUseCases(
            getItems = GetItems(repository),
            deleteItem = DeleteItem(repository),
            addItem = AddItem(repository),
            getItem = GetItem(repository),
            changeColorItem = ChangeColorItem(repository)
        )
    }
}