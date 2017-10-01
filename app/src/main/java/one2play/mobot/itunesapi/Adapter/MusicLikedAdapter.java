package one2play.mobot.itunesapi.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import one2play.mobot.itunesapi.Model.MusicModel;
import one2play.mobot.itunesapi.R;

/**
 * Created by fastroni on 01/10/2017.
 */

public class MusicLikedAdapter extends RecyclerView.Adapter<MusicLikedAdapter.ViewHolder> {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private List<MusicModel> mMusic;
    private Context mContext;
    private int visibleThreshold = 2;
    private int lastVisibleItem, totalItemCount;

    public MusicLikedAdapter(Context context, List<MusicModel> mMusic) {
        this.mContext = context;
        this.mMusic = mMusic;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_allmusics, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MusicModel music = mMusic.get(position);
        TextView txtMusicName = holder.txtMusicName;
        TextView txtArtistName = holder.txtArtistName;
        ImageView imgAlbumMusic = holder.imgAlbumMusic;

        txtMusicName.setText(music.getTrackName().toString());
        txtArtistName.setText(music.getArtistName().toString());
        Glide.with(this.mContext)
                .load(music.getArtworkUrl100())
                .into(imgAlbumMusic);
    }

    @Override
    public int getItemCount() {
        return mMusic.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtMusicName;
        public TextView txtArtistName;
        public ImageView imgAlbumMusic;

        public ViewHolder(View itemView) {
            super(itemView);

            txtMusicName = (TextView) itemView.findViewById(R.id.txtMusicName);
            txtArtistName = (TextView) itemView.findViewById(R.id.txtArtistName);
            imgAlbumMusic = (ImageView) itemView.findViewById(R.id.imgAlbumMusic);
        }
    }
}
