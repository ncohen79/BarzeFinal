package barzeCombo;

import java.util.List;

public class Bar {

    private String ID;
    private String name;
    private List<Float> location;
    private int waitTime;
    private int lowCover;
    private int highCover;

    public Bar() {

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Float> getLocation() {
        return location;
    }

    public void setLocation(List<Float> location) {
        this.location = location;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public int getLowCover() {
        return lowCover;
    }

    public void setLowCover(int lowCover) {
        this.lowCover = lowCover;
    }

    public int getHighCover() {
        return highCover;
    }

    public void setHighCover(int highCover) {
        this.highCover = highCover;
    }
}