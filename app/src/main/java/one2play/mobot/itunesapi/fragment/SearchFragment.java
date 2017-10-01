package one2play.mobot.itunesapi.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import one2play.mobot.itunesapi.R;
import one2play.mobot.itunesapi.ui.MatchActivity;

/**
 * Created by fastroni on 01/10/2017.
 */

public class SearchFragment extends Fragment {

    private EditText edtSearchMusic;
    private Button btnSearchMusic;

    public SearchFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_searchmusics, container, false);

        edtSearchMusic = (EditText) v.findViewById(R.id.edtSearchMusic);
        btnSearchMusic = (Button) v.findViewById(R.id.btnSearchMusic);

        btnSearchMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtSearchMusic.getText().toString().isEmpty()) {
                    isNetworkAvailable(getContext());
                    if (isNetworkAvailable(getContext())) {
                        Intent i = new Intent(getActivity(), MatchActivity.class);
                        String music = edtSearchMusic.getText().toString();
                        music = music.replaceAll(" ", "+");
                        i.putExtra("music", music);
                        startActivity(i);
                    } else {
                        Toast.makeText(getActivity(), R.string.msg_connection, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.msg_search_music_empty, Toast.LENGTH_LONG).show();
                }
            }
        });

        return v;
    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
