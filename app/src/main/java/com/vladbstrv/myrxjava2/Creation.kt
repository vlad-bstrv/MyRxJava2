package com.vladbstrv.myrxjava2

import android.util.Log
import io.reactivex.rxjava3.core.Observable
import java.lang.RuntimeException
import kotlin.random.Random

class Creation {

    fun exec() {
        Consumer(Producer()).exec()
    }

    /**Observable «генерирует» некоторый поток данных и уведомляет об
    этом либо о каком-либо другом событии подписчика Observer */
    class Producer {
        //Создаем Observable(поток) разными способами
        fun randomResultOperation(): Boolean {
            Thread.sleep(Random.nextLong(1000))
            return listOf(true, false, true)[Random.nextInt(2)]
        }
        fun create() = Observable.create<String>{ emitter ->
            try {
                for(i in 0..10) {
                    randomResultOperation().let {
                        if(it) {
                            emitter.onNext("Success $i")
                        } else {
                            emitter.onError(RuntimeException("Error123"))
                            return@create
                        }
                    }
                }
                emitter.onComplete()
            } catch (t: Throwable) {
                emitter.onError(RuntimeException("Error"))
            }
        }

    }

    /**Observer — интерфейс.
    Реализация интерфейса Observer — класс, объект которого лишь «подписывается» на
    Observable */
    class Consumer(private val producer: Producer) {

        private fun execLambda() {
            producer.create()
                .subscribe({
                    Log.d(TAG, "onNext: $it")
                }, {
                    Log.d(TAG, "onError: ${it.message}")
                }, {
                    Log.d(TAG, "onComplete")
                })
        }
        //подписываемся на Observable
        fun exec() {
            execLambda()
        }
    }

    companion object {
        const val TAG = "TAG"
    }
}