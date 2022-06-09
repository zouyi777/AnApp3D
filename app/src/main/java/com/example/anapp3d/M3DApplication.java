package com.example.anapp3d;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class M3DApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /**初始化Realm数据库配置---开始*/
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("MyTestRealm").build();
        Realm.setDefaultConfiguration(config);
        /**初始化Realm数据库配置---结束*/
    }
}
