package com.xoshop;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private String TAG = "Test";
    private Disposable mDisposable;

    @Test
    public void testRxJava() throws Exception {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.i(TAG, "监听到了订阅开始发送");
                e.onNext("1");
                e.onNext("2");
                e.onNext("3");
                e.onNext("4");
                e.onNext("5");
                e.onComplete();

            }
        }).subscribe(new Observer<String>() {
            Disposable disposable;
            int i = 0;

            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "进行了订阅");
                disposable = d;

            }

            @Override
            public void onNext(String value) {
                Log.i(TAG, "接受到的值为---》" + value);
                if (i == 2) {
                    disposable.dispose();
                }
                i++;

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.i(TAG, "接受完毕");
            }
        });
    }

    @Test
    public void testThreadInRx() throws Exception {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.i(TAG, "监听到了订阅开始发送");
                Log.i(TAG, "当前线程为-->" + Thread.currentThread().getName());
                e.onNext(1);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(TAG, "主线程的时候的id" + Thread.currentThread().getName());

            }
        }).observeOn(Schedulers.io()).
                subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "io的时候的线程为--->" + Thread.currentThread().getName());
                    }
                });
    }


    @Test
    public void testMapInRxJAVA() throws Exception {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.i(TAG,"准备要发送1");
                e.onNext(1);
                Log.i(TAG, "已发送1");
                Log.i(TAG,"准备要发送2");
                e.onNext(2);
                Log.i(TAG, "已发送2");
                Log.i(TAG,"准备要发送3");
                e.onNext(3);
                Log.i(TAG, "已发送3");
                Log.i(TAG,"准备要发送4");
                e.onNext(4);
                Log.i(TAG, "已发送4");
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                String data = String.valueOf(integer);
                Log.i(TAG,"通过map处理过的数据为--->"+integer);
                return data;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
              Log.i(TAG,"接受的值为--->"+s);
            }
        });
    }

    @Test
    public void testFlatMap() throws Exception {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);

            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> arrayStrings = new ArrayList<String>();
                for(int i = 0 ;i < 5; i++){
                    arrayStrings.add("i-->"+i+"integer--->"+integer);
                }
                return Observable.fromIterable(arrayStrings).delay(10, TimeUnit.MICROSECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i(TAG,"观察者接受到的值为-->"+s);
            }
        });
    }

    @Test
    public void testContactMap() throws Exception {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);

            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> arrayString = new ArrayList<String>();
                for(int i = 0;i< 5; i++){
                    arrayString.add("i--->"+i+"integer---->"+integer);
                }
                return Observable.fromIterable(arrayString).delay(10,TimeUnit.MICROSECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i(TAG,"观察者接受到的值为--->"+s);
            }
        });
    }

    @Test
    public void testJust() throws Exception {
       Observable.just(1,2,3,4,5,"2").cast(Integer.class).subscribe(data ->{
           Log.i(TAG,"onNext-->"+data);
       },data->{
           Log.i(TAG,"EXCEPTION--->"+data.getMessage());
       });
     }
}
