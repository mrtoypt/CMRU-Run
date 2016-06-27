package cmru.mrtoy.cmrurun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Cmruwalk extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) { // method
        super.onCreate(savedInstanceState);   // statement
        setContentView(R.layout.activity_cmruwalk);
    }

    public void clickSignUpMain(View view) {          // method no return
        startActivity(new Intent(Cmruwalk.this,SignUpMainActivity.class));
    }
}
