package com.iss.flavr.presentation.home;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.iss.flavr.R;
import com.iss.flavr.data.repository.local.session.SessionManager;
import com.iss.flavr.presentation.home.recipelist.RecipeListFragment;
import com.iss.flavr.presentation.login.LoginActivity;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;



public class HomeActivity extends AppCompatActivity {

    Context context;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Drawer drawerResult;
    private Fragment fragment = new RecipeListFragment();
    private final String TAG = HomeActivity.class.getSimpleName();
    private int currentFragment;
    PrimaryDrawerItem recipes;
    PrimaryDrawerItem tracking;
    PrimaryDrawerItem logout;

    SessionManager sessionManager;
    //private Drawable icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        sessionManager = SessionManager.getInstance(this);

        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setUpToolbar();
        setUpNavDrawer();
        drawerResult.setSelection(R.string.nav_recipes);
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(10.0f);
    }

    //clean fragment back stack!
    private void cleanStack() {
        //Log.d(TAG, "Clean stack!");
        this.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    private void setUpNavDrawer() {

        new DrawerBuilder().withActivity(this).build();
        recipes = new PrimaryDrawerItem().withIdentifier(R.string.nav_recipes).withName(R.string.nav_recipes).withSelectable(true).withIcon(R.drawable.app_icon);
        tracking = new PrimaryDrawerItem().withIdentifier(R.string.nav_add_recipe).withName(R.string.nav_add_recipe).withSelectable(true).withIcon(R.drawable.plus);
        logout = new PrimaryDrawerItem().withIdentifier(R.string.nav_logout).withName(R.string.nav_logout).withSelectable(true).withIcon(R.drawable.nav_logout);


        //initialize and create the image loader logic
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(R.drawable.ronaldo).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }

        });

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.drawer_background)
                .withSelectionListEnabledForSingleProfile(false)
                .addProfiles(
                        new ProfileDrawerItem().withName("Lionel Messi")
                                .withEmail("yarex@gmail.com")
                                .withIcon(R.drawable.ronaldo)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        //launchActivity(getApplicationContext(), ProfileActivity.class);
                        return false;
                    }
                })
                .build();

        drawerResult = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        recipes,
                        tracking,
                        logout
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        cleanStack();
                        int id = (int) drawerItem.getIdentifier();

                        switch (id) {
                            default:
                                loadFragment(id);
                                break;

                            case R.string.nav_add_recipe:
                                return true;
                            case R.string.nav_logout:
                                startLogOutActivity();
                                return true;
                        }
                        return false;
                    }
                })
                .build();

    }



    public void clearToolbarMenu() {
        toolbar.getMenu().clear();
    }

    //handle on back pressed for all fragments
    @Override
    public void onBackPressed() {
        if (drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }



    public void startLogOutActivity() {
        sessionManager = SessionManager.getInstance(getApplicationContext());
        sessionManager.logOut();
       /* AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if (isLoggedIn) {
            LoginManager.getInstance().logOut();
        }
        FirebaseMessaging.getInstance().unsubscribeFromTopic("peru");
        FirebaseMessaging.getInstance().unsubscribeFromTopic("general");*/
        Intent loginIntent = new Intent().setClass(
                getApplicationContext(), LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(loginIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

   /* private void updateDrawerItems(){
        drawerResult.removeAllItems();
        drawerResult.addItems(worldCup,
                ranking,
                teams,
                addTeams,entertainment, photo, player, bet,
                communications, aboutUs, share, rateApp,
                account, profile, logout);
        setTeamItems();
    }*/

    /*private void setTeamItems(){
        // Get person teams from local db
        UserSQLiteOpenHelper user = new UserSQLiteOpenHelper(context, "user", null, 1);
        SQLiteDatabase db = user.getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT * FROM TEAMS ORDER BY NAME", null);
        if (c.moveToFirst()) {
            int p = 4;
            String itemIcon;
            do {
                itemIcon = "team_" + c.getInt(0);
                int id = getResources().getIdentifier(itemIcon, "drawable", getPackageName());
                PrimaryDrawerItem iTeam = new PrimaryDrawerItem().withIdentifier(c.getInt(0))
                        .withName(c.getString(1)).withSelectable(true).withIcon(id);
                drawerResult.addItemAtPosition(iTeam, p);
                p++;
            } while (c.moveToNext());
        }
        c.close();
        db.close();
    }*/

    private void loadFragment(int id) {
        currentFragment = id;
        if (!isFragmentVisible(id)) {
            switch (id) {
                case R.string.nav_recipes:
                    fragment = new RecipeListFragment();
                    break;

            }
            clearToolbarMenu();
            invalidateOptionsMenu();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.left_in, 0);
            ft.replace(R.id.content_layout, fragment, Integer.toString(id));
            ft.commit();
        }
    }

    //to know if a fragment is currently being shown on the screen
    public boolean isFragmentVisible(int tag) {
        Fragment f = getSupportFragmentManager().findFragmentByTag(Integer.toString(tag));
        if (f != null && f.isVisible()) return true;
        return false;
    }



    @Override
    protected void onResume() {
        super.onResume();
        if (drawerResult != null) drawerResult.closeDrawer();
        //updateDrawerItems();
    }

    private void launchActivity(Context origin, Class destiny) {
        Intent intent = new Intent().setClass(origin, destiny);
        startActivity(intent);
    }


}

