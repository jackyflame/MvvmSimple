package com.jf.orr.net.worker;


import com.jf.orr.log.LogW;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;

/**
 * Created by Android Studio.
 * ProjectName: vw
 * Author: yh
 * Date: 2019/3/17
 * Time: 15:05
 */

public class RetryWithDelay implements Function<Observable<? extends Throwable>, Observable<?>> {

    private final int maxRetries;
    private final int retryDelayMillis;
    private int retryCount;

    public RetryWithDelay(int maxRetries, int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
    }

    @Override
    public Observable<?> apply(final Observable<? extends Throwable> attempts) {
        return attempts.flatMap(new Function<Throwable, Observable<?>>() {
            @Override
            public Observable<?> apply(Throwable throwable) {
                if (++retryCount <= maxRetries) {
                    String errorMsg = throwable.getMessage();
                    LogW.e("get error :"+errorMsg+", it will try after " + retryDelayMillis  + " millisecond, retry count " + retryCount);
                    return Observable.timer(retryDelayMillis, TimeUnit.MILLISECONDS);
                }
                // Max retries hit. Just pass the error along.
                return Observable.error(throwable);
            }
        });
    }

}
