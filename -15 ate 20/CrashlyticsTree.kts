package com.example

import timber.log.Timber
import com.google.firebase.crashlytics.FirebaseCrashlytics

class CrashlyticsTree : Timber.Tree() {
    private val crashlytics = FirebaseCrashlytics.getInstance()

    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return priority >= android.util.Log.WARN // ajuste se quiser
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        // Envia mensagens como non-fatal
        crashlytics.log("$priority/$tag: $message")
        t?.let { crashlytics.recordException(it) }
    }
}

Timber.d("Fluxo inicializado")
Timber.e(exception, "Erro ao sincronizar dados")