package com.boha.malengagolf.library.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.boha.malengagolf.library.R;
import com.boha.malengagolf.library.adapters.TourneyPlayerAdapter;
import com.boha.malengagolf.library.volley.toolbox.BaseVolley;
import com.boha.malengagolf.library.data.*;
import com.boha.malengagolf.library.util.*;

import java.util.List;

/**
 * Created by aubreyM on 2014/05/21.
 */
public class TournamentPlayerListFragment extends Fragment {
    public interface TournamentPlayerListListener {
        public void setBusy();
        public void setNotBusy();
        public void onScoringRequested(LeaderBoardDTO l);
    }

    TournamentPlayerListListener listener;

    @Override
    public void onAttach(Activity a) {
        if (a instanceof TournamentPlayerListListener) {
            listener = (TournamentPlayerListListener) a;
        } else {
            throw new UnsupportedOperationException("Host " + a.getLocalClassName() +
                    " must implement TournamentPlayerListListener");
        }

        Log.i(LOG,
                "onAttach ---- Fragment called and hosted by "
                        + a.getLocalClassName()
        );
        super.onAttach(a);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saved) {
        Log.e(LOG, "onCreateView ...");
        ctx = getActivity();
        inflater = getActivity().getLayoutInflater();
        view = inflater
                .inflate(R.layout.fragment_tournament_players, container, false);
        golfGroup = SharedUtil.getGolfGroup(ctx);

        setFields();
        return view;
    }

    private void getTournamentPlayers() {
        RequestDTO w = new RequestDTO();
        w.setRequestType(RequestDTO.GET_TOURNAMENT_PLAYERS);
        w.setTournamentID(tournament.getTournamentID());

        if (!BaseVolley.checkNetworkOnDevice(ctx)) {
            return;
        }
        listener.setBusy();
        WebSocketUtil.sendRequest(ctx,Statics.ADMIN_ENDPOINT,w,new WebSocketUtil.WebSocketListener() {
            @Override
            public void onMessage(ResponseDTO r) {
                listener.setNotBusy();
                if (!ErrorUtil.checkServerError(ctx, r)) {
                    return;
                }
                leaderBoardList = r.getLeaderBoardList();
                setList();
                checkScoringCompletion();
                CacheUtil.cacheData(ctx,r,CacheUtil.CACHE_TOURN_PLAYERS, tournament.getTournamentID(), new CacheUtil.CacheUtilListener() {
                    @Override
                    public void onFileDataDeserialized(ResponseDTO response) {

                    }

                    @Override
                    public void onDataCached() {

                    }
                });
            }

            @Override
            public void onClose() {

            }

            @Override
            public void onError(String message) {
                listener.setNotBusy();
                ErrorUtil.showServerCommsError(ctx);
            }

            @Override
            public void onSessionIDreceived(String sessionID) {
                SharedUtil.setSessionID(ctx, sessionID);
            }
        });
//        BaseVolley.getRemoteData(Statics.SERVLET_ADMIN, w, ctx, new BaseVolley.BohaVolleyListener() {
//            @Override
//            public void onResponseReceived(ResponseDTO r) {
//                listener.setNotBusy();
//                if (!ErrorUtil.checkServerError(ctx, r)) {
//                    return;
//                }
//                if (r.getLeaderBoardList().isEmpty()) {
//
//                }
//                leaderBoardList = r.getLeaderBoardList();
//                setList();
//                checkScoringCompletion();
//                CacheUtil.cacheData(ctx,r,CacheUtil.CACHE_TOURN_PLAYERS, tournament.getTournamentID(), new CacheUtil.CacheUtilListener() {
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
//                listener.setNotBusy();
//                ErrorUtil.showServerCommsError(ctx);
//            }
//        });
    }

    public void refresh(List<LeaderBoardDTO> list) {
        leaderBoardList = list;
        setList();
    }
    private void setList() {
        txtCount.setText("" + leaderBoardList.size());
        boolean useAgeGroups = false;
        if (tournament.getUseAgeGroups() == 1) useAgeGroups = true;
        adapter = new TourneyPlayerAdapter(ctx,
                R.layout.tourn_player_item,
                leaderBoardList,
                imageLoader,
                golfGroup.getGolfGroupID(),
                tournament.getGolfRounds(),
                tournament.getPar(), useAgeGroups, true, new TourneyPlayerAdapter.TourneyPlayerListener() {
            @Override
            public void onRemovePlayerRequested(LeaderBoardDTO l, int index) {
                leaderBoard = l;
                confirmRemoval(index);
            }

            @Override
            public void onScoringRequested(LeaderBoardDTO l) {
                leaderBoard = l;
                startScoring();
            }
        });
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                leaderBoard = leaderBoardList.get(i);
                selectedIndex = i;
                if (tournament.getClosedForScoringFlag() == 0)
                    startScoring();
            }
        });
        listView.setSelection(selectedIndex);
    }

    int selectedIndex;
    private void confirmRemoval(final int index) {
        AlertDialog.Builder diag = new AlertDialog.Builder(ctx);
        diag.setTitle(ctx.getResources().getString(R.string.remove_player))
                .setMessage(ctx.getResources().getString(R.string.remove_player_msg))
                .setPositiveButton(ctx.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        removePlayer(index);
                    }
                })
                .setNegativeButton(ctx.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }
    private void startScoring() {
        listener.onScoringRequested(leaderBoard);
    }



    private void checkScoringCompletion() {
        if (tournament.getClosedForScoringFlag() > 0) {
            return;
        }
        for (LeaderBoardDTO x : leaderBoardList) {
            CompleteRounds.markScoringCompletion(x);
        }
        int complete = 0;
        for (LeaderBoardDTO x : leaderBoardList) {
            if (x.isScoringComplete()) {
                complete++;
            }
        }


        if (complete == leaderBoardList.size() && leaderBoardList.size() > 0) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            if (tournament.getClosedForScoringFlag() == 0) {
                dialog
                        .setTitle(ctx.getResources().getString(R.string.player_scoring))
                        .setMessage(ctx.getResources().getString(R.string.close_scoring))
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {

                                closeTournamentForScoring();
                            }
                        }).create().show();
            }


        }
    }

    private void closeTournamentForScoring() {
        RequestDTO w = new RequestDTO();
        w.setRequestType(RequestDTO.UPDATE_TOURNAMENT);
        tournament.setClosedForScoringFlag(1);
        w.setTournament(tournament);
        if (!BaseVolley.checkNetworkOnDevice(ctx)) {
            return;
        }
        BaseVolley.getRemoteData(Statics.SERVLET_ADMIN, w, ctx, new BaseVolley.BohaVolleyListener() {
            @Override
            public void onResponseReceived(ResponseDTO response) {
                if (!ErrorUtil.checkServerError(ctx, response)) {
                    return;
                }
                Log.i(LOG, "closedForScoring flag updated");
            }

            @Override
            public void onVolleyError(VolleyError error) {
                ErrorUtil.showServerCommsError(ctx);
            }
        });
    }

    private void setFields() {
        txtTitle = (TextView) view.findViewById(R.id.TPLAYER_title);
        txtCount = (TextView) view.findViewById(R.id.TPLAYER_count);
        listView = (ListView) view.findViewById(R.id.TPLAYER_list);
    }

    private void removePlayer(int index) {

        leaderBoardList.remove(index);
        adapter.notifyDataSetChanged();
        txtCount.setText("" + leaderBoardList.size());

        if (!BaseVolley.checkNetworkOnDevice(ctx)) {
            return;
        }
        RequestDTO w = new RequestDTO();
        w.setRequestType(RequestDTO.REMOVE_TOURNAMENT_PLAYER);
        w.setTournamentID(tournament.getTournamentID());
        w.setPlayerID(leaderBoard.getPlayer().getPlayerID());
        listener.setBusy();
        BaseVolley.getRemoteData(Statics.SERVLET_ADMIN, w, ctx, new BaseVolley.BohaVolleyListener() {
            @Override
            public void onResponseReceived(ResponseDTO response) {
                listener.setNotBusy();
                if (!ErrorUtil.checkServerError(ctx, response)) {
                    return;
                }
            }

            @Override
            public void onVolleyError(VolleyError error) {
                listener.setNotBusy();
                ErrorUtil.showServerCommsError(ctx);
            }
        });
    }

    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    public void setTournament(TournamentDTO tournament) {
        this.tournament = tournament;
        txtTitle.setText(tournament.getTourneyName());
        CacheUtil.getCachedData(ctx,CacheUtil.CACHE_TOURN_PLAYERS,tournament.getTournamentID(),new CacheUtil.CacheUtilListener() {
            @Override
            public void onFileDataDeserialized(ResponseDTO response) {
                if (response != null) {
                    leaderBoardList = response.getLeaderBoardList();
                    setList();
                    //checkScoringCompletion();
                }
                getTournamentPlayers();
            }

            @Override
            public void onDataCached() {

            }
        });

    }

    static final String LOG = "TournamentPlayerListFragment";
    GolfGroupDTO golfGroup;
    Context ctx;
    View view;
    TextView txtTitle, txtCount;
    ListView listView;
    TournamentDTO tournament;
    List<LeaderBoardDTO> leaderBoardList;
    LeaderBoardDTO leaderBoard;
    TourneyPlayerAdapter adapter;
    ImageLoader imageLoader;

}
