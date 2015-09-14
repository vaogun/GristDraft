package com.vaojr.android.gristdraft;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by him on 8/2/15.
 */
public class ListNameFragment extends Fragment {

    private static final String KEY_LIST_NAME = "key_list_name";

    public ListManager mListManager = ListManager.get(getActivity());
    String mListName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mListName = savedInstanceState.getString(KEY_LIST_NAME);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_name, container, false);

        final EditText editText = (EditText) view.findViewById(R.id.listEditText);
        editText.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mListName = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Button clearButton = (Button) view.findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });

        Button enterButton = (Button) view.findViewById(R.id.enterButton);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long listId = mListManager.insertList(mListName);
                Toast.makeText(getActivity(), "Clicked enter", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(),
                        ItemListActivity.class);
                i.putExtra(ItemListFragment.EXTRA_LIST_NAME, mListName);
                i.putExtra(ItemListFragment.EXTRA_LIST_ID, listId);
                startActivity(i);
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(KEY_LIST_NAME, mListName);
    }
}
