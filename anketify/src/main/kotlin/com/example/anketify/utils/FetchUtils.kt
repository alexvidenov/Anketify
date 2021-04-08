package com.example.anketify.utils

import arrow.core.Option
import arrow.core.Try
import arrow.core.TryOf
import javax.transaction.Transactional

object FetchUtils {
    inline fun <T, S> getModelSafe(optional: Try<Option<T>>, onFound: (T) -> S, onNotFound: () -> S): S =
        optional.flatMap {
            it.fold<TryOf<S>>({
                return onNotFound()
            }, { m ->
                return onFound(m)
            })
        }.orThrow()
}