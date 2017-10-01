package one2play.mobot.itunesapi.Service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by fastroni on 30/09/2017.
 */

public interface MusicService {

    public static final String BASE_URL = "https://itunes.apple.com/";

    @GET("search?")
    Call<MusicList> listarMusica(@Query("term") String name, @Query("limit") int limit);

}
