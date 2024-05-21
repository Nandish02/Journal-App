package androidsamples.java.journalapp;

import android.app.Application;

public class JournalApplication extends Application {//OnCreate called when application is launched.It usually outlives the activity
    @Override
    public void onCreate() {
        super.onCreate();
        JournalRepository.init(this);// Repository is initialised here when the app launches
    }
}
