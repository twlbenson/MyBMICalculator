package c346.rp.edu.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight;
    EditText etHeight;
    Button btnCalculate;
    Button btnReset;
    TextView tvDate;
    TextView tvBMI;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalculate = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);
        tvDate  = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvResult = findViewById(R.id.textViewResult);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                final String datetime =
                        now.get(Calendar.DAY_OF_MONTH) + "/" +
                                (now.get(Calendar.MONTH)+1) + "/" +
                                now.get(Calendar.YEAR) + " " +
                                now.get(Calendar.HOUR_OF_DAY) + ":" +
                                now.get(Calendar.MINUTE);


                float weight = Float.parseFloat(etWeight.getText().toString());
                float height = Float.parseFloat(etHeight.getText().toString());

                float total = 0;

                if (etWeight.getText().toString().isEmpty() || etHeight.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please Fill Up!", Toast.LENGTH_SHORT).show();
                }
                else{
                    total = weight / (height*height);
                }

                tvDate.setText("Last Calculated Date: " + datetime);
                tvBMI.setText(String.format("Last Calculated BMI: %.3f", total));
                etWeight.setText("");
                etHeight.setText("");

                String result = "";
                if (total < 18.5){
                    result += "You are underweight";
                }
                else if(total >= 18.5 && total <= 24.9){
                    result += "Your BMI is normal";
                }
                else if(total >= 25 && total <= 29.9){
                    result +="You are overweight";
                }
                else{
                    result += "You are obese";
                }
                tvResult.setText(result);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

                SharedPreferences.Editor prefEdit = prefs.edit();

                prefEdit.putString("date", datetime);
                prefEdit.putFloat("bmi", total);
                prefEdit.putString("result", result);

                prefEdit.commit();

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etWeight.setText("");
                etHeight.setText("");
                tvDate.setText("Last Calculated Date: ");
                tvBMI.setText("Last Calculated BMI: ");
                tvResult.setText("");
                PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().clear().commit();

            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String date = prefs.getString("date", "");
        float bmi = prefs.getFloat("bmi", 0);
        String result = prefs.getString("result", "");

        tvDate.setText("Last Calculated Date: " + date);
        tvBMI.setText(String.format("Last Calculated BMI: %.3f", bmi));
        tvResult.setText(result);

    }
}
