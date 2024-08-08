package com.example.dibapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<String> MessageList;

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView MessageTextView;

        public MessageViewHolder(View itemView) {
            super(itemView);
            MessageTextView = itemView.findViewById(R.id.message_text_view);
        }
    }

    public MessageAdapter(List<String> MessageList) {
        this.MessageList = MessageList;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        String message = MessageList.get(position);
        holder.MessageTextView.setText(message);
    }

    @Override
    public int getItemCount() {
        return MessageList.size();
    }
}


