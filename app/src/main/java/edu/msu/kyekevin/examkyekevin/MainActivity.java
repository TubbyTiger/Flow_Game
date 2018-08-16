package edu.msu.kyekevin.examkyekevin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static android.view.View.LAYER_TYPE_SOFTWARE;

public class MainActivity extends AppCompatActivity {
    private FlowView flowView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flowView = (FlowView)findViewById(R.id.flowView);
        flowView.setLayerType(LAYER_TYPE_SOFTWARE, null);



    }

    public void onRestart(View view){
        flowView.getFlow().restart(flowView);
    }
}
