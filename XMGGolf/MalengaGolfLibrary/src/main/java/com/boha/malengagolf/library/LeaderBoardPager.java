package com.boha.malengagolf.library;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.boha.malengagolf.library.volley.toolbox.BaseVolley;
import com.boha.malengagolf.library.data.*;
import com.boha.malengagolf.library.fragments.LeaderBoardSplashFragment;
import com.boha.malengagolf.library.util.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by aubreyM on 2014/04/19.
 */
public class LeaderBoardPager extends FragmentActivity
        implements LeaderboardFragment.LeaderboardListener {
    public void onCreate(Bundle savedInstanceState) {
        Log.e(LOG, "################ onCreate ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_pager);
        ctx = getApplicationContext();
        mPager = (ViewPager) findViewById(R.id.pager);
        tournament = (TournamentDTO) getIntent().getSerializableExtra("tournament");
        golfGroup = SharedUtil.getGolfGroup(ctx);
        MGApp app = (MGApp) getApplication();
        imageLoader = app.getImageLoader();

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

    }

    private ImageLoader imageLoader;
    private GolfGroupDTO golfGroup;
    private List<LeaderBoardCarrierDTO> carrierList;
    LeaderBoardSplashFragment splashFragment;

    private void setSplashFrament() {
        Log.e(LOG, "################ setSplashFrament ");
        leaderBoardPages = new ArrayList<LeaderBoardPage>();
        splashFragment = new LeaderBoardSplashFragment();
        leaderBoardPages.add(splashFragment);
        initializeAdapter();

    }

    private void buildPages() {
        Log.w(LOG, "#########################..................buildPages........");
        carrierList = new ArrayList<LeaderBoardCarrierDTO>();
        if (response.getLeaderBoardCarriers() == null && response.getLeaderBoardList() == null) return;
        if (response.getLeaderBoardCarriers() == null) {
            response.setLeaderBoardCarriers(new ArrayList<LeaderBoardCarrierDTO>());
            LeaderBoardCarrierDTO carrier = new LeaderBoardCarrierDTO();
            carrier.setLeaderBoardList(response.getLeaderBoardList());
            response.getLeaderBoardCarriers().add(carrier);
        }
        for (LeaderBoardCarrierDTO lc : response.getLeaderBoardCarriers()) {
            if (lc.getLeaderBoardList().isEmpty()) {
                continue;
            }
            carrierList.add(lc);
        }

        Collections.sort(carrierList);
        for (LeaderBoardCarrierDTO carrierDTO : carrierList) {
            LeaderboardFragment fragment = new LeaderboardFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("tournament", tournament);
            bundle.putSerializable("carrier", carrierDTO);
            fragment.setArguments(bundle);
            fragment.setImageLoader(imageLoader);
            leaderBoardPages.add(fragment);

        }

        initializeAdapter();
        mPager.setCurrentItem(1, true);
    }

    private void initializeAdapter() {
        Log.e(LOG, "################ initializeAdapter ");
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    private void refreshLeaderBoard() {
        Log.w(LOG, "################,.......... refreshLeaderBoard ");
        mPager.setCurrentItem(0, true);
        RequestDTO w = new RequestDTO();
        w.setRequestType(RequestDTO.GET_LEADERBOARD);
        w.setTournamentID(tournament.getTournamentID());
        w.setTournamentType(tournament.getTournamentType());

        if (!BaseVolley.checkNetworkOnDevice(ctx)) {
            return;
        }
        setRefreshActionButtonState(true);
        BaseVolley.getRemoteData(Statics.SERVLET_ADMIN, w, ctx, new BaseVolley.BohaVolleyListener() {
            @Override
            public void onResponseReceived(ResponseDTO r) {
                setRefreshActionButtonState(false);
                if (!ErrorUtil.checkServerError(ctx, r)) {
                    return;
                }
                response = r;

                if (tournament.getUseAgeGroups() == 0) {
                    for (LeaderBoardDTO d : response.getLeaderBoardList()) {
                        d.setTimeStamp(new Date().getTime());
                    }

                    CacheUtil.cacheData(ctx, response, CacheUtil.CACHE_LEADER_BOARD, tournament.getTournamentID(), new CacheUtil.CacheUtilListener() {
                        @Override
                        public void onFileDataDeserialized(ResponseDTO response) {

                        }

                        @Override
                        public void onDataCached() {

                        }
                    });
                } else {
                    for (LeaderBoardCarrierDTO c : response.getLeaderBoardCarriers()) {
                        for (LeaderBoardDTO d : c.getLeaderBoardList()) {
                            d.setTimeStamp(new Date().getTime());
                        }
                    }
                    CacheUtil.cacheData(ctx, response, CacheUtil.CACHE_LEADERBOARD_CARRIERS, tournament.getTournamentID(), new CacheUtil.CacheUtilListener() {
                        @Override
                        public void onFileDataDeserialized(ResponseDTO response) {

                        }

                        @Override
                        public void onDataCached() {

                        }
                    });
                }

                setSplashFrament();
                buildPages();

            }

            @Override
            public void onVolleyError(VolleyError error) {
                setRefreshActionButtonState(false);
                ErrorUtil.showServerCommsError(ctx);
            }
        });

    }

    @Override
    public void onRequestRefresh() {
        refreshLeaderBoard();
    }

    @Override
    public void setBusy() {
        setRefreshActionButtonState(true);
    }

    @Override
    public void setNotBusy() {
        setRefreshActionButtonState(false);
    }

    @Override
    public void onScoreCardRequested(LeaderBoardDTO leaderBoard) {

    }

    private class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {

            return (Fragment) leaderBoardPages.get(i);
        }

        @Override
        public int getCount() {
            return leaderBoardPages.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = "Title";
            if (position == 0 && tournament != null) {
                return tournament.getTourneyName();
            }
            if (carrierList == null) return "";
            try {
                LeaderBoardCarrierDTO x = (LeaderBoardCarrierDTO)
                        carrierList.get(position - 1);
                if (x == null) {
                    Log.e(LOG, "carrier is null at position " + position);
                } else {
                    if (x.getAgeGroup() == null) {
                        if (tournament.getUseAgeGroups() == 0) {
                            title = ctx.getResources().getString(R.string.leaderboard);
                        } else {
                            title = ctx.getResources().getString(R.string.combined_leaderboard);
                        }
                    } else {
                        title = x.getAgeGroup().getGroupName();
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                title = "";
            }
            return title;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.e(LOG, "################ onCreateOptionsMenu ");
        getMenuInflater().inflate(R.menu.leaderboard_pager, menu);
        mMenu = menu;
        setSplashFrament();
        if (tournament.getUseAgeGroups() == 0) {
            CacheUtil.getCachedData(ctx, CacheUtil.CACHE_LEADER_BOARD, tournament.getTournamentID(), new CacheUtil.CacheUtilListener() {
                @Override
                public void onFileDataDeserialized(ResponseDTO r) {
                    if (r != null) {
                        response = r;
                        response.setLeaderBoardCarriers(new ArrayList<LeaderBoardCarrierDTO>());
                        LeaderBoardCarrierDTO carrier = new LeaderBoardCarrierDTO();
                        carrier.setLeaderBoardList(r.getLeaderBoardList());
                        response.getLeaderBoardCarriers().add(carrier);
                        buildPages();
                    }

                    refreshLeaderBoard();
                }

                @Override
                public void onDataCached() {

                }
            });
        } else {
            CacheUtil.getCachedData(ctx, CacheUtil.CACHE_LEADERBOARD_CARRIERS, tournament.getTournamentID(), new CacheUtil.CacheUtilListener() {
                @Override
                public void onFileDataDeserialized(ResponseDTO r) {
                    response = r;
                    if (r != null) {
                        buildPages();
                    }

                    refreshLeaderBoard();
                }

                @Override
                public void onDataCached() {

                }
            });
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_camera) {
            Intent z = new Intent(ctx, PictureActivity.class);
            z.putExtra("tournament", tournament);
            startActivity(z);
            return true;
        }
        if (item.getItemId() == R.id.menu_refresh) {
            refreshLeaderBoard();
            return true;
        }

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
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
    public void onPause() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onPause();
    }

    static final String LOG = "LeaderboardPager";
    ResponseDTO response;
    Menu mMenu;
    TournamentDTO tournament;
    Context ctx;
    List<LeaderBoardPage> leaderBoardPages;
    List<LeaderBoardCarrierDTO> carriers;
    ViewPager mPager;
    PagerAdapter pagerAdapter;
    PagerTitleStrip pagerTitleStrip;
}