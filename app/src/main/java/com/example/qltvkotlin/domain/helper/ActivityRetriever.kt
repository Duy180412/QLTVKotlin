package com.example.qltvkotlin.domain.helper

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.qltvkotlin.presentation.extension.cast

class ActivityRetriever(application: Application) :
    Application.ActivityLifecycleCallbacks {
    private val mActivities = arrayListOf<Activity>()

    init {
        application.registerActivityLifecycleCallbacks(this)
    }

    operator fun invoke(): FragmentActivity {
        return mActivities.lastOrNull().cast<FragmentActivity>() ?: error("Khong tim thay")
    }

    companion object {
        lateinit var shared: ActivityRetriever
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        mActivities.add(activity)
    }

    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityResumed(activity: Activity) {}
    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {
        mActivities.remove(activity)
    }
}