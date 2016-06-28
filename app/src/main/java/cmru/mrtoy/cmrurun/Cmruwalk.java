package cmru.mrtoy.cmrurun;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Cmruwalk extends AppCompatActivity {
    // Expercit
    private static final String urlLogo = "http://testapp.cm2cars.com/images/cmrulogo.png";
    private ImageView imageView;
    private EditText userEditText, passwdEditText;
    private String userString, passwdString;


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

        @Override
        protected String doInBackground(Void... params) {
            return null;
        } //doInBank
    }


    public void clickSignIn(View view) {
        userString = userEditText.getText().toString().trim();
        passwdString = passwdEditText.getText().toString().trim();
        // Check Space
        if (userString.equals("") || passwdString.equals("")) {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "Have Space", "Please Fill All Every Blank");
        }
    } // clickSignIn

    public void clickSignUpMain(View view) {          // method no return
        startActivity(new Intent(Cmruwalk.this, SignUpMainActivity.class));
    }
}
