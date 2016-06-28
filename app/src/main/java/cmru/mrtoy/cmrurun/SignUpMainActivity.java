package cmru.mrtoy.cmrurun;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    private String nameString, userString, passwdString, avataString;


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


} // end main class
