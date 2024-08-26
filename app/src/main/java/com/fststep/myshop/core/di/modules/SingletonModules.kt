/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.core.di.modules

import android.content.Context
import com.fststep.myshop.core.data.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Shubham Singh on 13/05/21.
 */

@Module
@InstallIn(SingletonComponent::class)
object SingletonModules {

    @Singleton
    @Provides
    fun providesPreferences(@ApplicationContext context: Context): Preferences {
        return Preferences(context)
    }
}
