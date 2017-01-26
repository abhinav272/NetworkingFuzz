package com.abhinav.networkingfuzz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.abhinav.networkingfuzz.adapter.CustomBaseAdapter;
import com.abhinav.networkingfuzz.model.Book;
import com.abhinav.networkingfuzz.model.Library;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private TextView title;
    private GridView gridView;
    private ProgressBar progressBar;
    private CustomBaseAdapter customBaseAdapter;
    private List<Book> loadedBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        hitAPI();
    }

    private void setupViews() {
        title = (TextView) findViewById(R.id.tv_title);
        gridView = (GridView) findViewById(R.id.grid_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setIndeterminate(true);
        loadedBooks = new ArrayList<>();
        customBaseAdapter = new CustomBaseAdapter(this, loadedBooks);
        gridView.setAdapter(customBaseAdapter);

        hitAPI();
    }

    private void hitAPI() {
        observeLibrary().flatMap(new Func1<Library, Observable<Book>>() {

            @Override
            public Observable<Book> call(Library library) {
                return Observable.from(library.getBooks()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        }).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Book>() {
            @Override
            public void onCompleted() {
                Toast.makeText(MainActivity.this, "Yo All books loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Log.e(MainActivity.class.getSimpleName(), "onError: ", e);
            }

            @Override
            public void onNext(Book book) {
                System.out.println(book.getTitle());
                updateUi(book);
            }
        });
    }

    private void updateUi(Book b) {
        gridView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        loadedBooks.add(b);
        customBaseAdapter.notifyDataSetChanged();
    }

    private Observable<Library> observeLibrary(){
        return RetrofitService.getRetrofitService().getLibrary();
    }
}
