package one2play.mobot.itunesapi.ui;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

import one2play.mobot.itunesapi.Model.MusicModel;
import one2play.mobot.itunesapi.R;
import one2play.mobot.itunesapi.database.SQLiteDatabaseHandler;

/**
 * Created by fastroni on 30/09/2017.
 */

@Layout(R.layout.card_music)
public class TinderCard {

    @View(R.id.musicImage)
    private ImageView musicImage;

    @View(R.id.musicName)
    private TextView musicName;

    @View(R.id.artistName)
    private TextView artistName;

    private MusicModel mProfile;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    private SQLiteDatabaseHandler db;

    public TinderCard(Context context, MusicModel profile, SwipePlaceHolderView swipeView) {
        mContext = context;
        mProfile = profile;
        mSwipeView = swipeView;

        db = new SQLiteDatabaseHandler(context);
    }

    @Resolve
    private void onResolved(){

        Glide.with(this.mContext)
                .load(mProfile.getArtworkUrl100())
                .into(musicImage);

        musicName.setText(mProfile.getTrackName());

        artistName.setText(mProfile.getArtistName());
    }

    @SwipeOut
    private void onSwipedOut(){
        Log.d("EVENT", "DISLINKE");
    }

    @SwipeCancelState
    private void onSwipeCancelState(){
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn(){
        Log.d("EVENT", "onSwipedIn" + musicName.getText().toString());
        db.addMusic(mProfile);
    }

    @SwipeInState
    private void onSwipeInState(){
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState(){
        Log.d("EVENT", "onSwipeOutState");
    }
}