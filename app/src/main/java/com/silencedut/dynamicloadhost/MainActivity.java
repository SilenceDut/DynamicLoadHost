package com.silencedut.dynamicloadhost;

import android.content.pm.PackageInfo;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ryg.dynamicload.internal.DLIntent;
import com.ryg.dynamicload.internal.DLPluginManager;
import com.ryg.utils.DLUtils;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private String mLuncherActivityName;
    private PackageInfo mPackageInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPlugin();
        findViewById(R.id.load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPlugin();
            }
        });
    }

    private void initPlugin() {
        String pluginFolder = Environment.getExternalStorageDirectory() + "/DynamicLoadHost";
        File file = new File(pluginFolder);
        File[] plugins = file.listFiles();
        mPackageInfo = DLUtils.getPackageInfo(this,plugins[0].getAbsolutePath());
        mLuncherActivityName = mPackageInfo.activities[0].name;
        DLPluginManager.getInstance(this).loadApk(plugins[0].getAbsolutePath());
    }

    private void loadPlugin() {
        DLPluginManager pluginManager = DLPluginManager.getInstance(this);
        pluginManager.startPluginActivity(this, new DLIntent(mPackageInfo.packageName, mLuncherActivityName));
    }
}
