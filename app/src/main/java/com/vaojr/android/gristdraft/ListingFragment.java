package com.vaojr.android.gristdraft;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ListingFragment extends Fragment {

    /** Implementation of ListView replacement */
    private SparseBooleanArray mSelectedPositions = new SparseBooleanArray();
    private boolean mIsSelectable = false;

    private void setItemChecked(int position,boolean isChecked) {
        mSelectedPositions.put(position, isChecked);
    }

    private boolean isItemChecked(int position) {
        return mSelectedPositions.get(position);
    }

    private void setSelectable(boolean selectable) {
        mIsSelectable = selectable;
    }

    private boolean isSelectable() {
        return mIsSelectable;
    }
    /**  ****************************************************************8  */


    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    MyAdapter mAdapter;
    String[] foods = new String[]{"apples", "oranges", "rice", "yams", "fish", "oil", "bananas",
                                  "chicken", "bread", "kale", "sweet potatoes", "corn", "beef"};

    String text = null;

    public ListingFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        View v = inflater.inflate(R.layout.fragment_listing, container, false);

        mRecyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(new MyAdapter(foods));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new MyItemDecoration());
        return v;
    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private String[] itemsData;

        public MyAdapter(String[] itemsDataInit) {
            itemsData = itemsDataInit;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemLayoutView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_layout, viewGroup, false);

            return new ViewHolder(itemLayoutView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int i) {
            holder.textView.setText(itemsData[i]);
        }

        @Override
        public int getItemCount() {
            return itemsData.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;

            public ViewHolder(View itemLayoutView) {
                super(itemLayoutView);
                itemLayoutView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Heyyyyyy", "Element " + getAdapterPosition() + " clicked.");
                        Toast.makeText(getActivity(), "Element " + getAdapterPosition() + " clicked.",
                                Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getActivity().getApplicationContext(), TestIntentActivity.class);
                        startActivity(i);
                    }
                });
                textView = (TextView) itemLayoutView.findViewById(R.id.textView);
            }
        }

    }



}
