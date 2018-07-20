package ru.weblokos.madbrains;

import android.app.Application;

import ru.weblokos.madbrains.DB.DataBase;

public class App extends Application {
    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppExecutors = new AppExecutors();

    }

    public AppExecutors getExecutors() {
        return mAppExecutors;
    }

    public DataBase getDatabase() {
        return DataBase.getInstance(this, mAppExecutors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }
}
