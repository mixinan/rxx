package cc.guoxingnan.rxx;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.AndroidCharacter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private Button bt;
    private ImageView iv;
    private HttpUtil util;

    public static String PATH = "http://www.77mv.com/uploads/allimg/c151025/1445M15H2P40-12224.jpg";
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        util = new HttpUtil();
        bt = (Button) findViewById(R.id.bt);
        iv = (ImageView) findViewById(R.id.iv);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.download(PATH)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<byte[]>() {
                            @Override
                            public void onCompleted() {
                                Log.i(TAG, "onCompleted: ");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError: "+e.getMessage());
                            }

                            @Override
                            public void onNext(byte[] bytes) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                iv.setImageBitmap(bitmap);
                            }
                        });
            }
        });
    }
}
