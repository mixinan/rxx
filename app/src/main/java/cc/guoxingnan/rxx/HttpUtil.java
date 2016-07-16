package cc.guoxingnan.rxx;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/7/16.
 */
public class HttpUtil {
    private OkHttpClient client;

    public HttpUtil() {
        client = new OkHttpClient();
    }

    public Observable<byte[]> download(final String path){

        return Observable.create(new Observable.OnSubscribe<byte[]>() {
            @Override
            public void call(final Subscriber<? super byte[]> subscriber) {
                Request request = new Request.Builder().url(path).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()){
                            byte[] data = response.body().bytes();
                            subscriber.onNext(data);
                        }
                        subscriber.onCompleted();
                    }
                });
            }
        });
    }
}
