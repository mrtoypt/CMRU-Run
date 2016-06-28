package cmru.mrtoy.cmrurun;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class SignUpMainActivity extends AppCompatActivity {

    // Explicit
    private EditText nameEditText, userEditText, passwdEditText;
    private RadioGroup radioGroup;
    private RadioButton avata1RadioButton, avata2RadioButton, avata3RadioButton, avata4RadioButton, avata5RadioButton;
    private String nameString, userString, passwdString, avataString;
    private static final String urlPHP = "http://swiftcodingthai.com/cmru/add_user_master.php";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_main);

        // Bind Widget การผูกตัวแปรกับ Wedget
        nameEditText = (EditText) findViewById(R.id.editText); // nameEditText = findViewById(R.id.editText) เสร็จแล้วกด Alt + Enter = cat
        userEditText = (EditText) findViewById(R.id.editText2);
        passwdEditText = (EditText) findViewById(R.id.editText3);
        radioGroup = (RadioGroup) findViewById(R.id.radAvata);
        avata1RadioButton = (RadioButton) findViewById(R.id.radioButton1);
        avata2RadioButton = (RadioButton) findViewById(R.id.radioButton2);
        avata3RadioButton = (RadioButton) findViewById(R.id.radioButton3);
        avata4RadioButton = (RadioButton) findViewById(R.id.radioButton4);
        avata5RadioButton = (RadioButton) findViewById(R.id.radioButton5);

        // Radio Controler
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton1:
                        avataString = "1";
                        break;
                    case R.id.radioButton2:
                        avataString = "2";
                        break;
                    case R.id.radioButton3:
                        avataString = "3";
                        break;
                    case R.id.radioButton4:
                        avataString = "4";
                        break;
                    case R.id.radioButton5:
                        avataString = "5";
                        break;

                } // switch case
            } // end on Redio check
        });  // ตรวจสอบ


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    } // end main method

    public void clickSignUpSign(View view) {

        // get value from Edit Text
        nameString = nameEditText.getText().toString().trim();
        userString = userEditText.getText().toString().trim();
        passwdString = passwdEditText.getText().toString().trim();

        // check space
        if (nameString.equals("") || userString.equals("") || passwdString.equals("")) {
            // Have Object
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "ข้อมูลไม่ครบ", "กรุณา กรอกข้อมูลให้ครบ ด้วยค่ะ");

        } else if (!checkChooseAvata()) {
            //Not Checked
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "ข้อมูลไม่ครบ", "กรุณาเลือก Avata ก่อนค่ะ");
        } else {
            // On Checked
            conFirmData();
        }  // end if check


    }// end of clicksignupsign


    private void conFirmData() {
        MyData myData = new MyData();
        int[] avataInts = myData.getAvataInts();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setIcon(avataInts[Integer.parseInt(avataString)]);
        builder.setTitle(nameString);
        builder.setMessage("User =" + userString + "\n" + "Password = " + passwdString);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                upLoadUserDataToSever();
                dialog.dismiss();
            }
        });
        builder.show();
    } // end of conFirmData

    private void upLoadUserDataToSever() {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()    // RequestBody = การเพคเกจข้อมูล เพื่อส่งไปยังไฟล์ php
                .add("isAdd", "true")
                .add("Name", nameString)
                .add("User", userString)
                .add("Password", passwdString)
                .add("Avata", avataString)
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(urlPHP).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                finish();
            }
        });



    }
    private boolean checkChooseAvata() {
        boolean status = true;
        if (avata1RadioButton.isChecked() || avata2RadioButton.isChecked() ||
                avata3RadioButton.isChecked() || avata4RadioButton.isChecked() ||
                avata5RadioButton.isChecked()) {
            // Have check
            status = true;
        } else {
            //  Not check
            status = false;
        }
        return status;
    }// end of checkChooseAvata


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SignUpMain Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://cmru.mrtoy.cmrurun/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SignUpMain Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://cmru.mrtoy.cmrurun/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
} // end main class
