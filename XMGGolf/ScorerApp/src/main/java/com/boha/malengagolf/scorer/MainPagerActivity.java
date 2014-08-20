package com.boha.malengagolf.scorer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.boha.malengagolf.library.*;
import com.boha.malengagolf.library.volley.toolbox.BaseVolley;
import com.boha.malengagolf.library.data.*;
import com.boha.malengagolf.library.fragments.GolfGroupTournamentListFragment;
import com.boha.malengagolf.library.fragments.SplashFragment;
import com.boha.malengagolf.library.util.*;
import org.acra.ACRA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by aubreyM on 2014/05/20.
 */
public class MainPagerActivity extends FragmentActivity implements GolfGroupTournamentListFragment.TournamentListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scorer_main_pager);
        ctx = getApplicationContext();
        golfGroup = SharedUtil.getGolfGroup(ctx);
        ACRA.getErrorReporter().putCustomData("golfGroupID", "" + golfGroup.getGolfGroupID());
        ACRA.getErrorReporter().putCustomData("golfGroupName", golfGroup.getGolfGroupName());
        mPager = (ViewPager) findViewById(R.id.pager);
        MGApp app = (MGApp) getApplication();
        imageLoader = app.getImageLoader();
        setTitle(ctx.getResources().getString(R.string.app_name));

    }

    private void buildPages() {
        pageFragmentList = new ArrayList<MGPageFragment>();

        tournamentListFragment = new GolfGroupTournamentListFragment();
        ResponseDTO w = new ResponseDTO();
        w.setTournaments(tournamentList);
        Bundle b = new Bundle();
        b.putSerializable("response", w);
        b.putSerializable("scorer",SharedUtil.getScorer(ctx));
        tournamentListFragment.setArguments(b);
        pageFragmentList.add(tournamentListFragment);

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                currentPageIndex = arg0;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @Override
    public void setBusy() {
        setRefreshActionButtonState(true);
    }

    @Override
    public void setNotBusy() {
        setRefreshActionButtonState(false);
    }

    TournamentDTO tournament;

    @Override
    public void onGalleryRequested(TournamentDTO t) {
        tournament = t;
        Intent w = new Intent(ctx, MGGalleryActivity.class);
        w.putExtra("tournament", tournament);
        startActivity(w);

    }




    static final String LOG = "MainPagerActivity-scorer";

    private class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {

            return (Fragment) pageFragmentList.get(i);
        }

        @Override
        public int getCount() {
            return pageFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = ctx.getResources().getString(R.string.tournaments);

            return title;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.scoring_main, menu);
        mMenu = menu;
//        menu.getItem(R.id.menu_camera).setVisible(false);
        CacheUtil.getCachedData(ctx, CacheUtil.CACHE_TOURNAMENTS, 0, new CacheUtil.CacheUtilListener() {
            @Override
            public void onFileDataDeserialized(ResponseDTO response) {
                if (response != null) {
                    tournamentList = response.getTournaments();
                    if (response.getTournaments() != null) {
                        buildPages();
                    }
                }
            }

            @Override
            public void onDataCached() {

            }
        });


        getTournaments();

        return true;
    }

    private void getTournaments() {
        RequestDTO w = new RequestDTO();
        w.setRequestType(RequestDTO.GET_TOURNAMENTS);
        w.setGolfGroupID(golfGroup.getGolfGroupID());

        if (!BaseVolley.checkNetworkOnDevice(ctx)) {
            return;
        }
        setRefreshActionButtonState(true);
        BaseVolley.getRemoteData(Statics.SERVLET_ADMIN, w, ctx, new BaseVolley.BohaVolleyListener() {
            @Override
            public void onResponseReceived(ResponseDTO response) {
                setRefreshActionButtonState(false);
                if (!ErrorUtil.checkServerError(ctx, response)) {
                    return;
                }
                tournamentList = response.getTournaments();
                for (TournamentDTO t: tournamentList) {
                    t.setSortType(TournamentDTO.SORT_BY_NEWEST_TOURNAMENT_ENTERED);
                }
                Collections.sort(tournamentList);
                if (response.getTournaments() != null) {
                    buildPages();
                }
                CacheUtil.cacheData(ctx, response, CacheUtil.CACHE_TOURNAMENTS, new CacheUtil.CacheUtilListener() {
                    @Override
                    public void onFileDataDeserialized(ResponseDTO response) {

                    }

                    @Override
                    public void onDataCached() {

                    }
                });
            }

            @Override
            public void onVolleyError(VolleyError error) {
                setRefreshActionButtonState(false);
                ErrorUtil.showServerCommsError(ctx);
            }
        });
    }

    public void setRefreshActionButtonState(final boolean refreshing) {
        if (mMenu != null) {
            final MenuItem refreshItem = mMenu.findItem(R.id.menu_refresh);
            if (refreshItem != null) {
                if (refreshing) {
                    refreshItem.setActionView(R.layout.action_bar_progess);
                } else {
                    refreshItem.setActionView(null);
                }
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_refresh:
                getTournaments();
                return true;
            case R.id.menu_camera:
                Intent z = new Intent(ctx, PictureActivity.class);
                z.putExtra("golfGroup", golfGroup);
                z.putExtra("tournament", tournament);
                startActivity(z);
                return true;

            case R.id.menu_clubs:
                Intent x = new Intent(ctx, GolfCourseMapActivity.class);
                startActivity(x);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onPause() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onPause();
    }

    Menu mMenu;
    Context ctx;
    ViewPager mPager;
    GolfGroupDTO golfGroup;
    ImageLoader imageLoader;
    List<MGPageFragment> pageFragmentList;
    PagerAdapter mPagerAdapter;
    GolfGroupTournamentListFragment tournamentListFragment;
    SplashFragment splashFragment;
    ResponseDTO response;
    int currentPageIndex;
    List<TournamentDTO> tournamentList;
}