package com.tickettravel.grupo2.tickettravel.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;

public abstract class ArrayRvAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>
{
    private ArrayList<T> items = new ArrayList<>();

    public void add(T item)
    {
        this.items.add(item);
        notifyItemInserted(this.items.size() - 1);
    }

    public void add(int position, T item)
    {
        this.items.add(position, item);
    }

    public void addAll(Collection<T> items)
    {
        this.items.addAll(items);
        notifyItemRangeInserted(this.items.size(), items.size());
    }

    public T remove(int position)
    {
        T item = this.items.remove(position);
        notifyItemRemoved(position);
        return item;
    }

    public T get(int position)
    {
        T item = this.items.get(position);
        return item;
    }

    public void setItems(Collection<T> items)
    {
        this.items = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    public void clear()
    {
        this.items.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount()
    {
        return items.size();
    }

    public ArrayList<T> getItems()
    {
        return items;
    }
}
