package cmru.mrtoy.cmrurun;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class Cmruwalk extends AppCompatActivity {
    // Expercit
    private static final String urlLogo = "http://testapp.cm2cars.com/images/cmrulogo.png";
    private static final String urlJSON = "http://swiftcodingthai.com/cmru/get_user_master.php";
    private ImageView imageView;
    private EditText userEditText, passwdEditText;
    private String userString, passwdString, strID , goldString, avataString;


    @Override
    protected void onCreate(Bundle savedInstanceState) { // method
        super.onCreate(savedInstanceState);   // statement
        setContentView(R.layout.activity_cmruwalk);

        //Bind widget
        imageView = (ImageView) findViewById(R.id.imageView6);
        userEditText = (EditText) findViewById(R.id.editText4);
        passwdEditText = (EditText) findViewById(R.id.editText5);

        //load logo
        Picasso.with(this).load(urlLogo).resize(150, 180).into(imageView);

    }

    // Create Inner class
    //Void = ก่อนโหลดทำอะไร, Void ระหว่างโหลด, String หลังโหลด ส่งสริงมา

    private class SynUser extends AsyncTask<Void, Void, String> {
        // Explcit
        private Context context;
        private String strURL;
        private boolean statusABoolean = true;
        private String truePasswdString, nameuserString;


        //  กด Alt + Ins  แล้วเลือก Construtor จากนั้นจะขึ้น 2 บันทัดให้กด Ship แล้วเลือกทั้งสอง
        public SynUser(Context context, String strURL) {
            this.context = context;
            this.strURL = strURL;
        }

        @Override
        protected String doInBackground(Void... params) {

            //
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(strURL).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();


            } catch (Exception e) {   // Exception e คือ การ Error ที่พอยอมรับได้ ให้ทำการข้ามขั้นตอนนี้ไป และรายงานค่ามาที่ e
                Log.d("29June", "e doinBack ==> " + e.toString());
                return null;
            }

            //  return null;
        } //doInBank

        //  กด Alt + Ins  แล้วเลือก Overridese จาก เลือก onPostExecute
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("29June", "jSUN ==> " + s);
            try {

                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (userString.equals(jsonObject.getString("User"))) {
                        statusABoolean = false;
                        truePasswdString = jsonObject.getString("Password");
                        nameuserString = jsonObject.getString("Name");
                        strID = jsonObject.getString("id");
                        goldString = jsonObject.getString("Gold");
                        avataString = jsonObject.getString("Avata");

                    }// end if
                }// end for


                if (statusABoolean) {
                    MyAlert myAlert = new MyAlert();
                    myAlert.myDialog(context, "No this User", "No User : " + userString + " On Database");


                } else if (passwdString.equals(truePasswdString)) {
                    //  true passwd
                    // Toast = ข้อความต้อนรับขึ้นมาระยะหนึ่งแล้วหายไป
                    Toast.makeText(context, "Wellcome " + nameuserString, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Cmruwalk.this, MapsActivity.class);
                    intent.putExtra("Name", nameuserString);
                    intent.putExtra("userID", strID);
                    intent.putExtra("Gold", goldString);
                    intent.putExtra("Avata", avataString);
                    startActivity(intent);
                    finish();


                } else {
                    //passwd false
                    MyAlert myAlert = new MyAlert();
                    myAlert.myDialog(context, "Password false", "Please Try Again");
                }

            } catch (Exception e) {
                Log.d("29June", "e onPost ==> " + e.toString());
            }

        }
    } // end of Synuser


    public void clickSignIn(View view) {
        userString = userEditText.getText().toString().trim();
        passwdString = passwdEditText.getText().toString().trim();
        // Check Space
        if (userString.equals("") || passwdString.equals("")) {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "Have Space", "Please Fill All Every Blank");
        } else {
            checkUserPass();
        }

    } // clickSignIn

    private void checkUserPass() {
        SynUser synUser = new SynUser(this, urlJSON);
        synUser.execute();
    }

    public void clickSignUpMain(View view) {          // method no return
        startActivity(new Intent(Cmruwalk.this, SignUpMainActivity.class));
    }
}
