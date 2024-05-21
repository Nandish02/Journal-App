package androidsamples.java.journalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

  interface BackPress {
    boolean onBackPressed();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
    Fragment fragment = (currentFragment == null) ? new EntryListFragment() : currentFragment;
    getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, fragment).commit();
  }
}
