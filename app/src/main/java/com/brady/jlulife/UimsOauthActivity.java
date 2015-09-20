package com.brady.jlulife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import java.io.IOException;
import java.net.URI;

public class UimsOauthActivity extends AppCompatActivity {
    EditText metuname;
    EditText metpwd;
    CheckBox mcboxRemember;
    CheckBox mcboxAutoLogin;
    Button btnLogin;
    AsyncHttpClient client;

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
                String secretpwd = Utils.getMD5Str("UIMS"+uname+pwd);
                if(client!=null){
                    RequestParams params = new RequestParams();
                    params.put("j_username",uname);
                    params.put("j_password",secretpwd);
                    /*client.post(ConstValue.SECURITY_CHECK, params, new ResponseHandlerInterface() {
                        @Override
                        public void sendResponseMessage(org.apache.http.HttpResponse httpResponse) throws IOException {

                        }

                        @Override
                        public void sendStartMessage() {

                        }

                        @Override
                        public void sendFinishMessage() {

                        }

                        @Override
                        public void sendProgressMessage(long l, long l1) {

                        }

                        @Override
                        public void sendCancelMessage() {

                        }

                        @Override
                        public void sendSuccessMessage(int i, org.apache.http.Header[] headers, byte[] bytes) {

                        }

                        @Override
                        public void sendFailureMessage(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {

                        }

                        @Override
                        public void sendRetryMessage(int i) {

                        }

                        @Override
                        public URI getRequestURI() {
                            return null;
                        }

                        @Override
                        public org.apache.http.Header[] getRequestHeaders() {
                            return new org.apache.http.Header[0];
                        }

                        @Override
                        public void setRequestURI(URI uri) {

                        }

                        @Override
                        public void setRequestHeaders(org.apache.http.Header[] headers) {

                        }

                        @Override
                        public void setUseSynchronousMode(boolean b) {

                        }

                        @Override
                        public boolean getUseSynchronousMode() {
                            return false;
                        }

                        @Override
                        public void setUsePoolThread(boolean b) {

                        }

                        @Override
                        public boolean getUsePoolThread() {
                            return false;
                        }

                        @Override
                        public void onPreProcessResponse(ResponseHandlerInterface responseHandlerInterface, org.apache.http.HttpResponse httpResponse) {

                        }

                        @Override
                        public void onPostProcessResponse(ResponseHandlerInterface responseHandlerInterface, org.apache.http.HttpResponse httpResponse) {

                        }

                        @Override
                        public void setTag(Object o) {

                        }

                        @Override
                        public Object getTag() {
                            return null;
                        }
                    })*/
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
    }
}
