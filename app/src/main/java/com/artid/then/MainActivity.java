package com.artid.then;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import static com.artid.then.R.id.spinner;

public class MainActivity extends AppCompatActivity {
    private static final String API_KEY = "AIzaSyD6Rm3n8Ygq6wygh2515sqiRo60D1i24kc";
    String targetLanguage;
    Handler textViewHandler;
    TextView textView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //final ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        final EditText editText = (EditText) findViewById(R.id.editText);
        final Button button = (Button) findViewById(R.id.button);

        final Spinner spinnerlanguage = (Spinner) findViewById(R.id.spinner);
        final String[] language = getResources().getStringArray(R.array.language_arrays);
        ArrayAdapter<String> adapterlanguage = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, language);
        spinnerlanguage.setAdapter(adapterlanguage);


        textView = (TextView) findViewById(R.id.textView);
        textViewHandler = new Handler();
        spinnerlanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String language = parent.getItemAtPosition(position).toString();
                //targetLanguage = selectedItem;
                if(language.equals("Thai")){
                    targetLanguage = "th";
                }
                if(language.equals("English")){
                    targetLanguage = "en";
                }
                if(language.equals("Japanese")){
                    targetLanguage = "ja";
                }
                if(language.equals("Chinese")){
                    targetLanguage = "zh-CN";
                }
                if(language.equals("Korean")){
                    targetLanguage = "ko";
                }
                if(language.equals("Vietnamese")){
                    targetLanguage = "vi";
                }
                if(language.equals("Lao")){
                    targetLanguage = "lo";
                }

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TranslateText().execute(editText.getText().toString());
            }
        });
    }

    private class TranslateText extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Translating...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            TranslateOptions options = TranslateOptions.newBuilder()
                    .setApiKey(API_KEY)
                    .build();
            Translate translate = options.getService();
            final Translation translation =
                    translate.translate(params[0],
                            Translate.TranslateOption.targetLanguage(targetLanguage));
            textViewHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (textView != null) {
                        textView.setText(translation.getTranslatedText());
                    }
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void arg) {
            super.onPostExecute(arg);
            if(progressDialog.isShowing()) { progressDialog.dismiss(); }
        }
    }
}
