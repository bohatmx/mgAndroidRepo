package com.boha.malengagolf.library.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.boha.malengagolf.library.R;
import com.boha.malengagolf.library.data.LeaderBoardDTO;
import com.boha.malengagolf.library.data.RequestDTO;
import com.boha.malengagolf.library.util.Statics;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class LeaderboardOneRoundAdapter extends ArrayAdapter<LeaderBoardDTO> {

    private final LayoutInflater mInflater;
    private final int mLayoutRes;
    private List<LeaderBoardDTO> mList;
    private int golfGroupID;
    private ImageLoader imageLoader;
    private Context ctx;

    public LeaderboardOneRoundAdapter(Context context,
                                      int textViewResourceId,
                                      List<LeaderBoardDTO> list, int golfGroupID, ImageLoader imageLoader) {
        super(context, textViewResourceId, list);
        this.mLayoutRes = textViewResourceId;
        mList = list;
        ctx = context;
        this.golfGroupID = golfGroupID;
        this.imageLoader = imageLoader;
        this.mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    View view;

    static class ViewHolderItem {
        TextView txtPlayer, txtLastHole, txtTotal, txtPar, txtPosition, txtParLabel;
        NetworkImageView image;
        ImageView winner;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolderItem;
        if (convertView == null) {
            convertView = mInflater.inflate(mLayoutRes, null);
            viewHolderItem = new ViewHolderItem();
            viewHolderItem.txtPlayer = (TextView) convertView
                    .findViewById(R.id.ONE_ROUND_playerName);
            viewHolderItem.txtPosition = (TextView) convertView
                    .findViewById(R.id.ONE_ROUND_position);
            viewHolderItem.txtTotal = (TextView) convertView
                    .findViewById(R.id.ONE_ROUND_totalScore);
            viewHolderItem.txtPar = (TextView) convertView
                    .findViewById(R.id.ONE_ROUND_par);
            viewHolderItem.txtPar = (TextView) convertView
                    .findViewById(R.id.ONE_ROUND_par);
            viewHolderItem.txtParLabel = (TextView) convertView
                    .findViewById(R.id.ONE_ROUND_parLabel);
            viewHolderItem.txtLastHole = (TextView) convertView
                    .findViewById(R.id.ONE_ROUND_lastHole);
            viewHolderItem.image = (NetworkImageView) convertView
                    .findViewById(R.id.ONE_ROUND_image);
            viewHolderItem.winner = (ImageView) convertView
                    .findViewById(R.id.ONE_ROUND_winnerImage);

            convertView.setTag(viewHolderItem);
        } else {
            viewHolderItem = (ViewHolderItem) convertView.getTag();
        }

        LeaderBoardDTO p = mList.get(position);
        if (p.getWinnerFlag() > 0) {
            viewHolderItem.winner.setVisibility(View.VISIBLE);
        } else {
            viewHolderItem.winner.setVisibility(View.GONE);
        }


        if (p.getParStatus() == LeaderBoardDTO.NO_PAR_STATUS) {
            viewHolderItem.txtPosition.setVisibility(View.GONE);
        } else {
            viewHolderItem.txtPosition.setVisibility(View.VISIBLE);
        }

        if (p.getPosition() < 10) {
            viewHolderItem.txtPosition.setText("0" + p.getPosition());
        } else {
            viewHolderItem.txtPosition.setText("" + p.getPosition());
        }

        if (p.isTied()) {
            viewHolderItem.txtPosition.setText("T" + p.getPosition());
        }
        viewHolderItem.txtPlayer.setText(p.getPlayer().getFirstName() + " "
                + p.getPlayer().getLastName());

        formatStrokes(viewHolderItem.txtTotal, p.getParStatus(), p.getTotalScore());

        //Log.e("adap", "totalPoints: " + p.getTotalPoints() + " tourType: "+ p.getTournamentType());
        if (p.getTournamentType() == 0) {
            p.setTournamentType(RequestDTO.STROKE_PLAY_INDIVIDUAL);
        }
        switch (p.getTournamentType()) {
            case RequestDTO.STROKE_PLAY_INDIVIDUAL:
                viewHolderItem.txtParLabel.setText(ctx.getResources().getString(R.string.par));
                if (p.getParStatus() == LeaderBoardDTO.NO_PAR_STATUS) {
                    viewHolderItem.txtPar.setText("N/S");
                    viewHolderItem.txtPar.setTextColor(ctx.getResources().getColor(R.color.grey2));

                } else {
                    viewHolderItem.txtPar.setVisibility(View.VISIBLE);
                    formatStrokes(viewHolderItem.txtPar, p.getParStatus());
                }
                break;
            case RequestDTO.STABLEFORD_INDIVIDUAL:
                viewHolderItem.txtParLabel.setText(ctx.getResources().getString(R.string.points));
                viewHolderItem.txtPar.setText("" + p.getTotalPoints());
                if (p.getTotalPoints() == 0) {
                    viewHolderItem.txtPar.setTextColor(ctx.getResources().getColor(R.color.grey2));

                } else {
                    viewHolderItem.txtPar.setVisibility(View.VISIBLE);
                    viewHolderItem.txtPar.setTextColor(ctx.getResources().getColor(R.color.black));
                }
                break;
        }

        viewHolderItem.txtLastHole.setText("" + p.getLastHole());
        int x = position % 2;
        if (x > 0) {
            convertView.setBackgroundColor(ctx.getResources().getColor(R.color.beige_pale));
        } else {
            convertView.setBackgroundColor(ctx.getResources().getColor(R.color.white));
        }
        //image
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(Statics.IMAGE_URL).append("golfgroup")
                    .append(golfGroupID).append("/player/");
            sb.append("t");
            sb.append(p.getPlayer().getPlayerID()).append(".jpg");
            viewHolderItem.image.setDefaultImageResId(R.drawable.boy);
            viewHolderItem.image.setImageUrl(sb.toString(), imageLoader);
        } catch (Exception e) {
            Log.w("OneRound", "network image view problem", e);
        }
        animateView(convertView);
        return (convertView);
    }

    public void animateView(final View view) {
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.grow_fade_in_center);
        a.setDuration(1000);
        if (view == null)
            return;
        view.startAnimation(a);
    }


    private void formatStrokes(TextView txt, int parStatus) {
        if (parStatus == 0) { //Even par
            txt.setTextColor(ctx.getResources().getColor(R.color.black));
            txt.setText("E");
        }
        if (parStatus > 0) { //under par
            txt.setTextColor(ctx.getResources().getColor(R.color.absa_red));
            txt.setText("-" + parStatus);
        }
        if (parStatus < 0) { //over par
            txt.setTextColor(ctx.getResources().getColor(R.color.blue));
            txt.setText("+" + (parStatus * -1));
        }


    }

    private void formatStrokes(TextView txt, int parStatus, int score) {
        if (parStatus == 0) { //Even par
            txt.setTextColor(ctx.getResources().getColor(R.color.black));
        }
        if (parStatus > 0) { //under par
            txt.setTextColor(ctx.getResources().getColor(R.color.absa_red));
        }
        if (parStatus < 0) { //over par
            txt.setTextColor(ctx.getResources().getColor(R.color.blue));
        }
        txt.setText("" + score);

    }

    static final int UNDER = 1, PAR = 2, OVER = 3;
    static final Locale x = Locale.getDefault();
    static final SimpleDateFormat y = new SimpleDateFormat("dd MMMM yyyy", x);
}
