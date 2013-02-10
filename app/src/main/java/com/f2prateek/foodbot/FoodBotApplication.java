package com.f2prateek.foodbot;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import com.google.inject.Injector;
import com.google.inject.Stage;
import roboguice.RoboGuice;

/**
 * FoodBot application
 */
public class FoodBotApplication extends Application {

    /**
     * Create main application
     */
    public FoodBotApplication() {
    }

    /**
     * Create main application
     *
     * @param context
     */
    public FoodBotApplication(final Context context) {
        this();
        attachBaseContext(context);
    }

    /**
     * Create main application
     *
     * @param instrumentation
     */
    public FoodBotApplication(final Instrumentation instrumentation) {
        this();
        attachBaseContext(instrumentation.getTargetContext());
    }

    /**
     * Sets the application injector. Using the {@link RoboGuice#newDefaultRoboModule} as well as a
     * custom binding module {@link FoodBotModule} to set up your application module
     *
     * @param application
     * @return
     */
    public static Injector setApplicationInjector(Application application) {
        return RoboGuice.setBaseApplicationInjector(application, Stage.DEVELOPMENT, RoboGuice.newDefaultRoboModule
                (application), new FoodBotModule());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        setApplicationInjector(this);
    }
}
