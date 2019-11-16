package com.wak.refresh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wak.refresh.R;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<String> list = new ArrayList<>();

    public TestAdapter(Context context) {
        SoftReference<Context> softReference = new SoftReference<>(context);
        this.context = softReference.get();
        for (int i = 0; i < 30; i++) {
            list.add(String.valueOf(i));
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_base, parent, false);
        TestViewHolder testViewHolder = new TestViewHolder(view);
        return testViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((TestViewHolder) holder).setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    static class TestViewHolder extends RecyclerView.ViewHolder {
        private TextView adapterTv;

        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
            adapterTv = itemView.findViewById(R.id.adapterTv);
        }

        public void setData(String s) {
            adapterTv.setText(s);

        }
    }
}
