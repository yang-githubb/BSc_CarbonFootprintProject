package com.example.carbonfootprint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.TextViewHolder> {

    private final List<String> texts;
    private final Context context;

    public static class TextViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    public TextAdapter(Context context, List<String> texts) {
        this.context = context;
        this.texts = texts;
    }

    @NonNull
    @Override
    public TextAdapter.TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new TextViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TextViewHolder holder, int position) {
        int actualPosition = position % texts.size();
        holder.textView.setText(texts.get(actualPosition));

        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorItemBackground2));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }
    }


    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }


}
