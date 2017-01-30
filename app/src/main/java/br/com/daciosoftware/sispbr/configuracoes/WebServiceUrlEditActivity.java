package br.com.daciosoftware.sispbr.configuracoes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import br.com.daciosoftware.sispbr.R;
import br.com.daciosoftware.sispbr.util.DialogBox;

public class WebServiceUrlEditActivity extends AppCompatActivity {

    private List<String> fieldsValidate;

    private EditText editTextUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webservice_url_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextUrl = (EditText) findViewById(R.id.editTextUrl);
        editTextUrl.setText(new WebServiceDAO(this).getUrl());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
        return true;
    }

    public boolean validaForm() {
        fieldsValidate = new ArrayList<>();
        String url = editTextUrl.getText().toString();

        if (url.equals("")) {
            fieldsValidate.add(getResources().getString(R.string.label_url_web_service));
        }

        return fieldsValidate.size() <= 0;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
            case R.id.save:
                if (validaForm()) {
                    new WebServiceDAO(this).save(editTextUrl.getText().toString());
                    finish();
                } else {
                    new DialogBox(this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.msg_validade_form), this.fieldsValidate.toString()).show();
                }
                return true;
            case R.id.desfazer:
                editTextUrl.setText(new WebServiceDAO(this).getUrl());
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


}
