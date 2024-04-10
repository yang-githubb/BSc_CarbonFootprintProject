package com.example.carbonfootprint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.TextViewHolder> {

    private final List<String> texts;

    public static class TextViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    public TextAdapter(List<String> texts) {
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
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }


}
