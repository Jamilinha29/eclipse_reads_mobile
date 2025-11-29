package com.example.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

/**
 * Exemplo de Worker que sincroniza dados locais com a API.
 * Integre com Room/Retrofit conforme seu repositório.
 */
class SyncWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        return try {
            // Exemplo: pegar dados pendentes da DB e enviar para API
            // val pending = localDb.myDao().getPending()
            // api.sendBatch(pending)
            // marcar como sincronizado na DB
            // Simulação:
            delay(1000) // simula I/O
            Result.success()
        } catch (e: Exception) {
            // Pode retornar retry se for recuperável
            Result.retry()
        }
    }

    companion object {
        private const val UNIQUE_WORK_NAME = "OfflineSyncPeriodic"

        fun schedulePeriodicSync(context: Context) {
            val workRequest = PeriodicWorkRequestBuilder<SyncWorker>(1, TimeUnit.HOURS)
                .setInitialDelay(5, TimeUnit.MINUTES)
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                UNIQUE_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
        }
    }
}
