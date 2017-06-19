package com.example.nemanja.addressbookrushorm;

import android.app.Application;

import com.example.nemanja.addressbookrushorm.models.Contact;

import java.util.ArrayList;
import java.util.List;

import co.uk.rushorm.android.AndroidInitializeConfig;
import co.uk.rushorm.core.Rush;
import co.uk.rushorm.core.RushCore;

public class MyRushApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AndroidInitializeConfig config = new AndroidInitializeConfig(getApplicationContext());
        List<Class<? extends Rush>> classes = new ArrayList<>();
        classes.add(Contact.class);
        config.setClasses(classes);
        RushCore.initialize(config);
    }
}
