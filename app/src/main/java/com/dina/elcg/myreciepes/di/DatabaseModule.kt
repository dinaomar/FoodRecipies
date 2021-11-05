package com.dina.elcg.myreciepes.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dina.elcg.myreciepes.data.database.RecipesDatabase
import com.dina.elcg.myreciepes.util.Constants.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.modules.ApplicationContextModule
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RecipesDatabase =
        Room.databaseBuilder(context, RecipesDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDao(database: RecipesDatabase) = database.recipesDao()
}