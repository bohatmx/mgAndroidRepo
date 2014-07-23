package com.boha.malengagolf.library;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.boha.malengagolf.library.base.BaseVolley;
import com.boha.malengagolf.library.data.*;
import com.boha.malengagolf.library.fragments.SplashFragment;
import com.boha.malengagolf.library.fragments.StaggeredListener;
import com.boha.malengagolf.library.fragments.StaggeredPlayerGridFragment;
import com.boha.malengagolf.library.gallery.StaggeredTournamentGridFragment;
import com.boha.malengagolf.library.util.ErrorUtil;
import com.boha.malengagolf.library.util.MGPageFragment;
import com.boha.malengagolf.library.util.SharedUtil;
import com.boha.malengagolf.library.util.Statics;

import java.util.*;

/**
 * Created by aubreyM on 2014/04/22.
 */
public class MGGalleryActivity extends FragmentActivity implements StaggeredListener{
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scorer_main_pager);
        ctx = getApplicationContext();

        tournament = (TournamentDTO) getIntent().getSerializableExtra("tournament");
        golfGroup = SharedUtil.getGolfGroup(ctx);

        mPager = (ViewPager) findViewById(R.id.pager);
        MGApp app = (MGApp) getApplication();
        imageLoader = app.getImageLoader();
        setTitle(tournament.getTourneyName());
        setTimer();

        //TODO - stash leaderboardlist and fileNames in cache ..

    }
    Timer timer;
    private void setTimer() {
        timer = new Timer("timer", true);
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                Log.e(LOG,"############ Timer fired a request to refresh at " + new Date().toString());
                runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        getPlayerPictures();
                    }});
            }
        };
        timer.scheduleAtFixedRate(tt, DELAY, PERIOD);
    }
    static final String LOG = "MGGalleryActivity";
    List<String> fileNames;
    List<LeaderBoardDTO> leaderBoardList;
    SplashFragment splashFragment;
    int currentPageIndex;
    ImageLoader imageLoader;
    static final long DELAY = 60000, PERIOD = DELAY * 3;



    private void getPlayerPictures() {
        Log.e(LOG, ".....getPlayerPictures, really getting leaderboard");
        RequestDTO w = new RequestDTO();
        w.setRequestType(RequestDTO.GET_LEADERBOARD);
        w.setTournamentID(tournament.getTournamentID());
        w.setTournamentType(tournament.getTournamentType());
        setRefreshActionButtonState(true);
        BaseVolley.getRemoteData(Statics.SERVLET_ADMIN, w, ctx, new BaseVolley.BohaVolleyListener() {
            @Override
            public void onResponseReceived(ResponseDTO r) {
                setRefreshActionButtonState(false);
                if (!ErrorUtil.checkServerError(ctx, r)) {
                   return;
                }
                carriers = new ArrayList<LeaderBoardCarrierDTO>();
                if (tournament.getUseAgeGroups() == 0) {
                    LeaderBoardCarrierDTO c = new LeaderBoardCarrierDTO();
                    c.setLeaderBoardList(r.getLeaderBoardList());
                    carriers.add(c);
                } else {
                    carriers = r.getLeaderBoardCarriers();
                }
                buildPages();
                mPager.setCurrentItem(currentPageIndex, true);
                //TODO = check if scoring complete - kill timer
            }

            @Override
            public void onVolleyError(VolleyError error) {
                setRefreshActionButtonState(false);
                ErrorUtil.showServerCommsError(ctx);
            }
        });
    }

    List<LeaderBoardCarrierDTO> carriers;

    private  void buildPages() {
        pageFragmentList = new ArrayList<MGPageFragment>();
        staggeredTournamentGridFragment = new StaggeredTournamentGridFragment();
        Bundle b = new Bundle();
        b.putSerializable("tournament", tournament);
        staggeredTournamentGridFragment.setArguments(b);

        for (LeaderBoardCarrierDTO carrier: carriers) {
            if (carrier.getLeaderBoardList() == null || carrier.getLeaderBoardList().isEmpty()) continue;
            StaggeredPlayerGridFragment spgf = new StaggeredPlayerGridFragment();
            Bundle b2 = new Bundle();
            ResponseDTO w = new ResponseDTO();
            w.setLeaderBoardList(carrier.getLeaderBoardList());
            w.setTournaments(new ArrayList<TournamentDTO>());
            w.getTournaments().add(tournament);
            b2.putSerializable("response",w);
            spgf.setArguments(b2);
            pageFragmentList.add(spgf);
        }

        pageFragmentList.add(staggeredTournamentGridFragment);
        //
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

    @Override
    public void onPlayerTapped(LeaderBoardDTO lb, int index) {
        Intent w = new Intent(this,ScoreCardActivity.class);
        w.putExtra("leaderBoard", lb);
        startActivity(w);
    }

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
            String title = ctx.getResources().getString(R.string.tourn_pics);
            if (pageFragmentList.get(position) instanceof StaggeredPlayerGridFragment) {
                StaggeredPlayerGridFragment f = (StaggeredPlayerGridFragment)pageFragmentList.get(position);
                AgeGroupDTO ag = f.getAgeGroup();
                if (ag != null) {
                    title = ag.getGroupName();
                } else {
                    title = ctx.getResources().getString(R.string.leaderboard);
                }
            }
            if (pageFragmentList.get(position) instanceof StaggeredTournamentGridFragment) {
                title = ctx.getResources().getString(R.string.tourn_pics);
            }
            return title;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gallery, menu);
        mMenu = menu;
        getPlayerPictures();
        return true;
    }

    static final int CAMERA_REQUESTED = 5533;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAMERA_REQUESTED:
                if (resultCode == Activity.RESULT_OK) {
                    staggeredTournamentGridFragment.getTournamentPictures();
                }
                break;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_camera) {
            Intent x = new Intent(ctx, PictureActivity.class);
            x.putExtra("tournament", tournament);
            startActivityForResult(x, CAMERA_REQUESTED);
            return true;

        }
        if (item.getItemId() == R.id.menu_refresh) {
            getPlayerPictures();
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
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onPause();
    }

    StaggeredTournamentGridFragment staggeredTournamentGridFragment;
    Menu mMenu;
    Context ctx;
    TournamentDTO tournament;
    GolfGroupDTO golfGroup;
    ResponseDTO response;
    PagerAdapter mPagerAdapter;
    ViewPager mPager;
    List<MGPageFragment> pageFragmentList;
}