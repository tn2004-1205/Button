package jp.ac.gifu_u.info.takaharu.myapplication;

import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener, LocationListener {
    // センサを管理するマネージャ
    //private SensorManager manager;
    private LocationManager manager;
    private Location location;
    /*@Override
    public void onClick(View v) {
        finish();
    }*/
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //EdgeToEdge.enable(this);
        //setContentView(R.layout.activity_main);
        //setContentView(new MyView (this));
        //Button b = (Button) findViewById(R.id.button);
        //b.setOnClickListener(this);
        // マネージャを取得
        //manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        manager = (LocationManager)
                getSystemService(LOCATION_SERVICE);
    }

    public void onResume() {
        super.onResume();

        // パーミッションがあつか確認
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // GPSから1000msecまたは10m移動するごとにリスナーを呼び出し
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 1000, 10, this);
        }
        else {
            // 権限がなければリクエストする
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }
        /*
        // 明るさセンサ(TYPE_LIGHT)のリストを取得
        List<Sensor> sensors =
                manager.getSensorList(Sensor.TYPE_LIGHT);
        // ひとつ以上見つかったら、最初のセンサを取得してリスナーに登録
        if (sensors.size() != 0) {
            Sensor sensor = sensors.get(0);
            manager.registerListener(
                    this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
         */
    protected void onPause() {
        super.onPause();
        // 一時停止の際にリスナー登録を解除
        //manager.unregisterListener(this);
        manager.removeUpdates(this);
    }
    public void onSensorChanged(SensorEvent arg0) {
        // 明るさセンサが変化したとき
        if (arg0.sensor.getType() == Sensor.TYPE_LIGHT) {
            // 明るさの値(単位ルクス)を取得
            float intensity = arg0.values[0];
            // 結果をテキストとして表示
            String str = Float.toString(intensity) + "ルクス";
            TextView textview =
                    (TextView) findViewById(R.id.status_text);
            textview.setText(str);
        /*
        // 地磁気センサが変化したとき
        if (arg0.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            // 地磁気の値をX・Y・Z方向ごとに取得
            float x = arg0.values[0];
            float y = arg0.values[1];
            float z = arg0.values[2];
            // 結果をテキストとして表示
            String str = Float.toString(x) + ","
                    + Float.toString(y) + "," + Float.toString(z);
            TextView textView =
                    (TextView) findViewById(R.id,status_text);
            textview.setText(str);
        }
        */
        }
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        this.location = location;
        // 得られた緯度経度の情報を表示
        double lat = location.getLatitude();   //緯度
        double lng = location.getLongitude(); // 経度
        // TextView に表示
        TextView locText = findViewById(R.id.location_text);
        String locStr = String.format("緯度: %.5f\n経度: %.5f", lat, lng);
        locText.setText(locStr);

        Toast.makeText(this,
                String.format("%.3f %.3f", lat, lng),
                Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // パーミッションが許可されたので再度 onResume 呼ぶか requestLocationUpdates を直接呼ぶ
                onResume();
            } else {
                Toast.makeText(this, "位置情報の権限が必要です", Toast.LENGTH_SHORT).show();
            }
        }
    }
}