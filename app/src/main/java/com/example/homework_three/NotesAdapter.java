package com.example.homework_three;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private List<NotesModel> list = new ArrayList<>();

    private FragmentActivity activity;

    public void addNote(NotesModel model){
        this.list.add(model);
        notifyDataSetChanged();
    }

    public List<NotesModel> getData(){
        return list;
    }

    public void restore(NotesModel notesModel, int pos){
        list.add(pos, notesModel);
        notifyDataSetChanged();
    }

    public void delete(int pos){
        list.remove(pos);
        notifyItemRemoved(pos);
    }

    public NotesAdapter(FragmentActivity activity){
        this.activity = activity;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notes, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.txtTitle.setText(list.get(position).getTitle());
        holder.txtDescription.setText(list.get(position).getDescription());
        holder.txtDate.setText(list.get(position).getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = list.get(holder.getAdapterPosition()).getTitle();
                String description = list.get(holder.getAdapterPosition()).getDescription();
                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                bundle.putString("description", description);
                AddNoteFragment addNoteFragment = new AddNoteFragment();
                addNoteFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,addNoteFragment).addToBackStack("EditNote").commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NotesViewHolder extends  RecyclerView.ViewHolder {
        TextView txtTitle, txtDescription, txtDate;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_note_title);
            txtDescription = itemView.findViewById(R.id.txt_note_description);
            txtDate = itemView.findViewById(R.id.txt_note_date);
        }
    }

}
