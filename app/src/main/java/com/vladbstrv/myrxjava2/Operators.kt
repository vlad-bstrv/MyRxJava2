package com.vladbstrv.myrxjava2

import android.util.Log
import io.reactivex.rxjava3.core.Observable

class Operators {

    fun exec() {
        Consumer(Producer()).exec()
    }

    class Producer {
        fun createJust() = Observable.just("1", "2", "3", "3")
        fun createJust2() = Observable.just("4", "5", "6")
    }

    class Consumer(val producer: Producer) {
        fun exec() {
            execMerge()
        }

        fun execTake() {
            producer.createJust()
                .skip(2)
                .subscribe({ s ->
                    Log.d(TAG, "onNext: $s")
                }, {
                    Log.d(TAG, "onError: ${it.message}")
                })
        }

        fun execMap() {
            producer.createJust()
                .map { it + it }
                .subscribe({ s ->
                    Log.d(TAG, "onNext: $s")
                }, {
                    Log.d(TAG, "onError: ${it.message}")
                })
        }

        fun execDistinct() {
            producer.createJust()
                .distinct()
                .subscribe({ s ->
                    Log.d(TAG, "onNext: $s")
                }, {
                    Log.d(TAG, "onError: ${it.message}")
                })
        }

        fun execFilter() {
            producer.createJust()
                .filter() { it.toInt() > 1 }
                .subscribe({ s ->
                    Log.d(TAG, "onNext: $s")
                }, {
                    Log.d(TAG, "onError: ${it.message}")
                })
        }

        fun execMerge() {
            producer.createJust()
                .mergeWith(producer.createJust2())
                .subscribe({ s ->
                    Log.d(TAG, "onNext: $s")
                }, {
                    Log.d(TAG, "onError: ${it.message}")
                })
        }

    }

    companion object {
        const val TAG = "TAG"
    }

}