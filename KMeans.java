/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmeans;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Bayu Aji Firmansyah
 */
public class KMeans {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //read object data
        String path = "C:\\Users\\Bayu Aji Firmansyah\\Documents\\NetBeansProjects\\KMeans\\src\\kmeans\\DataTrain.txt";
        ArrayList<Data> data = new ArrayList<Data>();
        data = DataBuilder.build(path);
        //input variable
        Scanner s = new Scanner(System.in);
        System.out.print("K : ");
        int k = s.nextInt();
        System.out.print("Threshold : ");
        double threshold = s.nextDouble();
        double function = 0;
        //set random klaster
        for (Data temp : data) {
            temp.setRandomKlaster(1, k);
        }
        ModelKMeans.KMeans(data, k, threshold, function);
        // display data klaster
        System.out.printf("%4S  %10S  %10S  %10S  %10S  %10S\n", "No", "Latitude", "Longitude", "Brightness", "Confidance", "Klaster");
        for (Data x : data) {
            System.out.printf("%4d  %10f  %10f  %10f  %10f  %10d\n", x.getNo(), x.getLatitude(), x.getLongitude(), x.getBrightness(), x.getConfidance(), x.getKlaster());
        }
    }

}
