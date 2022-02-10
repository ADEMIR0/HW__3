package com.example.homework_three;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AddNoteFragment extends Fragment {

    private Button btnAdd;
    private EditText etTitle;
    private EditText etDescription;

    public AddNoteFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_note, container, false);
        btnAdd = view.findViewById(R.id.btn_add);


        etTitle = view.findViewById(R.id.et_title);
        etDescription = view.findViewById(R.id.et_description);


        btnAdd.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String description = etDescription.getText().toString();
                String dateTime = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
                Bundle bundle = new Bundle();
                NotesModel notesModel = new NotesModel(title,description,dateTime);
                bundle.putSerializable("model",notesModel);
                getActivity().getSupportFragmentManager().setFragmentResult("NoteIsAdding", bundle);
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }
}