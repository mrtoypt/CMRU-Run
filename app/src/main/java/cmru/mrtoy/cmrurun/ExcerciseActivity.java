package cmru.mrtoy.cmrurun;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class ExcerciseActivity extends AppCompatActivity {

    //Expercit
    private String userIDString, nameString, goldString, avataString;
    private ImageView imageView;
    private TextView nameTextView, stationTextView, questionTextView;
    private RadioGroup radioGroup;
    private RadioButton choice1RadioButton, choice2RadioButton, choice3RadioButton, choice4RadioButton;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercise);
        // Bind Wiget
        imageView = (ImageView) findViewById(R.id.imageView7);
        nameTextView = (TextView) findViewById(R.id.textView7);
        stationTextView = (TextView) findViewById(R.id.textView8);
        questionTextView = (TextView) findViewById(R.id.textView9);
        radioGroup = (RadioGroup) findViewById(R.id.regChoice);
        choice1RadioButton = (RadioButton) findViewById(R.id.radioButton);
        choice2RadioButton = (RadioButton) findViewById(R.id.radioButton6);
        choice3RadioButton = (RadioButton) findViewById(R.id.radioButton7);
        choice4RadioButton = (RadioButton) findViewById(R.id.radioButton8);

        // Get value form Intent
        userIDString = getIntent().getStringExtra("userID");
        nameString = getIntent().getStringExtra("Name");
        goldString = getIntent().getStringExtra("Gold");
        avataString = getIntent().getStringExtra("Avata");

        //Show view
        MyData myData = new MyData();
        int[] iconInts = myData.getAvataInts();
        int intIndex = Integer.parseInt(avataString);
        imageView.setImageResource(iconInts[intIndex]);

        nameTextView.setText(nameString);
        stationTextView.setText("ฐานที่ " + Integer.toString(Integer.parseInt(goldString) + 1));

        SynQuestion synQuestion = new SynQuestion();
        synQuestion.execute();




    }// end onCreate




    private class SynQuestion extends AsyncTask<Void, Void, String>
    {
        // Explicit
        private static final String urlJSON = "http://swiftcodingthai.com/cmru/get_question.php";


        @Override
        protected String doInBackground(Void... voids) {
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlJSON).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                Log.d("1JulV1","JSON ==> " + e.toString());
                return null;
            }
        } // end do in

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("1JulV1","JSON ==> " + s);


            try {
                JSONArray jsonArray = new JSONArray(s);
                String[] questionStrings = new String[jsonArray.length()];
                String[] choice1Strings = new String[jsonArray.length()];
                String[] choice2Strings = new String[jsonArray.length()];
                String[] choice3Strings = new String[jsonArray.length()];
                String[] choice4Strings = new String[jsonArray.length()];
                String[] answerStrings = new String[jsonArray.length()];


                for (int i=0;i<jsonArray.length();i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    questionStrings[i] = jsonObject.getString("Question");
                    choice1Strings[i] = jsonObject.getString("Choice1");
                    choice2Strings[i] = jsonObject.getString("Choice2");
                    choice3Strings[i] = jsonObject.getString("Choice3");
                    choice4Strings[i] = jsonObject.getString("Choice4");
                    answerStrings[i] = jsonObject.getString("Answer");
                    Log.d("1JulV3","Question(" + i + ") ==> " + questionStrings[i]);

                }//end for

            } catch (Exception e) {
                Log.d("1JulV2","e onPost ==> " + e.toString());
            }



        }
    }


    public void clickAnswer(View view) {


    }//end clickAnswer

}// end class
