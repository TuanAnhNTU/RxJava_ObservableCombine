package vn.edu.ntu.tuananh.rxjava_observablecombine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding3.widget.RxTextView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity
{
    EditText edtStr1, edtStr2;
    TextView txtKQ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtStr1 = findViewById(R.id.edtString1);
        edtStr2 = findViewById(R.id.edtString2);
        txtKQ = findViewById(R.id.txtCombine);

//        Observable<String> in1 = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                emitter.onNext(edtStr1.getText().toString());
//                emitter.onComplete();
//            }
//        });

//        Observable<String> in2 = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                emitter.onNext(edtStr2.getText().toString());
//                emitter.onComplete();
//            }
//        });

        Observable<CharSequence> input1 = RxTextView.textChanges(edtStr1);
        Observable<CharSequence> input2 = RxTextView.textChanges(edtStr2);
        Observable<String> combine = Observable.combineLatest(
                input1.retry(3).onErrorReturn(e-> "Lỗi xảy ra"),
                input2.retry(3).onErrorReturn(e-> "Lỗi xảy ra"),
                (st1, st2) -> st1 + " " + st2);
        combine.map(str ->str.toString().toUpperCase())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(str -> txtKQ.setText(str),
                        err-> Log.d("Lỗi", err.toString()));

    }
}
