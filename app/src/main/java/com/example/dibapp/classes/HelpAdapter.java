package com.example.dibapp.classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dibapp.model.HelpItem;

import com.example.dibapp.R;


import java.util.List;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.HelpViewHolder> {

    private final List<HelpItem> helpItemList;

    public HelpAdapter(List<HelpItem> helpItemList) {
        this.helpItemList = helpItemList;
    }

    @NonNull
    @Override
    public HelpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.help_item, parent, false);
        return new HelpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpViewHolder holder, int position) {
        HelpItem helpItem = helpItemList.get(position);
        holder.promptTextView.setText(helpItem.getPrompt());
        holder.explanationTextView.setText(helpItem.getExplanation());
    }

    @Override
    public int getItemCount() {
        return helpItemList.size();
    }

    public static class HelpViewHolder extends RecyclerView.ViewHolder {
        TextView promptTextView;
        TextView explanationTextView;

        public HelpViewHolder(@NonNull View itemView) {
            super(itemView);
            promptTextView = itemView.findViewById(R.id.promptTextView);
            explanationTextView = itemView.findViewById(R.id.explanationTextView);
        }
    }
}
