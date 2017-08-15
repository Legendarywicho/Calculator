package io.luis_santiago.calculatorandroid;

import android.annotation.TargetApi;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import static android.R.attr.editable;
import static android.R.attr.value;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static io.luis_santiago.calculatorandroid.R.id.tip;
import static io.luis_santiago.calculatorandroid.R.id.total;

@TargetApi(Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {

    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();
    private final String TAG = "Main activity";

    //Variables for the UI
    private double billAmount = 0.0;
    private double percent = 0.15;
    private SeekBar seekBar;
    private EditText editText;
    private TextView percentTextView;
    private TextView tipTextView;
    private TextView totalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        tipTextView.setText(currencyFormat.format(0));
        totalTextView.setText(currencyFormat.format(0));
        editText.addTextChangedListener(textWatcher);
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String value = editText.getText().toString();
            if(value.equals("")){
                tipTextView.setText(currencyFormat.format(0));
                totalTextView.setText(currencyFormat.format(0));
                billAmount = 0;
            }
            else{
                billAmount = Double.parseDouble(value);
                Log.e(TAG, value);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String value = editText.getText().toString();
            Log.e("Main activity","Ya acabe de escribir: "+value);
            calculate();
        }
    };

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
            // When the progress is changed, set up the answer immediately
            percent = i;
            percentTextView.setText(percentFormat.format(percent/100));
            Log.e(TAG, Double.toString(percent));
            calculate();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private void init(){
        //Setting up the views
        percentTextView = (TextView) findViewById(R.id.percent);
        tipTextView = (TextView)findViewById(tip);
        totalTextView = (TextView) findViewById(total);
        seekBar = (SeekBar)findViewById(R.id.seek_bar);
        editText=(EditText)findViewById(R.id.edit_text);
    }

    private void calculate(){
        double tip = (billAmount/100) * percent;
        double total = tip + billAmount;
        tipTextView.setText(currencyFormat.format(tip));
        totalTextView.setText(currencyFormat.format(total));
    }
}
