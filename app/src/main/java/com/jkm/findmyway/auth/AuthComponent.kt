package com.jkm.findmyway.auth

import dagger.Component

@Component(modules = [AuthPresenterModule::class])
interface AuthComponent {
    fun inject(activity: AuthActivity)
}
