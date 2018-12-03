package barzeCombo;

import android.graphics.Bitmap;
import android.media.Image;

public class DataModel {
    private String name;
    private Double wait;
    private String cover;
    private Bitmap pic;
    private Double lat;
    private Double lng;

    public DataModel(String name, Double wait, String cover, Double lat, Double lng, Bitmap pic) {
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

    public Double getWait() {
        return wait;
    }
    public String getCover() {
        return cover;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }
    public Bitmap getPic() {
        return pic;
    }
}
