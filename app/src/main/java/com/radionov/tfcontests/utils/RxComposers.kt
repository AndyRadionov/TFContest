package com.radionov.tfcontests.utils

import io.reactivex.*

/**
 * @author Andrey Radionov
 */

class RxComposers(private val subscribeScheduler: Scheduler,
                  private val observeScheduler: Scheduler) {

    fun getCompletableComposer(): CompletableTransformer {
        return CompletableTransformer { upstream ->
            upstream
                    .subscribeOn(subscribeScheduler)
                    .observeOn(observeScheduler)
        }
    }

    fun <T> getSingleComposer(): SingleTransformer<T, T> {
        return SingleTransformer { single ->
            single
                    .subscribeOn(subscribeScheduler)
                    .observeOn(observeScheduler)
        }
    }

    fun <T> getObservableComposer(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable
                    .subscribeOn(subscribeScheduler)
                    .observeOn(observeScheduler)
        }
    }
}
