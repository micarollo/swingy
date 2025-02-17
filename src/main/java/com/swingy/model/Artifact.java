package main.java.com.swingy.model;

public class Artifact {
    private String p_quality;
    private int p_points;

    public Artifact(int villainLevel) {
        this.p_quality = calculateQuality(villainLevel);
        this.p_points = calculatePoints(villainLevel);
    }

    public String calculateQuality(int villainLevel) {
        
    }
}