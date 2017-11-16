package com.japri.amierul.tensorflowandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

public class MainActivity extends AppCompatActivity {

    private static final String MODEL_FILE = "file:///android_asset/optimized_tensor.pb";
    private static final String INPUT_NODE = "input";
    private static final String OUTPUT_NODE = "output";

    private static final int[] INPUT_SIZE = {1};

    private TensorFlowInferenceInterface inferenceInterface;

    static {
        System.loadLibrary("tensorflow_inference");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inferenceInterface = new TensorFlowInferenceInterface();

        inferenceInterface.initializeTensorFlow(getAssets(), MODEL_FILE);

        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResult();
            }
        });


    }

    private void showResult(){

        final EditText editNum1 = findViewById(R.id.editNum1);

        float num1 = Float.parseFloat(editNum1.getText().toString());

        float[] inputFloats = {num1};

        inferenceInterface.fillNodeFloat(INPUT_NODE, INPUT_SIZE, inputFloats);

        inferenceInterface.runInference(new String[] {OUTPUT_NODE});

        float[] resu = {0};
        inferenceInterface.readNodeFloat(OUTPUT_NODE, resu);

        final TextView textViewR = findViewById(R.id.txtViewResult);
        textViewR.setText(Float.toString(resu[0]));
    }
}
