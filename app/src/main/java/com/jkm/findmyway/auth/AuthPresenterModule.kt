package com.jkm.findmyway.auth

import android.app.Activity
import dagger.Module
import dagger.Provides
import java.lang.ref.WeakReference

@Module
class AuthPresenterModule(
    private val activity: WeakReference<Activity>,
    private val view: AuthContract.View
) {
    @Provides
    fun provideAuthActivity(): WeakReference<Activity> = activity

    @Provides
    fun provideAuthView(): AuthContract.View = view
}
