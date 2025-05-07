package jp.ac.gifu_u.info.takaharu.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        //setContentView(R.layout.activity_main);
        setContentView(new MyView (this));
        //Button b = (Button) findViewById(R.id.button);
        //b.setOnClickListener(this);
    }
}