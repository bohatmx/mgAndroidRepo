package com.boha.malengagolf.library.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.boha.malengagolf.library.R;
import com.boha.malengagolf.library.ScoreCardActivity;
import com.boha.malengagolf.library.adapters.PlayerHistoryAdapter;
import com.boha.malengagolf.library.base.BaseVolley;
import com.boha.malengagolf.library.data.LeaderBoardDTO;
import com.boha.malengagolf.library.data.PlayerDTO;
import com.boha.malengagolf.library.data.RequestDTO;
import com.boha.malengagolf.library.data.ResponseDTO;
import com.boha.malengagolf.library.util.ErrorUtil;
import com.boha.malengagolf.library.util.MGPageFragment;
import com.boha.malengagolf.library.util.Statics;

import java.util.ArrayList;
import java.util.List;
/* COPYRIGHT (C) 1997 Aubrey Malabie. All Rights Reserved. */
/**
 * Created by aubreyM on 2014/04/09.
 */
public class PlayerHistoryFragment extends Fragment implements MGPageFragment {

    @Override
    public void onAttach(Activity a) {

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
                .inflate(R.layout.fragment_player_history, container, false);
        setFields();
        Bundle b = getArguments();
        if (b != null) {
            player = (PlayerDTO)b.getSerializable("player");
            getPlayerHistory();
        }

        return view;
    }
    private void getPlayerHistory() {
        RequestDTO z = new RequestDTO();
        z.setRequestType(RequestDTO.GET_PLAYER_HISTORY);
        z.setPlayerID(player.getPlayerID());

        if (!BaseVolley.checkNetworkOnDevice(ctx)) {
            return;
        }
        BaseVolley.getRemoteData(Statics.SERVLET_ADMIN,z,ctx, new BaseVolley.BohaVolleyListener() {
            @Override
            public void onResponseReceived(ResponseDTO response) {
                if (!ErrorUtil.checkServerError(ctx, response)) {
                    return;
                }
                leaderBoardList = response.getLeaderBoardList();
                if (leaderBoardList == null) {
                    leaderBoardList = new ArrayList<LeaderBoardDTO>();
                }
                setList();
            }

            @Override
            public void onVolleyError(VolleyError error) {
                ErrorUtil.showServerCommsError(ctx);
            }
        });
    }
    @Override
    public void onSaveInstanceState(Bundle b) {
        super.onSaveInstanceState(b);
    }

    public void setFields() {
        listView = (ListView) view.findViewById(R.id.PH_FRAG_list);
        txtPlayerName = (TextView) view.findViewById(R.id.PH_FRAG_playerName);
        txtTourneyCount = (TextView) view.findViewById(R.id.PH_FRAG_count);
    }

    public void setList() {
        Log.i(LOG, "setList");
        txtPlayerName.setText(player.getFullName());
        txtTourneyCount.setText("" + leaderBoardList.size());
        playerHistoryAdapter = new PlayerHistoryAdapter(ctx, R.layout.player_history_item, leaderBoardList);
        listView.setAdapter(playerHistoryAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                leaderBoard = leaderBoardList.get(i);
                Intent xx = new Intent(ctx, ScoreCardActivity.class);
                xx.putExtra("leaderBoard", leaderBoard);
               startActivity(xx);
            }
        });
    }
    LeaderBoardDTO leaderBoard;
    public void setLeaderBoardList(PlayerDTO player, List<LeaderBoardDTO> leaderBoardList) {

        this.leaderBoardList = leaderBoardList;
        this.player = player;

        setList();

    }

    PlayerHistoryAdapter playerHistoryAdapter;
    ListView listView;
    TextView txtPlayerName, txtTourneyCount;
    static final String LOG = "PlayersHistoryFragment";
    Context ctx;
    View view;
    PlayerDTO player;
    ResponseDTO response;
    List<LeaderBoardDTO> leaderBoardList;


}
