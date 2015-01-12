package com.example.android.recyclerplayground.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.android.recyclerplayground.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.VerticalItemHolder> {

    private final List<GameItem> mItems;

    private AdapterView.OnItemClickListener mOnItemClickListener;

    public SimpleAdapter() {
        mItems = new ArrayList<GameItem>();
    }

    /*
     * A common adapter modification or reset mechanism. As with ListAdapter,
     * calling notifyDataSetChanged() will trigger the RecyclerView to update
     * the view. However, this method will not trigger any of the RecyclerView
     * animation features.
     */
    public void setItemCount(int count) {
        mItems.clear();
        mItems.addAll(generateDummyData(count));

        notifyDataSetChanged();
    }

    /*
     * Inserting a new item at the head of the list. This uses a specialized
     * RecyclerView method, notifyItemInserted(), to trigger any enabled item
     * animations in addition to updating the view.
     */
    public void addItem() {
        mItems.add(1, generateDummyItem());
        notifyItemInserted(1);
    }

    /*
     * Inserting a new item at the head of the list. This uses a specialized
     * RecyclerView method, notifyItemRemoved(), to trigger any enabled item
     * animations in addition to updating the view.
     */
    public void removeItem() {
        if (mItems.isEmpty()) {
            return;
        }

        mItems.remove(0);
        notifyItemRemoved(0);
    }

    @Override
    public VerticalItemHolder onCreateViewHolder(ViewGroup container, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View root = inflater.inflate(R.layout.view_match_item, container, false);

        return new VerticalItemHolder(root, this);
    }

    @Override
    public void onBindViewHolder(VerticalItemHolder itemHolder, int position) {
        GameItem item = mItems.get(position);

        itemHolder.setAwayScore(String.valueOf(item.awayScore));
        itemHolder.setHomeScore(String.valueOf(item.homeScore));

        itemHolder.setAwayName(item.awayTeam);
        itemHolder.setHomeName(item.homeTeam);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private void onItemHolderClick(VerticalItemHolder itemHolder) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(null, itemHolder.itemView,
                    itemHolder.getPosition(), itemHolder.getItemId());
        }
    }

    public static class GameItem {
        public final String homeTeam;
        public final String awayTeam;
        public final int homeScore;
        public final int awayScore;

        public GameItem(String homeTeam, String awayTeam, int homeScore, int awayScore) {
            this.homeTeam = homeTeam;
            this.awayTeam = awayTeam;
            this.homeScore = homeScore;
            this.awayScore = awayScore;
        }
    }

    private static GameItem generateDummyItem() {
        Random random = new Random();
        return new GameItem("Upset Home", "Upset Away",
                random.nextInt(100),
                random.nextInt(100));
    }

    private static List<SimpleAdapter.GameItem> generateDummyData(int count) {
        ArrayList<SimpleAdapter.GameItem> items = new ArrayList<SimpleAdapter.GameItem>();

        for (int i = 0; i < count; i++) {
            items.add(new SimpleAdapter.GameItem("Losers", "Winners", i, i + 5));
        }

        return items;
    }

    public static class VerticalItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mHomeScore;
        private final TextView mAwayScore;
        private final TextView mHomeName;
        private final TextView mAwayName;

        private final SimpleAdapter mAdapter;

        public VerticalItemHolder(View itemView, SimpleAdapter adapter) {
            super(itemView);
            itemView.setOnClickListener(this);

            mAdapter = adapter;

            mHomeScore = (TextView) itemView.findViewById(R.id.text_score_home);
            mAwayScore = (TextView) itemView.findViewById(R.id.text_score_away);
            mHomeName = (TextView) itemView.findViewById(R.id.text_team_home);
            mAwayName = (TextView) itemView.findViewById(R.id.text_team_away);
        }

        @Override
        public void onClick(View v) {
            mAdapter.onItemHolderClick(this);
        }

        public void setHomeScore(CharSequence homeScore) {
            mHomeScore.setText(homeScore);
        }

        public void setAwayScore(CharSequence awayScore) {
            mAwayScore.setText(awayScore);
        }

        public void setHomeName(CharSequence homeName) {
            mHomeName.setText(homeName);
        }

        public void setAwayName(CharSequence awayName) {
            mAwayName.setText(awayName);
        }
    }

}
