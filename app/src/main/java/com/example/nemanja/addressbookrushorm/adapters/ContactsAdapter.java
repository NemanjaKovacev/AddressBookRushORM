package com.example.nemanja.addressbookrushorm.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nemanja.addressbookrushorm.R;
import com.example.nemanja.addressbookrushorm.holders.ContactsViewHolder;
import com.example.nemanja.addressbookrushorm.interfaces.LongClickListener;
import com.example.nemanja.addressbookrushorm.models.Contact;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsViewHolder> {

    private final Context context;
    private final List<Contact> contacts;
    private Contact contact;

    public ContactsAdapter(Context context, List<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row, parent, false);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, final int position) {

        holder.txtName.setText(contacts.get(position).getName());
        holder.txtPhone.setText(contacts.get(position).getPhone());
        holder.txtEmail.setText(contacts.get(position).getEmail());

        holder.setLongClickListener(new LongClickListener() {
            @Override
            public void onItemLongClick() {
                contact = contacts.get(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public Contact getSelectedContact() {
        return contact;
    }
}
