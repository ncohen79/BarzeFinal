package barzeCombo;

import android.graphics.Bitmap;
import android.media.Image;

public class DataModel {
    private String name;
    private int wait;
    private int cover;
    private Bitmap pic;
    private float lat;
    private float lng;

    public DataModel(String name, int wait, int cover, float lat, float lng, Bitmap pic) {
        this.name = name;
        this.wait = wait;
        this.cover = cover;
        this.lat = lat;
        this.lng = lng;
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWait() {
        return wait;
    }

    public int getCover() {
        return cover;
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }

    public Bitmap getPic() {
        return pic;
    }
}
