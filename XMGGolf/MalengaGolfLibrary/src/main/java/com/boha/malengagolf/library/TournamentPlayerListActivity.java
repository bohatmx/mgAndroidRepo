package com.boha.malengagolf.library;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.android.volley.toolbox.ImageLoader;
import com.boha.malengagolf.library.data.GolfGroupDTO;
import com.boha.malengagolf.library.data.LeaderBoardDTO;
import com.boha.malengagolf.library.data.ResponseDTO;
import com.boha.malengagolf.library.data.TournamentDTO;
import com.boha.malengagolf.library.fragments.TournamentPlayerListFragment;
import com.boha.malengagolf.library.util.SharedUtil;
import com.boha.malengagolf.library.util.ToastUtil;

/**
 * Created by aubreyM on 2014/05/21.
 */
public class TournamentPlayerListActivity extends FragmentActivity implements TournamentPlayerListFragment.TournamentPlayerListListener {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);
        ctx = getApplicationContext();
        tournament = (TournamentDTO) getIntent().getSerializableExtra("tournament");
        golfGroup = SharedUtil.getGolfGroup(ctx);

        MGApp app = (MGApp) getApplication();
        imageLoader = app.getImageLoader();
        tournamentPlayerListFragment = (TournamentPlayerListFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment);
        tournamentPlayerListFragment.setImageLoader(imageLoader);
        tournamentPlayerListFragment.setTournament(tournament);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tournament_players, menu);
        mMenu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getTitle().toString().equalsIgnoreCase(ctx.getResources().getString(R.string.take_pic))) {
            Intent z = new Intent(ctx, PictureActivity.class);
            z.putExtra("golfGroup", golfGroup);
            z.putExtra("tournament", tournament);
            startActivity(z);
            return true;
        }


        if (item.getTitle().toString().equalsIgnoreCase(ctx.getResources().getString(R.string.help_me))) {
            ToastUtil.toast(ctx, "Under Construction");
            return true;
        }
        if (item.getTitle().toString().equalsIgnoreCase(ctx.getResources().getString(R.string.back))) {
            onBackPressed();
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public void setRefreshActionButtonState(final boolean refreshing) {
        if (mMenu != null) {
            final MenuItem refreshItem = mMenu.findItem(R.id.menu_camera);
            if (refreshItem != null) {
                if (refreshing) {
                    refreshItem.setActionView(R.layout.action_bar_progess);
                } else {
                    refreshItem.setActionView(null);
                }
            }
        }
    }

    TournamentPlayerListFragment tournamentPlayerListFragment;
    TournamentDTO tournament;
    GolfGroupDTO golfGroup;
    Context ctx;
    ImageLoader imageLoader;
    Menu mMenu;

    @Override
    public void setBusy() {
        setRefreshActionButtonState(true);
    }

    @Override
    public void setNotBusy() {
        setRefreshActionButtonState(false);
    }

    @Override
    public void onScoringRequested(LeaderBoardDTO l) {
        Intent w = new Intent(ctx, ScoringByHoleActivity.class);
        w.putExtra("leaderBoard", l);
        w.putExtra("tournament", tournament);
        startActivityForResult(w, REQUEST_SCORING);
    }
    static final int REQUEST_SCORING = 933;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_SCORING:
                if (resultCode == RESULT_OK) {
                    //refresh
                    ResponseDTO w = (ResponseDTO)data.getSerializableExtra("response");
                    tournamentPlayerListFragment.refresh(w.getLeaderBoardList());
                }
                break;
        }
    }
    @Override
    public void onPause() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onPause();
    }

}