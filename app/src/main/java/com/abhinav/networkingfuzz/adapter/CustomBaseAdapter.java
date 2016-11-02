package com.abhinav.networkingfuzz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.abhinav.networkingfuzz.R;
import com.abhinav.networkingfuzz.model.Book;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by abhinav.sharma on 11/1/2016.
 */
public class CustomBaseAdapter extends BaseAdapter {

    private Context mContext;
    private List<Book> books;
    private LayoutInflater inflater;

    public CustomBaseAdapter(Context mContext, List<Book> books) {
        this.mContext = mContext;
        this.books = books;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Book getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view;

        if (convertView == null) {
            view = inflater.inflate(R.layout.single_grid_item, parent, false);
        } else view = convertView;

        Picasso.with(mContext)
                .load(getItem(position)
                .getImage())
                .fit()
                .centerInside()
                .into((ImageView) view.findViewById(R.id.image_view));

        view.findViewById(R.id.image_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, books.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
