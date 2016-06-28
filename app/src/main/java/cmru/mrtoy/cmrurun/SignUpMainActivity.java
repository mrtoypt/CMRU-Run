package cmru.mrtoy.cmrurun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SignUpMainActivity extends AppCompatActivity {

    // Explicit
    private EditText nameEditText, userEditText, passwdEditText;
    private RadioGroup radioGroup;
    private RadioButton avata1RadioButton, avata2RadioButton, avata3RadioButton, avata4RadioButton, avata5RadioButton;
    private String nameString, userString, passwdString;


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
            myAlert.myDialog(this,"ข้อมูลไม่ครบ","กรุณา กรอกข้อมูลให้ครบ ด้วยค่ะ");

        } else {

        }


    }// end of clicksignupsign


} // end main class
