package com.danielg.todolist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

class EntryRecyclerAdapter extends RecyclerView.Adapter<EntryRecyclerAdapter.EntryListViewHolder> {

    private List<Entry> mEntries;

    EntryRecyclerAdapter(List<Entry> entries) {
        mEntries = entries;
    }

    @Override
    public EntryListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EntryListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_entry, parent, false));
    }

    @Override
    public void onBindViewHolder(EntryListViewHolder holder, int position) {
        Entry entry = mEntries.get(position);
        holder.checkboxState.setChecked(entry.isComplete());
        holder.textTitle.setText(entry.getTitle());
        holder.textNotes.setText(entry.getNotes());
    }

    @Override
    public int getItemCount() {
        return mEntries.size();
    }

    void addItem(Entry entry) {
        mEntries.add(entry);
        notifyItemInserted(mEntries.size() - 1);
    }

    public void editItem(int pos, String title, String notes) {

    }

    class EntryListViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkboxState;
        TextView textTitle;
        TextView textNotes;

        EntryListViewHolder(View view) {
            super(view);
            checkboxState = (CheckBox) view.findViewById(R.id.checkbox_state);
            textTitle = (TextView) view.findViewById(R.id.text_title);
            textNotes = (TextView) view.findViewById(R.id.text_notes);
        }
    }

}
