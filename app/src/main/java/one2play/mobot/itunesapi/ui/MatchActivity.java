package one2play.mobot.itunesapi.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.ArrayList;

import one2play.mobot.itunesapi.Service.MusicList;
import one2play.mobot.itunesapi.Model.MusicModel;
import one2play.mobot.itunesapi.Service.MusicService;
import one2play.mobot.itunesapi.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class MatchActivity extends AppCompatActivity {

    private ArrayList<MusicModel> dataMusic;
    private SwipePlaceHolderView mSwipeView;
    private Context mContext;
    private String music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        music = b.get("music").toString();

        mSwipeView = (SwipePlaceHolderView) findViewById(R.id.swipeView);
        mContext = getApplicationContext();

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.msg_swipe_in)
                        .setSwipeOutMsgLayoutId(R.layout.ms_swipe_out));


        findViewById(R.id.btnReject).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(false);
            }
        });

        findViewById(R.id.btnAccept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(true);
            }
        });

        requestMusic(music);
    }

    public void requestMusic(String music) {
        dataMusic = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MusicService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Data limit of search
        int limit = 20;

        MusicService service = retrofit.create(MusicService.class);
        final Call<MusicList> requestMusic = service.listarMusica(music, limit);

        requestMusic.enqueue(new Callback<MusicList>() {
            @Override
            public void onResponse(Call<MusicList> call, Response<MusicList> response) {
                if (response.isSuccessful()) {
                    MusicList musicaList = response.body();

                    for (MusicModel musicModel : musicaList.musica) {
                       // dataMusic.add(musicModel);
                        mSwipeView.addView(new TinderCard(mContext, musicModel , mSwipeView));
                    }
                    if(musicaList.musica.isEmpty()){
                        Intent i = new Intent(MatchActivity.this, MainActivity.class);
                        startActivity(i);
                        Toast.makeText(MatchActivity.this, R.string.msg_no_results, Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MusicList> call, Throwable t) {
                Log.e(TAG, "Erro: " + t.getMessage());
            }

        });

    }
}
