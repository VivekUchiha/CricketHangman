package com.example.satsv.crickethangman;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private String[] words;
    private Random rand;
    private String currWord;
    private LinearLayout wordLayout;
    private TextView[] charViews;
    private TextView t1,t2;
    private ImageView[] imag;
    int l;
    private SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private EditText et;
    private Button b,b1;
    private int errors,best;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        t1 = (TextView) findViewById(R.id.textView3);
        t2 = (TextView) findViewById(R.id.textView4);
        currWord="";
        wordLayout=(LinearLayout)findViewById(R.id.ll);
        Resources res = getResources();
        words = res.getStringArray(R.array.words);
        playGame();
        b1=(Button)findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b1.setVisibility(View.INVISIBLE);
                b.setVisibility(View.VISIBLE);
                et.setText("Enter Letter");
                for(int k=1;k<=5;k++)
                    imag[k].setVisibility(View.INVISIBLE);
                imag[6].setVisibility(View.GONE);
                playGame();





            }
        });

    }

    private void playGame() {
        best = sharedPref.getInt(getString(R.string.saved_high_score_key), 9);
        if(best==9)
            t2.setText("Best : ");
        else
            t2.setText("Best : "+Integer.toString(best));
        t1.setText("Errors : 0");
        l=0;
        imag = new ImageView[7];
        imag[0]=(ImageView)findViewById(R.id.imageView0);
        imag[1]=(ImageView)findViewById(R.id.imageView14);
        imag[2]=(ImageView)findViewById(R.id.imageView15);
        imag[3]=(ImageView)findViewById(R.id.imageView16);
        imag[4]=(ImageView)findViewById(R.id.imageView17);
        imag[5]=(ImageView)findViewById(R.id.imageView18);
        imag[6]=(ImageView)findViewById(R.id.imageView19);




        errors = 0;
        rand=new Random();

        String newWord = words[rand.nextInt(words.length)];
        while (newWord.equals(currWord)) newWord = words[rand.nextInt(words.length)];
        currWord = newWord;
        charViews = new TextView[currWord.length()];
        wordLayout.removeAllViews();
        for (int c = 0; c < currWord.length(); c++) {
            charViews[c] = new TextView(this);
            charViews[c].setText("" + currWord.charAt(c));
            charViews[c].setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            charViews[c].setGravity(Gravity.CENTER);
            if(currWord.charAt(c)!=' ')
            {charViews[c].setBackgroundResource(R.drawable.bak);
             charViews[c].setTextColor(Color.WHITE);}

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.weight = 1.0f;

            charViews[c].setLayoutParams(params);
            charViews[c].setTextColor(Color.WHITE);

            wordLayout.addView(charViews[c]);
        }
        et = (EditText) findViewById(R.id.editText3);
        b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {              //do Best memory



            @Override
            public void onClick(View v) {
                int flag = 0,f=0;
                char x;
                String a = et.getText().toString();
                if (a.length() > 1)
                    et.setText("Enter Single Letter");
                else {
                    x = a.charAt(0);
                    x = Character.toUpperCase(x);
                    for (int c = 0; c < currWord.length(); c++) {
                        if (x == currWord.charAt(c)) {
                            flag = 1;
                            charViews[c].setTextColor(Color.BLACK);
                            l++;

                        }
                    }
                    if(l==currWord.length()-1)
                    {
                        if(best>errors)
                        {
                            best = errors;
                            editor.putInt(getString(R.string.saved_high_score_key), best);
                            editor.commit();}
                        b.setVisibility(View.INVISIBLE);
                        b1.setVisibility(View.VISIBLE);

                    }





                    if (flag==0)
                    {
                        errors++;
                        t1.setText("Errors : " + Integer.toString(errors));
                        imag[errors].setVisibility(View.VISIBLE);

                        if(errors==6)
                        {b1.setVisibility(View.VISIBLE);
                         b.setVisibility(View.INVISIBLE);
                            et.setText("You have failed");
                         if(best>errors)
                         {
                             best = errors;
                             editor.putInt(getString(R.string.saved_high_score_key), best);
                             editor.commit();

                         }





                        }

                    }
                    }


                }

        });







    }
}












