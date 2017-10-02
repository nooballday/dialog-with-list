package com.salam.naufal.dialogwithlist;


import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.salam.naufal.dialogwithlist.R;

public class ListDialogPicker implements View.OnClickListener {

    private ListView mListDialogSearch;
    private EditText mSearchBox;
    private ImageView mClearBtn;
    private LinearLayout mOtherTextLayout;
    private EditText mOtherText;
    private Button mOkBtnText;

    private Context context;
    private OnMyDialogResult myDialogResult;
    private Dialog dialog;

    private ArrayAdapter<String> adapter;


    public ListDialogPicker(Context context) {
        this.context = context;
    }

    public void showDialog(String[] mItems, boolean mIsEnterText) {
        dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_list_view, null);
        ViewHolder(view);
        dialog.setContentView(view);

        adapter = new ArrayAdapter<>(context, R.layout.simple_list_item_layout, mItems);
        mListDialogSearch.setAdapter(adapter);

        //filter the listview
        mSearchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s);
            }
        });

        //click on listview and set it to et
        mListDialogSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //check if the item is WNA
                if (((TextView) view).getText().equals("Warga Negara Asing")) {
                    mOtherTextLayout.setVisibility(View.VISIBLE);
                    mOkBtnText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //passing data through interface
                            String field = mOtherText.getText().toString();
                            myDialogResult.finish(field);
                            dialog.dismiss();
                        }
                    });

                } else {
                    myDialogResult.finish(((TextView) view).getText().toString());
                    dialog.dismiss();
                }
            }
        });

        //check if other text field is required
        if (mIsEnterText) {
            mOkBtnText.setOnClickListener(this);
        } else {
            mOtherTextLayout.setVisibility(View.GONE);
        }

        mClearBtn.setOnClickListener(this);

        dialog.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonClear:
                mSearchBox.setText("");
                break;
            case R.id.ok_btn_text:
                String field = mOtherText.getText().toString();
                myDialogResult.finish(field);
                dialog.dismiss();
        }
    }

    public void setDialogResult(OnMyDialogResult dialogResult) {
        myDialogResult = dialogResult;
    }

    public interface OnMyDialogResult {
        void finish(String result);
    }

    public void ViewHolder(View v) {
        mListDialogSearch = (ListView) v.findViewById(R.id.lvDialogSearch);
        mSearchBox = (EditText) v.findViewById(R.id.editTextSearch);
        mClearBtn = (ImageView) v.findViewById(R.id.buttonClear);
        mOtherTextLayout = (LinearLayout) v.findViewById(R.id.layout_other_text);
        mOtherText = (EditText) v.findViewById(R.id.other_text);
        mOkBtnText = (Button) v.findViewById(R.id.ok_btn_text);
    }

}
