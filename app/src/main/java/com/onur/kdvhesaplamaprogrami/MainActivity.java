package com.onur.kdvhesaplamaprogrami;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private EditText editTutar,editKDV;
    private Button buttonYuzde1,buttonYuzde8,buttonYuzde18,buttonHesapla,buttonBaslik;
    private TextView textViewKdvDahilorHaric,textViewIslemTutari,textViewKdvTutari,textViewToplamTutar;
    private RadioGroup radioGroup;
    private double tutar = 0.0;
    private double kdv = 0.0;
    private boolean kdvdahil = true;
    private Animation ay,ya;
    private AdView banner1;
    private TextWatcher editTutarDegisimler = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            try {
                tutar =Double.parseDouble(s.toString());
            }catch (NumberFormatException e){
                tutar=0.0;
            }
            guncelle();

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher editKDVDegisimler = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            try {
                kdv =Double.parseDouble(s.toString());

            }catch (NumberFormatException e){
                kdv = 0.0;

            }
            guncelle();

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private RadioGroup.OnCheckedChangeListener radioGroupDegisimler = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radioButtonKdvDahil) {
                kdvdahil = true;
            }
            else if (checkedId == R.id.radioButton2KdvHaric){
                kdvdahil = false;
            }
            guncelle();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        banner1 = findViewById(R.id.banner1);
        AdRequest adRequest = new AdRequest.Builder().build();
        banner1.loadAd(adRequest);




        editTutar = findViewById(R.id.editTutar);
        editKDV = findViewById(R.id.editKDV);
        buttonBaslik = findViewById(R.id.buttonBaslik);
        buttonHesapla = findViewById(R.id.buttonHesapla);
        buttonYuzde1 = findViewById(R.id.buttonYuzde1);
        buttonYuzde8 = findViewById(R.id.buttonYuzde8);
        buttonYuzde18 = findViewById(R.id.buttonYuzde18);
        textViewKdvDahilorHaric = findViewById(R.id.textViewKdvDahilorHaric);
        textViewIslemTutari = findViewById(R.id.textViewIslemTutari);
        textViewKdvTutari = findViewById(R.id.textViewKdvTutari);
        textViewToplamTutar = findViewById(R.id.textViewToplamTutar);
        radioGroup = findViewById(R.id.radioGroup);

        ay = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.asagiidanyukaributon);
        ya = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.yukaridanasagibaslik);
        buttonBaslik.setAnimation(ya);
        buttonHesapla.setAnimation(ay);

        editTutar.addTextChangedListener(editTutarDegisimler);
        editKDV.addTextChangedListener(editKDVDegisimler);
        radioGroup.setOnCheckedChangeListener(radioGroupDegisimler);





        buttonYuzde1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editKDV.setText(String.valueOf(1));
            }
        });
        buttonYuzde8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editKDV.setText(String.valueOf(8));

            }
        });
        buttonYuzde18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editKDV.setText(String.valueOf(18));

            }
        });

        guncelle();

    }
    public void guncelle (){

        buttonHesapla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

                DecimalFormat formatter = new DecimalFormat("###,###.##");

                double kdvdahilIslemTutari = tutar / (1+kdv/100);
                double kdvdahilKdvTutari = tutar-kdvdahilIslemTutari;

                double kdvharicKdvsi = tutar * (kdv/100);
                double kdvharicToplamtutar = tutar+kdvharicKdvsi;



                if (kdvdahil){
                    textViewKdvDahilorHaric.setText("### KDV DAHİL ###");
                    textViewKdvDahilorHaric.setTextColor(Color.BLACK);
                    textViewKdvDahilorHaric.setBackgroundResource(R.color.yesil);
                    textViewKdvDahilorHaric.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);

                    textViewToplamTutar.setText(formatter.format(tutar));
                    textViewIslemTutari.setText(formatter.format(kdvdahilIslemTutari));
                    textViewKdvTutari.setText(formatter.format(kdvdahilKdvTutari));
                }
                else {
                    textViewKdvDahilorHaric.setText("### KDV HARİÇ ###");
                    textViewKdvDahilorHaric.setTextColor(Color.BLACK);
                    textViewKdvDahilorHaric.setBackgroundResource(R.color.kirmizi);
                    textViewKdvDahilorHaric.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);

                    textViewIslemTutari.setText(formatter.format(tutar));
                    textViewKdvTutari.setText(formatter.format(kdvharicKdvsi));
                    textViewToplamTutar.setText(formatter.format(kdvharicToplamtutar));




                }
            }
        });
    }
}