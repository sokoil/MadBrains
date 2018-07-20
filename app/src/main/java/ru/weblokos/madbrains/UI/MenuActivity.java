package ru.weblokos.madbrains.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import ru.weblokos.madbrains.R;
import ru.weblokos.madbrains.UI.Backend.BackendProductListFragment;
import ru.weblokos.madbrains.UI.Frontend.FrontendProductListFragment;

public class MenuActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setUpUi();
    }

    private void setUpUi() {
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager mFragmentManager = getSupportFragmentManager();
                switch (item.getItemId()) {
                    case R.id.menu_back:
                        Fragment fragmentBack = new BackendProductListFragment();
                        if (fragmentBack != null) {
                            mFragmentManager.beginTransaction().replace(R.id.frame, fragmentBack).addToBackStack(null).commit();
                        }
                        break;
                    case R.id.menu_front:
                        Fragment fragmentFront = new FrontendProductListFragment();
                        if (fragmentFront != null) {
                            mFragmentManager.beginTransaction().replace(R.id.frame, fragmentFront).addToBackStack(null).commit();
                        }
                        break;
                }
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.menu_back);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
