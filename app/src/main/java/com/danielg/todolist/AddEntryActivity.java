package com.danielg.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddEntryActivity extends AppCompatActivity {

    private static final String TAG = "AddEntryActivity";

    private EditText txtTitle;
    private EditText txtNotes;
    private Button btnCancel;
    private Button btnSave;
    private Entry entry;
    public static final String KEY_ENTRY = "ENTRY_OBJ";

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_add_entry);

        setupComponents();

        btnCancel = (Button) findViewById(R.id.button_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        btnSave = (Button) findViewById(R.id.button_save);
        btnSave.setEnabled(false);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                entry = new Entry(txtTitle.getText().toString(), txtNotes.getText().toString(), false);
                intent.putExtra(KEY_ENTRY, entry);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void setupComponents() {
        Toolbar addToolbar = (Toolbar) findViewById(R.id.toolbar_add_entry);
        setSupportActionBar(addToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add entry");
        }

        txtTitle = (EditText) findViewById(R.id.text_title);
        txtNotes = (EditText) findViewById(R.id.text_notes);

        txtTitle.addTextChangedListener(new TitleNotesTextWatcher());
        txtNotes.addTextChangedListener(new TitleNotesTextWatcher());
    }


    private class TitleNotesTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if(txtTitle.length() == 0 && txtNotes.length() == 0 && btnSave.isEnabled()) {
                btnSave.setEnabled(false);
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(txtTitle.length() > 0 || txtNotes.length() > 0) {
                btnSave.setEnabled(true);
            } else {
                btnSave.setEnabled(false);
            }
        }
    }
}
