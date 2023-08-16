package comtugas.bintangrestu.myrecipes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import comtugas.bintangrestu.myrecipes.databinding.ActivityMainBinding;
import comtugas.bintangrestu.myrecipes.ui.favorite.FavoriteFragment;
import comtugas.bintangrestu.myrecipes.ui.home.HomeFragment;
import comtugas.bintangrestu.myrecipes.ui.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    String idPengguna, namaLengkap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Terima data dari login.

        Intent intent = getIntent();
        namaLengkap = intent.getStringExtra("namaLengkap");
        idPengguna = intent.getStringExtra("idPengguna");

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_favorite, R.id.nav_logout, R.id.nav_exit)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                int id=item.getItemId();
                if(id == R.id.nav_exit){
                    System.exit(0);
                }else if(id == R.id.nav_logout){
                    finish();
                }else if(id == R.id.nav_favorite){
                    FavoriteFragment favoriteFragment = new FavoriteFragment();
                    Bundle data = new Bundle();//Use bundle to pass data
                    data.putString("namaLengkap", namaLengkap);
                    data.putString("idPengguna", idPengguna);
                    favoriteFragment.setArguments(data);
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, favoriteFragment).commit();
                }else if(id == R.id.nav_profile){
                    ProfileFragment profileFragment = new ProfileFragment();
                    Bundle data = new Bundle();//Use bundle to pass data
                    data.putString("namaLengkap", namaLengkap);
                    data.putString("idPengguna", idPengguna);
                    profileFragment.setArguments(data);
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, profileFragment).commit();
                }else if(id == R.id.nav_home){
                    HomeFragment homeFragment = new HomeFragment();
                    Bundle data = new Bundle();//Use bundle to pass data
                    data.putString("namaLengkap", namaLengkap);
                    data.putString("idPengguna", idPengguna);
                    homeFragment.setArguments(data);
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, homeFragment).commit();
                }
                return true;
            }
        });

        HomeFragment homeFragment = new HomeFragment();
        Bundle data = new Bundle();//Use bundle to pass data
        data.putString("namaLengkap", namaLengkap);
        data.putString("idPengguna", idPengguna);
        homeFragment.setArguments(data);
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, homeFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}