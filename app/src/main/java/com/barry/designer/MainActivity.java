package com.barry.designer;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.barry.designer.util.FileUtils;
import com.lzp.floatingactionbuttonplus.FabTagLayout;
import com.lzp.floatingactionbuttonplus.FloatingActionButtonPlus;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        FloatingActionButtonPlus fab = (FloatingActionButtonPlus) findViewById(R.id.FabPlus);
        fab.setOnItemClickListener(new FloatingActionButtonPlus.OnItemClickListener() {
            @Override
            public void onItemClick(FabTagLayout tagView, int position) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        break;
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        initUserView();
    }

    NavigationView navigationView = null;
    private void initUserView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ImageView ivHead = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.iv_head);
        ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });
        if (MyApplication.currUser != null) {

            TextView tvName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_name);
            tvName.setText(MyApplication.currUser.getName());

            //设置缓存的文件
            Bitmap userIcon = FileUtils.getUserIcon(MainActivity.this);
            if (userIcon!=null) {
                ivHead.setImageBitmap(userIcon);
            } else {
                ivHead.setImageResource(R.mipmap.ic_launcher);
            }
        }
    }

    /**
     * 退出登录
     */
    private void loginOut(View view) {

        FileUtils.delDatUser(MainActivity.this);
        Button btnExit = (Button) navigationView.getHeaderView(0).findViewById(R.id.btn_exit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GuiderActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    public void setContentFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.frag_content, fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Toast.makeText(MainActivity.this, "点击设置", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_tongzhi:
                Toast.makeText(MainActivity.this, "点击通知", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_xiaoxi:
                Toast.makeText(MainActivity.this, "点击消息", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_signout:
                FileUtils.delDatUser(MainActivity.this);
                Intent intent = new Intent(MainActivity.this, GuiderActivity.class);
                startActivity(intent);
                finish();

                return true;
            case R.id.action_about:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent intent = new Intent();
            intent.setClass(this, ShouYeActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, DiscoverActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
