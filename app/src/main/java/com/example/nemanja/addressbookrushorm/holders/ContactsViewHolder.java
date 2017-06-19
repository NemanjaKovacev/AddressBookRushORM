package com.example.nemanja.addressbookrushorm.holders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.example.nemanja.addressbookrushorm.R;
import com.example.nemanja.addressbookrushorm.interfaces.LongClickListener;

public class ContactsViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnCreateContextMenuListener {

    public final TextView txtName;
    public final TextView txtPhone;
    public final TextView txtEmail;
    private LongClickListener longClickListener;

    public ContactsViewHolder(@NonNull View view) {
        super(view);
        txtName = (TextView) view.findViewById(R.id.nameText);
        txtEmail = (TextView) view.findViewById(R.id.emailText);
        txtPhone = (TextView) view.findViewById(R.id.phoneText);

        view.setOnLongClickListener(this);
        view.setOnCreateContextMenuListener(this);
    }

    @Override
    public boolean onLongClick(View v) {
        this.longClickListener.onItemLongClick();
        return false;
    }

    public void setLongClickListener(LongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(R.string.edit);
        menu.add(R.string.delete);
    }
}
