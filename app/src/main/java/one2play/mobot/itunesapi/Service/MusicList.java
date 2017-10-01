package one2play.mobot.itunesapi.Service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import one2play.mobot.itunesapi.Model.MusicModel;

/**
 * Created by fastroni on 30/09/2017.
 */

public class MusicList {
    @SerializedName("results")
    @Expose
    public ArrayList<MusicModel> musica = null;

    public ArrayList<MusicModel> getTrackName(){
        return musica;
    }

    public void setTrackName(ArrayList<MusicModel> musica){
        this.musica = musica;
    }

}
