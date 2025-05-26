package com.example.pr6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private Context context;

    private ArrayList<Book> bookArrayList;

    public RecycleViewAdapter(Context context, ArrayList<Book> bookArrayList){
        this.context = context;
        this.bookArrayList = bookArrayList;
    }

    @NonNull
    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return bookArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView bookName, bookAuthor;

        public  ViewHolder(@NonNull View itemView){
            super(itemView);
            bookName = itemView.findViewById(R.id.b_name);
            bookAuthor = itemView.findViewById(R.id.b_author);

        }
    }

    public interface OnBookClickListener {
        void onBookClick(int bookId);
        void onBookDelete(int bookId);
    }

    private OnBookClickListener listener;

    public RecycleViewAdapter(Context context, ArrayList<Book> bookList, OnBookClickListener listener) {
        this.context = context;
        this.bookArrayList = bookList;
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = bookArrayList.get(position);
        holder.bookName.setText(book.getBook_name());
        holder.bookAuthor.setText(book.getBook_author());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBookClick(book.getID_Book());
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onBookDelete(book.getID_Book());
            }
            return true;
        });
    }
}
