package jp.ac.gifu_u.info.takaharu.myapplication;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.io.IOException;

public class CameraView extends SurfaceView implements SurfaceHolder.Callback {
    private Camera cam;
    private SurfaceHolder holder;

    public CameraView(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); // 古いAPIですが必要
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        cam = Camera.open();
        try {
            cam.setPreviewDisplay(holder);
            cam.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        // 必要に応じて再設定する
        if (holder.getSurface() == null) return;
        try {
            cam.stopPreview();
        } catch (Exception e) {
            // 無視してもよい
        }
        try {
            cam.setPreviewDisplay(holder);
            cam.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        if (cam != null) {
            cam.stopPreview();
            cam.release();
            cam = null;
        }
    }
}
