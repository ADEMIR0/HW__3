package com.example.homework_three;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


public class MainFragment extends Fragment {

    private RecyclerView rvNotes;
    private NotesAdapter adapter;
    private FloatingActionButton btnOpenAddNote;

    public MainFragment() {

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NotesAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        rvNotes = view.findViewById(R.id.rv_notes);
        btnOpenAddNote = view.findViewById(R.id.btn_open_add_note);

        ListenNoData();
        rvNotes.setAdapter(adapter);

        btnOpenAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container, new AddNoteFragment());
                transaction.addToBackStack("AddNoteFragment");
                transaction.commit();
            }
        });

        listenNoDate();
        enableSwipeToDeleteAndUndo();

        return view;
    }

    private void ListenNoData() {

    }

    private void listenNoDate(){
        getActivity().getSupportFragmentManager().setFragmentResultListener("NoteIsAdding", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if (requestKey.equals("NoteIsAdding")){
                    NotesModel notesModel= (NotesModel) result.getSerializable("model") ;
                    Toast.makeText(requireContext(), notesModel + "", Toast.LENGTH_LONG).show();
                    adapter.addNote(notesModel);
                }
            }
        });
    }
    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final NotesModel item = adapter.getData().get(position);

                adapter.delete(position);


                Snackbar snackbar = Snackbar
                        .make(rvNotes, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        adapter.restore(item, position);
                        rvNotes.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(rvNotes);
    }
}
