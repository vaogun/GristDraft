package com.vaojr.android.gristdraft;

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
import java.text.NumberFormat;

/**
 * Created by him on 8/12/15.
 */
public class ItemFragment extends Fragment {
    public static final String EXTRA_LIST_ID_ITEM_FRAGMENT =
            "com.vaojr.android.gristdraft.list_id_for_item_fragment";

    public static final String EXTRA_LIST_NAME_ITEM_FRAGMENT =
            "com.vaojr.android.gristdraft.list_name";

    private static final String KEY_ITEM_NAME = "key_item_name";
    private static final String KEY_STORE_NAME = "key_store_name";
    private static final String KEY_CATEGORY_NAME = "key_category_name";
    private static final String KEY_AMOUNT = "key_amount";
    private static final String KEY_TOTAL_IN_CENTS = "key_total_in_cents";

    String mItem = null, mStore = null, mCategory = null;
    int mAmount = 0, mTotalInCents = 0;

    private long listId = -1;
    private String listName = null;

    public ListManager mListManager = ListManager.get(getActivity());

    public static ItemFragment newInstance(long listId, String listName) {
        Bundle args = new Bundle();
        args.putLong(EXTRA_LIST_ID_ITEM_FRAGMENT, listId);
        args.putString(EXTRA_LIST_NAME_ITEM_FRAGMENT, listName);

        ItemFragment fragment = new ItemFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (listName == null) {
            listName = getArguments().getString(EXTRA_LIST_NAME_ITEM_FRAGMENT);
        }

        if (listId == -1) {
            listId = getArguments().getLong(EXTRA_LIST_ID_ITEM_FRAGMENT);
        }

        /*
        if (savedInstanceState != null) {
            mItem = savedInstanceState.getString(KEY_ITEM_NAME);
            mTotalInCents = savedInstanceState.getInt(KEY_TOTAL_IN_CENTS);
            mCategory = savedInstanceState.getString(KEY_CATEGORY_NAME);
            mStore = savedInstanceState.getString(KEY_STORE_NAME);
            mAmount = savedInstanceState.getInt(KEY_AMOUNT);
        }
        */
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        EditText editText1 = (EditText)view.findViewById(R.id.editTextItem1);
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mItem = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        EditText editText2 = (EditText)view.findViewById(R.id.editTextItem2);
        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() != 0) {
                    mAmount = Integer.parseInt(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        EditText editText3 = (EditText)view.findViewById(R.id.editTextItem3);
        editText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mStore = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final EditText editText4 = (EditText)view.findViewById(R.id.editTextItem4);
        editText4.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals(current)){
                    editText4.removeTextChangedListener(this);
                    String cleanString = s.toString().replaceAll("[$,.]", "");
                    double parsed = Double.parseDouble(cleanString);
                    String formatted = NumberFormat.getCurrencyInstance().format((parsed/100));
                    current = formatted;
                    editText4.setText(formatted);
                    editText4.setSelection(formatted.length());
                    editText4.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (current == "") {
                    mTotalInCents = 0;
                } else {
                    mTotalInCents = Integer.parseInt(s.toString()
                            .replace("$", "").replace(".", "").replace(",", ""));
                }

            }
        });

        EditText editText5 = (EditText)view.findViewById(R.id.editTextItem5);
        editText5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCategory = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Button enterButton = (Button)view.findViewById(R.id.eButton);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItem == null || mItem.trim().length() == 0) {
                    Toast.makeText(getActivity(), "Enter Item Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mAmount != mAmount) {
                    Toast.makeText(getActivity(), "Enter Amount Between 1 - 9999,",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mStore == null || mStore.trim().length() == 0) {
                    Toast.makeText(getActivity(), "Enter Store Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mTotalInCents != mTotalInCents) {
                    mTotalInCents = 0;
                }

                if (mCategory == null || mCategory.trim().length() == 0) {
                    Toast.makeText(getActivity(), "Enter Item Category", Toast.LENGTH_SHORT).show();
                    return;
                }

                long itemId = mListManager.insertItem(listId, mItem, mTotalInCents, mCategory,
                        mStore, mAmount);
                Toast.makeText(getActivity(), "Clicked enter, inserted item: " + itemId
                        + ", in list: " + listName, Toast.LENGTH_SHORT).show();

                getActivity().finish();
            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(KEY_ITEM_NAME, mItem);
        savedInstanceState.putString(KEY_STORE_NAME, mStore);
        savedInstanceState.putString(KEY_CATEGORY_NAME, mCategory);
        savedInstanceState.putInt(KEY_AMOUNT, mAmount);
        savedInstanceState.putInt(KEY_TOTAL_IN_CENTS, mTotalInCents);
    }
}
