package com.boha.malengagolf.scorer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.toolbox.ImageLoader;
import com.boha.malengagolf.library.GolfCourseMapActivity;
import com.boha.malengagolf.library.MGApp;
import com.boha.malengagolf.library.MGGalleryActivity;
import com.boha.malengagolf.library.PictureActivity;
import com.boha.malengagolf.library.data.GolfGroupDTO;
import com.boha.malengagolf.library.data.RequestDTO;
import com.boha.malengagolf.library.data.ResponseDTO;
import com.boha.malengagolf.library.data.ScorerDTO;
import com.boha.malengagolf.library.data.TournamentDTO;
import com.boha.malengagolf.library.fragments.GolfGroupTournamentListFragment;
import com.boha.malengagolf.library.fragments.SplashFragment;
import com.boha.malengagolf.library.util.CacheUtil;
import com.boha.malengagolf.library.util.ErrorUtil;
import com.boha.malengagolf.library.util.MGPageFragment;
import com.boha.malengagolf.library.util.SharedUtil;
import com.boha.malengagolf.library.util.Statics;
import com.boha.malengagolf.library.util.ToastUtil;
import com.boha.malengagolf.library.util.WebSocketUtil;
import com.boha.malengagolf.library.volley.toolbox.BaseVolley;

import org.acra.ACRA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by aubreyM on 2014/05/20.
 */
public class MainPagerActivity extends AppCompatActivity implements GolfGroupTournamentListFragment.TournamentListener {
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
        //
        setTitle(golfGroup.getGolfGroupName());
        ScorerDTO s = SharedUtil.getScorer(ctx);
        getSupportActionBar().setSubtitle(s.getFullName());

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
            String title = ctx.getResources().getString(R.string.tournaments)
                    + " (" + tournamentList.size() + ")";

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
                getTournaments();
            }

            @Override
            public void onDataCached() {

            }
        });




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
        WebSocketUtil.sendRequest(ctx,Statics.ADMIN_ENDPOINT,w, new WebSocketUtil.WebSocketListener() {
            @Override
            public void onMessage(final ResponseDTO response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setRefreshActionButtonState(false);
                        if (!ErrorUtil.checkServerError(ctx, response)) {
                            return;
                        }
                        if (response.getTournaments() == null) {
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
                });
            }

            @Override
            public void onClose() {

            }

            @Override
            public void onError(String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setRefreshActionButtonState(false);
                        ErrorUtil.showServerCommsError(ctx);
                    }
                });
            }


        });
//        BaseVolley.getRemoteData(Statics.SERVLET_ADMIN, w, ctx, new BaseVolley.BohaVolleyListener() {
//            @Override
//            public void onResponseReceived(ResponseDTO response) {
//                setRefreshActionButtonState(false);
//                if (!ErrorUtil.checkServerError(ctx, response)) {
//                    return;
//                }
//                tournamentList = response.getTournaments();
//                for (TournamentDTO t: tournamentList) {
//                    t.setSortType(TournamentDTO.SORT_BY_NEWEST_TOURNAMENT_ENTERED);
//                }
//                Collections.sort(tournamentList);
//                if (response.getTournaments() != null) {
//                    buildPages();
//                }
//                CacheUtil.cacheData(ctx, response, CacheUtil.CACHE_TOURNAMENTS, new CacheUtil.CacheUtilListener() {
//                    @Override
//                    public void onFileDataDeserialized(ResponseDTO response) {
//
//                    }
//
//                    @Override
//                    public void onDataCached() {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onVolleyError(VolleyError error) {
//                setRefreshActionButtonState(false);
//                ErrorUtil.showServerCommsError(ctx);
//            }
//        });
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
            case R.id.menu_help:
                ToastUtil.toast(ctx,ctx.getResources().getString(R.string.under_cons));
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