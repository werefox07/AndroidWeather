package com.geekbrains.menuexample;

import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


///////////////////////////////////////////////////////////////////////////
// On Custom Adapter Click Listener
///////////////////////////////////////////////////////////////////////////

interface OnCustomAdapterClickListener{
    void removeView(int position);
    void editView(int position);
}


///////////////////////////////////////////////////////////////////////////
// Custom Elements Adapter
///////////////////////////////////////////////////////////////////////////

public class CustomElementsAdapter extends RecyclerView.Adapter<CustomElementsAdapter.CustomViewHolder> implements OnCustomAdapterClickListener {

    private List<String> mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    CustomElementsAdapter(List<String> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CustomElementsAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent,
                                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_element, parent, false);

        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CustomElementsAdapter.CustomViewHolder holder, int position) {
        holder.bind(mDataset.get(position), this);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void addView(String text) {
        mDataset.add(text);
        notifyDataSetChanged();
    }

    @Override
    public void removeView(int position) {
        mDataset.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public void editView(int position) {
        mDataset.set(position, "Edit");
        notifyItemChanged(position);
    }

    public void clear() {
        mDataset.clear();
        notifyDataSetChanged();
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        TextView mTextView;
        TextView mContextView;

        private OnCustomAdapterClickListener callbacks;

        CustomViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.txtTitle);
            mContextView = v.findViewById(R.id.txtOptionDigit);
            mContextView.setOnClickListener(this);
        }

        void bind(String text, OnCustomAdapterClickListener callbacks) {
            this.callbacks = callbacks;
            mTextView.setText(text);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.txtOptionDigit: {
                    PopupMenu popup = new PopupMenu(v.getContext(), v);
                    popup.getMenuInflater().inflate(R.menu.context_menu, popup.getMenu());
                    popup.setOnMenuItemClickListener(this);
                    popup.show();
                    break;
                }
            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_edit:
                    if (callbacks != null) callbacks.editView(getAdapterPosition());
                    return true;
                case R.id.menu_delete:
                    if (callbacks != null) callbacks.removeView(getAdapterPosition());
                    return true;
                default:
                    return false;
            }
        }
    }
}
