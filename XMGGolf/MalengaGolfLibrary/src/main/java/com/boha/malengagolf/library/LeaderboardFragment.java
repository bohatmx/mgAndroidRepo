package com.boha.malengagolf.library;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.boha.malengagolf.library.adapters.LeaderboardOneRoundAdapter;
import com.boha.malengagolf.library.base.BaseVolley;
import com.boha.malengagolf.library.data.*;
import com.boha.malengagolf.library.util.*;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by aubreyM on 2014/04/09.
 */
public class LeaderboardFragment extends Fragment implements LeaderBoardPage {

    public interface LeaderboardListener {
        public void onRequestRefresh();

        public void setBusy();

        public void setNotBusy();
        public void onScoreCardRequested(LeaderBoardDTO leaderBoard);
    }

    LeaderboardListener leaderboardListener;
    LeaderBoardCarrierDTO leaderBoardCarrier;

    @Override
    public void onAttach(Activity a) {
        if (a instanceof LeaderboardListener) {
            leaderboardListener = (LeaderboardListener) a;
        } else {
            throw new UnsupportedOperationException("Host " + a.getLocalClassName() +
                    " must implement LeaderboardListener");
        }
        Log.i(LOG,
                "onAttach ---- Fragment called and hosted by "
                        + a.getLocalClassName()
        );
        super.onAttach(a);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saved) {
        Log.i(LOG, "onCreateView");
        ctx = getActivity();
        inflater = getActivity().getLayoutInflater();
        view = inflater
                .inflate(R.layout.fragment_leaderboard, container, false);
        setFields();
        Bundle bundle = getArguments();
        if (bundle != null) {
            tournament = (TournamentDTO) bundle.getSerializable("tournament");
            leaderBoardCarrier = (LeaderBoardCarrierDTO) bundle.getSerializable("carrier");
            setLeaderBoardList(tournament, leaderBoardCarrier);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle b) {
        b.putSerializable("response", response);
        super.onSaveInstanceState(b);
    }

    public void setFields() {
        listView = (ListView) view.findViewById(R.id.LB_list);
        txTourneyName = (TextView) view.findViewById(R.id.LB_tournName);
        txtClubName = (TextView) view.findViewById(R.id.LB_clubName);
        txtTimeStamp = (TextView) view.findViewById(R.id.LB_timeStamp);
        txtPlayerCount = (TextView) view.findViewById(R.id.LB_count);
        txtAverage = (TextView) view.findViewById(R.id.LB_average);
        txtLive = (TextView) view.findViewById(R.id.LB_live);
        txtComplete = (TextView) view.findViewById(R.id.LB_complete);
    }

    List<LeaderBoardDTO> goodList;
    ImageLoader imageLoader;

    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
        Log.w(LOG, "imageLoader has been set ......");
    }

    private void setLeaderBoardLive() {


//        for (LeaderBoardDTO x : leaderBoardList) {
//            CompleteRounds.markScoringCompletion(x);
//        }
//        for (LeaderBoardDTO x : leaderBoardList) {
//            int cnt = 0;
//            for (TourneyScoreByRoundDTO v : x.getTourneyScoreByRoundList()) {
//                if (v.getScoringComplete() == 0) {
//                    cnt++;
//                }
//            }
//            if (cnt == 0) {
//                x.setScoringComplete(true);
//            }
//        }
        int cnt = 0;
        for (LeaderBoardDTO x : leaderBoardList) {
            if (x.isScoringComplete()) {
                cnt++;
            }
        }

        if (leaderBoardList.size() == (cnt)) {
            txtComplete.setVisibility(View.VISIBLE);
            txtLive.setVisibility(View.GONE);
            txtComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showCloseConfirmDialog();
                }
            });
        } else {
            txtComplete.setVisibility(View.GONE);
            txtLive.setVisibility(View.VISIBLE);
        }
        if (tournament.getClosedForScoringFlag() > 0) {
            txtComplete.setText(ctx.getResources().getString(R.string.closed));
            txtComplete.setBackgroundColor(ctx.getResources().getColor(R.color.black));
            return;
        }
    }
    private void showWithdrawConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(ctx.getResources().getString(R.string.withdraw))
                .setMessage(ctx.getResources().getString(R.string.withdraw_text)
                        + "\n\n" + leaderBoard.getPlayer().getFullName())
                .setPositiveButton(ctx.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        withdrawPlayer();
                    }
                })
                .setNegativeButton(ctx.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }

    private void withdrawPlayer() {

        leaderBoardList.remove(selectedIndex);
        txtPlayerCount.setText("" + leaderBoardList.size());
        if (leaderboardAdapter != null) {
            leaderboardAdapter.notifyDataSetChanged();
        }
        if (leaderboardOneRoundAdapter != null) {
            leaderboardOneRoundAdapter.notifyDataSetChanged();
        }
        setLeaderBoardLive();

        RequestDTO w = new RequestDTO();
        w.setRequestType(RequestDTO.WITHDRAW_PLAYER);
        w.setTournamentID(tournament.getTournamentID());
        w.setLeaderBoardID(leaderBoard.getLeaderBoardID());
        if (!BaseVolley.checkNetworkOnDevice(ctx)) {
            return;
        }
        leaderboardListener.setBusy();

        BaseVolley.getRemoteData(Statics.SERVLET_ADMIN,w,ctx,new BaseVolley.BohaVolleyListener() {
            @Override
            public void onResponseReceived(ResponseDTO response) {
                leaderboardListener.setNotBusy();
                if (!ErrorUtil.checkServerError(ctx,response)) {
                    return;
                }
                ToastUtil.toast(ctx,ctx.getResources().getString(R.string.withdrawn));

            }

            @Override
            public void onVolleyError(VolleyError error) {
                leaderboardListener.setNotBusy();
                ErrorUtil.showServerCommsError(ctx);
            }
        });
    }


    private void showCloseConfirmDialog() {
        if (tournament.getClosedForScoringFlag() == 1) {
            txtComplete.setText(ctx.getResources().getString(R.string.complete));
            txtComplete.setBackgroundColor(ctx.getResources().getColor(R.color.green));
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(ctx.getResources().getString(R.string.close_leaderboard))
                .setMessage(ctx.getResources().getString(R.string.close_leaderboard_text))
                .setPositiveButton(ctx.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tournament.setClosedForScoringFlag(1);
                        closeLeaderBoard();
                    }
                })
                .setNegativeButton(ctx.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }
    private void closeLeaderBoard() {
        RequestDTO w = new RequestDTO();
        w.setRequestType(RequestDTO.CLOSE_LEADERBORD);
        w.setTournamentID(tournament.getTournamentID());

        if (!BaseVolley.checkNetworkOnDevice(ctx)) {
            return;
        }
        leaderboardListener.setBusy();
        BaseVolley.getRemoteData(Statics.SERVLET_ADMIN,w,ctx,new BaseVolley.BohaVolleyListener() {
            @Override
            public void onResponseReceived(ResponseDTO response) {
                leaderboardListener.setNotBusy();
                if (!ErrorUtil.checkServerError(ctx, response)) {
                    return;
                }

                txtComplete.setText(ctx.getResources().getString(R.string.closed));
                txtComplete.setBackgroundColor(ctx.getResources().getColor(R.color.black));
            }

            @Override
            public void onVolleyError(VolleyError error) {
                leaderboardListener.setNotBusy();
                ErrorUtil.showServerCommsError(ctx);
            }
        });
    }

    int selectedIndex;
    static final Locale loc = Locale.getDefault();
    static final SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMMM yyyy HH:mm", loc);
    public void setList() {

        Log.i(LOG, "setList............. ");
        goodList = new ArrayList<LeaderBoardDTO>();
        List<LeaderBoardDTO> bList = new ArrayList<LeaderBoardDTO>();
        for (LeaderBoardDTO x : leaderBoardList) {
            if (x.getParStatus() == LeaderBoardDTO.NO_PAR_STATUS) {
                bList.add(x);
            } else {
                goodList.add(x);
            }
        }
        setPositions(goodList);
        goodList.addAll(bList);
        leaderBoardList = null;
        leaderBoardList = goodList;
        if (!leaderBoardList.isEmpty()) {
            long time = leaderBoardList.get(0).getTimeStamp();
            txtTimeStamp.setText(sdf.format(new Date(time)));
        }
        setLeaderBoardLive();

        if (tournament.getGolfRounds() == 1) {
            leaderboardOneRoundAdapter = new LeaderboardOneRoundAdapter(ctx,
                    R.layout.leaderboard_one_round,
                    leaderBoardList,
                    SharedUtil.getGolfGroup(ctx).getGolfGroupID(),
                    imageLoader);
            listView.setAdapter(leaderboardOneRoundAdapter);
        } else {
            leaderboardAdapter = new LeaderboardAdapter(ctx,
                    R.layout.leaderboard_item,
                    leaderBoardList,
                    tournament.getGolfRounds(), tournament.getPar(), imageLoader);
            listView.setAdapter(leaderboardAdapter);
        }
        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                leaderBoard = leaderBoardList.get(i);
                startScorecard();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                leaderBoard = leaderBoardList.get(i);
                selectedIndex = i;
                Log.i(LOG, "leaderboard selected onItemLongClick: " + leaderBoard.getPlayer().getFullName());
                return false;
            }
        });
        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                leaderBoard = leaderBoardList.get(i);
                selectedIndex = i;
                Log.i(LOG, "leaderboard selected onItemSelected: " + leaderBoard.getPlayer().getFullName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setPositions(List<LeaderBoardDTO> list) {
        int mPosition = 1;
        int running = 1, score = 999;
        switch(tournament.getTournamentType()) {
            case RequestDTO.STABLEFORD_INDIVIDUAL:
                for (LeaderBoardDTO lb : list) {
                    if (lb.isTied()) {
                        if (score == 999) {
                            score = lb.getTotalPoints();
                            mPosition = running;
                            lb.setPosition(mPosition);
                        } else {
                            if (score == lb.getTotalPoints()) {
                                lb.setPosition(mPosition);
                            } else {
                                score = lb.getTotalPoints();
                                mPosition = running;
                                lb.setPosition(mPosition);
                            }
                        }

                    } else {
                        score = 999;
                        lb.setPosition(running);
                    }

                    running++;
                }
                break;
            case RequestDTO.STROKE_PLAY_INDIVIDUAL:
                for (LeaderBoardDTO lb : list) {
                    if (lb.isTied()) {
                        if (score == 999) {
                            score = lb.getParStatus();
                            mPosition = running;
                            lb.setPosition(mPosition);
                        } else {
                            if (score == lb.getParStatus()) {
                                lb.setPosition(mPosition);
                            } else {
                                score = lb.getParStatus();
                                mPosition = running;
                                lb.setPosition(mPosition);
                            }
                        }

                    } else {
                        score = 999;
                        lb.setPosition(running);
                    }

                    running++;
                }
                break;
        }

    }

    public void setLeaderBoardList(TournamentDTO tournament, LeaderBoardCarrierDTO carrier) {
        this.tournament = tournament;
        this.leaderBoardCarrier = carrier;
        txTourneyName.setText(tournament.getTourneyName());
        txtClubName.setText(tournament.getClubName());

        this.leaderBoardList = carrier.getLeaderBoardList();
        txtPlayerCount.setText("" + leaderBoardList.size());
        getAverage();
        int cnt = 0;
        for (LeaderBoardDTO x : leaderBoardList) {
            if (x.getAgeGroup() == null) cnt++;
        }
        if (leaderBoardList.size() == cnt) { //combined leaderboard

        }
        positionWinner();
        setList();

    }

    List<CompleteRounds> completeRoundsList;

    private void getAverage() {
        completeRoundsList = CompleteRounds.getCompletedRounds(leaderBoardList);
        if (!completeRoundsList.isEmpty()) {
            double t = 0;
            for (CompleteRounds pav : completeRoundsList) {
                t += pav.getAverage();
            }
            t = t / completeRoundsList.size();
            txtAverage.setText(df.format(t));
        } else {
            txtAverage.setText("N/A");
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.leaderboard_context, menu);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        if (leaderBoard == null) {
            throw new UnsupportedOperationException("Leaderboard is null onCreateContextMenu. aborting...");
        }
        menu.setHeaderTitle(leaderBoard.getPlayer().getFullName());
        menu.setHeaderIcon(R.drawable.winner_red32);

        if (leaderBoard.getPosition() == 1 && leaderBoard.isTied()) {
            menu.getItem(0).setVisible(true);
            menu.getItem(1).setVisible(true);
        } else {
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(false);
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        Log.w(LOG,
                "onContextItemSelected - select option ..." + item.getTitle());

        int winnerFlag = 0;
        if (item.getTitle().toString().equalsIgnoreCase(
                ctx.getResources().getString(R.string.winner_countout))) {
            winnerFlag = LeaderBoardDTO.WINNER_BY_COUNT_OUT;
            updateWinner(winnerFlag);
        }
        if (item.getTitle().toString().equalsIgnoreCase(
                ctx.getResources().getString(R.string.winner_playoff))) {
            winnerFlag = LeaderBoardDTO.WINNER_BY_PLAYOFF;
            updateWinner(winnerFlag);
        }
        if (item.getTitle().toString().equalsIgnoreCase(
                ctx.getResources().getString(R.string.view_scorecard))) {
            startScorecard();
        }
        if (item.getTitle().toString().equalsIgnoreCase(
                ctx.getResources().getString(R.string.withdraw))) {
            showWithdrawConfirmDialog();
        }

        return true;

    }

    private void positionWinner() {
        List<LeaderBoardDTO> gList = new ArrayList<LeaderBoardDTO>();
        int index = 0;
        for (LeaderBoardDTO x : leaderBoardList) {
            if (x.getWinnerFlag() > 0 && x.isTied()) {
                gList.add(x);
                break;
            }
            index++;
        }
        if (gList.size() > 0) {
            leaderBoardList.remove(index);
            gList.addAll(leaderBoardList);
            leaderBoardList = gList;
        }
    }

    private void updateWinner(int winnerFlag) {

        //TODO set winner flag visually and put winner top of list
        leaderBoard.setWinnerFlag(winnerFlag);
        for (LeaderBoardDTO x : leaderBoardList) {
            x.setWinnerFlag(0);
        }
        List<LeaderBoardDTO> gList = new ArrayList<LeaderBoardDTO>();
        int index = 0;
        for (LeaderBoardDTO x : leaderBoardList) {
            if (leaderBoard.getLeaderBoardID() == x.getLeaderBoardID()) {
                x.setWinnerFlag(winnerFlag);
                gList.add(x);
                break;
            }
            index++;
        }
        leaderBoardList.remove(index);
        gList.addAll(leaderBoardList);
        leaderBoardList = gList;
        setList();

        RequestDTO w = new RequestDTO();
        w.setRequestType(RequestDTO.UPDATE_WINNER_FLAG);
        w.setLeaderBoardID(leaderBoard.getLeaderBoardID());
        w.setWinnerFlag(winnerFlag);
        w.setZippedResponse(false);
        leaderboardListener.setBusy();
        if (!BaseVolley.checkNetworkOnDevice(ctx)) {
            return;
        }
        BaseVolley.getRemoteData(Statics.SERVLET_ADMIN, w, ctx, new BaseVolley.BohaVolleyListener() {
            @Override
            public void onResponseReceived(ResponseDTO response) {
                leaderboardListener.setNotBusy();
                if (!ErrorUtil.checkServerError(ctx, response)) {
                    return;
                }

                ToastUtil.toast(ctx, ctx.getResources().getString(R.string.winner_updated));
            }

            @Override
            public void onVolleyError(VolleyError error) {
                leaderboardListener.setNotBusy();
                ErrorUtil.showServerCommsError(ctx);
            }
        });
    }

    private void startScorecard() {
        Log.e(LOG, "startScorecard: useDialogForScorecard = "
                + useDialogForScorecard);
        if (!useDialogForScorecard) {
            Intent s = new Intent(ctx, ScoreCardActivity.class);
            s.putExtra("leaderBoard", leaderBoard);
            startActivity(s);
        } else {
            leaderboardListener.onScoreCardRequested(leaderBoard);

        }
    }

    static final DecimalFormat df = new DecimalFormat("###,###.00");
    double overallAverage;
    LeaderboardAdapter leaderboardAdapter;
    LeaderboardOneRoundAdapter leaderboardOneRoundAdapter;
    FragmentManager fragmentManager;
    ListView listView;
    TextView txTourneyName, txtClubName, txtPlayerCount,
            txtTimeStamp, txtAverage, txtLive, txtComplete;
    static final String LOG = "LeaderboardFragment";
    Context ctx;
    View view;
    TournamentDTO tournament;
    ResponseDTO response;
    List<LeaderBoardDTO> leaderBoardList;
    LeaderBoardDTO leaderBoard;
    boolean useDialogForScorecard;


    public void setUseDialogForScorecard(boolean useDialogForScorecard) {
        this.useDialogForScorecard = useDialogForScorecard;
        Log.d(LOG, "setUseDialogForScorecard " + useDialogForScorecard);
    }

    public LeaderBoardCarrierDTO getLeaderBoardCarrier() {
        return leaderBoardCarrier;
    }

}
