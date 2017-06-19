package com.example.nemanja.addressbookrushorm.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nemanja.addressbookrushorm.R;
import com.example.nemanja.addressbookrushorm.adapters.ContactsAdapter;
import com.example.nemanja.addressbookrushorm.models.Contact;

import java.util.ArrayList;
import java.util.List;

import co.uk.rushorm.core.RushSearch;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText editTextName;
    private EditText editTextPhone;
    private EditText editTextEmail;
    private Button buttonSave;
    @NonNull
    private final ArrayList<Contact> contacts = new ArrayList<>();
    private ContactsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContactsAdapter(this, contacts);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_layout, null);
                dialogBuilder.setTitle(R.string.contact_add);
                editTextName = (EditText) mView.findViewById(R.id.nameEditText);
                editTextPhone = (EditText) mView.findViewById(R.id.phoneEditText);
                editTextEmail = (EditText) mView.findViewById(R.id.emailEditText);
                buttonSave = (Button) mView.findViewById(R.id.saveButton);

                dialogBuilder.setView(mView);
                final AlertDialog dialog = dialogBuilder.create();

                buttonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Contact contact = new Contact();
                        save(contact);
                        editTextName.setText("");
                        editTextPhone.setText("");
                        editTextEmail.setText("");
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        Toast.makeText(MainActivity.this, R.string.toast_contact_added, Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        });
        this.retrieve();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        CharSequence title = item.getTitle();
        if (title == (getResources().getString(R.string.edit))) {

            final AlertDialog.Builder dialogEditBuilder = new AlertDialog.Builder(MainActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.dialog_layout, null);
            dialogEditBuilder.setTitle(R.string.contact_edit);
            editTextName = (EditText) mView.findViewById(R.id.nameEditText);
            editTextPhone = (EditText) mView.findViewById(R.id.phoneEditText);
            editTextEmail = (EditText) mView.findViewById(R.id.emailEditText);
            buttonSave = (Button) mView.findViewById(R.id.saveButton);
            dialogEditBuilder.setView(mView);
            final AlertDialog dialog = dialogEditBuilder.create();

            editTextName.setText(adapter.getSelectedContact().getName());
            editTextPhone.setText(adapter.getSelectedContact().getPhone());
            editTextEmail.setText(adapter.getSelectedContact().getEmail());

            buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Contact oldContact = adapter.getSelectedContact();
                    Contact contact = new RushSearch().whereId(oldContact.getId()).findSingle(Contact.class);
                    save(contact);
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(MainActivity.this, R.string.toast_contact_edited, Toast.LENGTH_SHORT).show();
                }
            });
            dialog.show();

        } else {
            Contact oldContact = adapter.getSelectedContact();
            Contact contact = new RushSearch().whereId(oldContact.getId()).findSingle(Contact.class);
            contact.delete();
            retrieve();
            Toast.makeText(MainActivity.this, R.string.toast_contact_deleted, Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }

    private void retrieve() {
        List<Contact> contacts = new RushSearch().find(Contact.class);
        adapter = new ContactsAdapter(MainActivity.this, contacts);
        recyclerView.setAdapter(adapter);
    }

    private void save(@NonNull Contact contact) {
        contact.setName(editTextName.getText().toString());
        contact.setPhone(editTextPhone.getText().toString());
        contact.setEmail(editTextEmail.getText().toString());
        contact.save();
        retrieve();
    }
}
