package com.example.technicalassingmentturkcell

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class AppExecutors {
    private val mainThreadExecutor: MainThreadExecutor = MainThreadExecutor()
    private val diskOI = Executors.newSingleThreadExecutor()

    fun getDiskIO(): ExecutorService = diskOI
    fun getMainThreadExecutor() = mainThreadExecutor

   companion object {
        private var instance: AppExecutors? = null

        fun getExecutors(): AppExecutors {
            val tempInstance = instance
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = AppExecutors()
                this.instance = instance
                return instance
            }
        }
    }
}

class MainThreadExecutor : Executor {

    private val mainThreadHandler = Handler(Looper.getMainLooper())

    override fun execute(command: Runnable) {
        mainThreadHandler.post(command)
    }
}