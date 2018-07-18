/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmeans;

import java.util.ArrayList;

/**
 *
 * @author Bayu Aji Firmansyah
 */
public class Data {

    private int no;
    private double latitude;
    private double longitude;
    private double brightness;
    private int confidance;
    private int klaster;

    public void setNo(int no) {
        this.no = no;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setBrightness(double brigthnes) {
        brightness = brigthnes;
    }

    public void setConfidance(int confidance) {
        this.confidance = confidance;
    }

    public void setKlaster(int klaster) {
        this.klaster = klaster;
    }

    public int getNo() {
        return no;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getBrightness() {
        return brightness;
    }

    public double getConfidance() {
        return confidance;
    }

    public int getKlaster() {
        return klaster;
    }

    public void setRandomKlaster(int min, int max) {
        this.klaster = min + (int) (Math.random() * max);
    }
}
