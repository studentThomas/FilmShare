package com.example.filmshare.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.filmshare.R;
import com.example.filmshare.domain.List;
import com.example.filmshare.domain.Movie;

import java.util.ArrayList;

public class ListAdapter extends  RecyclerView.Adapter<ListAdapter.ListViewHolder>{

    private java.util.List<List> lists = new ArrayList<>();

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ListViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        List currentList = lists.get(position);
        holder.name.setText(currentList.getName());
        holder.description.setText(currentList.getDescription());
    }
    @Override
    public int getItemCount() {
        return lists.size();
    }

    public void setLists(java.util.List<List> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView description;


        public ListViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.list_name);
            description = itemView.findViewById(R.id.list_description);
        }
    }
}
