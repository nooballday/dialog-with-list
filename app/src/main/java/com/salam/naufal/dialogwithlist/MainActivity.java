package com.salam.naufal.dialogwithlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    ListDialogPicker listDialogFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (listDialogFragment == null) {
            listDialogFragment = new ListDialogPicker(this);
        }

        final EditText mEditext = (EditText) findViewById(R.id.et_result);
        Button mButton = (Button) findViewById(R.id.take_btn);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogList(mEditext, new String[]{"Cassandara", "Antwan", "Deborah", "Betty", "Andre"});
            }
        });
    }

    public void openDialogList(final EditText mEditText, String[] listValue){
        listDialogFragment.showDialog(listValue, false);
        listDialogFragment.setDialogResult(new ListDialogPicker.OnMyDialogResult() {
            @Override
            public void finish(String result) {
                mEditText.setText(result);
            }
        });
    }
}
