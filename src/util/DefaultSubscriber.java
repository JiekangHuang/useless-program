package util;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class DefaultSubscriber<T> implements Subscriber<T> {
    // 用來和發布者互動的物件
    private Subscription subscription;
    // 資料處理器
    private DataHandler<T> handler;

    public DefaultSubscriber(DataHandler<T> handler) {
        this.handler = handler;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        // 記錄subscription物件
        this.subscription = subscription;
        // 向發布者取得第一筆資料
        this.subscription.request(1);
    }

    @Override
    public void onNext(T item) {
        // 將資料轉交handler處理
        this.handler.handle(item);
        // 向發布者取得下一筆資料
        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        // 不該執行到這裡
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        // 不該執行到這裡
        System.out.println("MySubscriber onComplete");
    }

    /**
     * 實際負責處理資料
     */
    @FunctionalInterface
    public interface DataHandler<T> {
        public void handle(T item);
    }
}