package com.vaojr.android.gristdraft;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.vaojr.android.gristdraft.GristDbHelper.GristListCursor;
import com.vaojr.android.gristdraft.GristContract.ListEntry;

public class ListingFragment extends Fragment {

    public static final String EXTRA_LIST_ID_LIST_FRAG =
            "com.vaojr.android.gristdraft.list_id_list_frag";

    public static final String EXTRA_LIST_NAME_LIST_FRAG =
            "com.vaojr.android.gristdraft.list_name_list_frag";

    private SimpleCursorRecyclerAdapter mSimpleCursorRecyclerAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private GristListCursor mCursor;

    public ListingFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Query the list of GristLists
        mCursor = ListManager.get(getActivity()).queryGls();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_listing, container, false);

        FloatingActionButton mFloatingActionButton = (FloatingActionButton)
                v.findViewById(R.id.floatingActionButtonList);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ListNameActivity.class);
                startActivity(i);
            }
        });

        mRecyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSimpleCursorRecyclerAdapter = new SimpleCursorRecyclerAdapter(
                R.layout.item_layout, mCursor, new String[] {ListEntry.COLUMN_NAME}, new int[] {R.id.textView});
        mRecyclerView.setAdapter(mSimpleCursorRecyclerAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new MyItemDecoration());

        return v;
    }

    @Override
    public void onDestroy() {
        mCursor.close();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mSimpleCursorRecyclerAdapter.swapCursor(mCursor = ListManager.get(getActivity()).queryGls());
    }

    class SimpleCursorRecyclerAdapter extends CursorRecyclerAdapter<SimpleViewHolder> {

        private int mLayout;
        private int[] mFrom;
        private int[] mTo;
        private String[] mOriginalFrom;

        public SimpleCursorRecyclerAdapter (int layout, Cursor c, String[] from, int[] to) {
            super(c);
            mLayout = layout;
            mTo = to;
            mOriginalFrom = from;
            findColumns(c, from);
        }

        @Override
        public SimpleViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(mLayout, parent, false);
            return new SimpleViewHolder(v, mTo);
        }

        @Override
        public void onBindViewHolder (SimpleViewHolder holder, Cursor cursor) {
            final int count = mTo.length;
            final int[] from = mFrom;

            for (int i = 0; i < count; i++) {
                holder.views[i].setText(cursor.getString(from[i]));
            }
        }

        /**
         * Create a map from an array of strings to an array of column-id integers in cursor c.
         * If c is null, the array will be discarded.
         *
         * @param c the cursor to find the columns from
         * @param from the Strings naming the columns of interest
         */
        private void findColumns(Cursor c, String[] from) {
            if (c != null) {
                int i;
                int count = from.length;
                if (mFrom == null || mFrom.length != count) {
                    mFrom = new int[count];
                }
                for (i = 0; i < count; i++) {
                    mFrom[i] = c.getColumnIndexOrThrow(from[i]);
                }
            } else {
                mFrom = null;
            }
        }

        @Override
        public Cursor swapCursor(Cursor c) {
            findColumns(c, mOriginalFrom);
            return super.swapCursor(c);
        }
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {
        public TextView[] views;

        public SimpleViewHolder (View itemView, int[] to) {
            super(itemView);
            views = new TextView[to.length];
            for(int i = 0 ; i < to.length ; i++) {
                views[i] = (TextView) itemView.findViewById(to[i]);
                views[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(),
                                ItemListActivity.class);
                        mCursor = ListManager.get(getActivity()).queryListName(
                                mSimpleCursorRecyclerAdapter.getItemId(getAdapterPosition()));
                        if (mCursor.moveToFirst()) {
                            i.putExtra(ItemListFragment.EXTRA_LIST_ID,
                                    (mSimpleCursorRecyclerAdapter.getItemId(getAdapterPosition())));
                            i.putExtra(ItemListFragment.EXTRA_LIST_NAME, mCursor.getString(
                                    mCursor.getColumnIndex("name")));
                            startActivity(i);
                        }
                    }
                });
            }
        }
    }
}
