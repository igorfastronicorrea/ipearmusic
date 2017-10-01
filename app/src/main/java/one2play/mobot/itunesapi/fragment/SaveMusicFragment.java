package one2play.mobot.itunesapi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import one2play.mobot.itunesapi.Model.MusicModel;
import one2play.mobot.itunesapi.Adapter.MusicLikedAdapter;
import one2play.mobot.itunesapi.R;
import one2play.mobot.itunesapi.database.SQLiteDatabaseHandler;

/**
 * Created by fastroni on 01/10/2017.
 */

public class SaveMusicFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MusicLikedAdapter adapter;
    private SQLiteDatabaseHandler db;
    private List<MusicModel> musics;
    private TextView txtNoResults;
    private SwipeRefreshLayout mySwipeRefreshLayout;

    public SaveMusicFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_allmusics, container, false);
        musics = new ArrayList<>();
        db = new SQLiteDatabaseHandler(getContext());
        musics = db.allMusics();

        mySwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swiperefresh);
        recyclerView = (RecyclerView) v.findViewById(R.id.rv_allmusics);

        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new MusicLikedAdapter(getContext(), musics);
        recyclerView.setAdapter(adapter);

        if(musics.size() == 0){
            txtNoResults = (TextView) v.findViewById(R.id.txtNoResults);
            txtNoResults.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
        }

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        updateList();
                    }
                }
        );

        return v;
    }

    private void updateList(){
        musics = db.allMusics();
        adapter = new MusicLikedAdapter(getContext(), musics);
        recyclerView.setAdapter(adapter);
        mySwipeRefreshLayout.setRefreshing(false);
    }
    @Override
    public void onResume() {
        super.onResume();
        updateList();
    }
}
