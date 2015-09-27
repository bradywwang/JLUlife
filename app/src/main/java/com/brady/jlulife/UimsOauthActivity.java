package com.brady.jlulife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.brady.jlulife.Model.UIMSModel;
import com.brady.jlulife.Utils.ConstValue;
import com.brady.jlulife.Utils.Utils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;

public class UimsOauthActivity extends AppCompatActivity {
    EditText metuname;
    EditText metpwd;
    CheckBox mcboxRemember;
    CheckBox mcboxAutoLogin;
    Button btnLogin;
    AsyncHttpClient client;
    UIMSModel uimsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uims_oauth);
        initComponents();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = metuname.getText().toString();
                String pwd = metpwd.getText().toString();
                if(uimsModel!=null) {
                    uimsModel.login(uname, pwd);
//                    uimsModel.getCurrentInfo(getApplicationContext());
                }else {
                    Log.i(getClass().getSimpleName(),"null 1");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_uims_oauth, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    void initComponents(){
        metuname = (EditText) findViewById(R.id.uims_uname);
        metpwd = (EditText) findViewById(R.id.uims_pwd);
        mcboxAutoLogin = (CheckBox) findViewById(R.id.uims_auto_login);
        mcboxRemember = (CheckBox) findViewById(R.id.uims_remember_pwd);
        btnLogin = (Button) findViewById(R.id.btn_uims_login);
        client = new AsyncHttpClient();
        uimsModel = UIMSModel.getInstance();
        Log.i(getClass().getSimpleName(),"null 2");

    }
}
