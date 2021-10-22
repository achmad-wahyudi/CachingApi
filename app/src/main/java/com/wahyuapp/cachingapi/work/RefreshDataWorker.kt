package com.wahyuapp.cachingapi.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.wahyuapp.cachingapi.database.SampleDatabase
import com.wahyuapp.cachingapi.database.UserRepository
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {

        //any task background
        val userDao = SampleDatabase.getInstance(applicationContext).userDAO()
        val repository = UserRepository(userDao)

        return try {
            repository.refreshUsers()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}