/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmeans;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Bayu Aji Firmansyah
 */
public class ModelKMeans {

    public static ArrayList groupByKlaster(ArrayList<Data> data, int k) {
        ArrayList<ArrayList<Data>> grouped = new ArrayList<ArrayList<Data>>(k);
        for (int i = 0; i < k; i++) {
            grouped.add(new ArrayList<Data>());
        }
        for (Data x : data) {
            grouped.get(x.getKlaster() - 1).add(x);
        }
        return grouped;
    }

    /*
    *       [klaster keberapa - 1][sumfitur] =     [sumfitur1K1] [sumfitur2K1] [sumfitur3K1] [k1] [..] [..]
    *                                              [sumfitur1K2] [sumfitur2K2] [sumfitur3K2] [k2] [..] [..]
    *                                              [sumfitur1K3] [sumfitur2K3] [sumfitur3K3] [k3] [..] [..]
    *                                              [..]          [..]          [..]          [..] [..] [..]
     */
    public static double[][] sumOfFeaturesForCentroid(ArrayList data) {
        //plus 1 untuk nyimpen jumlah data yang ada diklaster
        double sumOfFeatures[][] = new double[data.size()][4 + 1];
        for (int ii = 0; ii < data.size(); ii++) {
            ArrayList<Data> x = (ArrayList<Data>) data.get(ii);
            for (int i = 0; i < x.size(); i++) {
                sumOfFeatures[ii][0] += x.get(i).getLatitude();
                sumOfFeatures[ii][1] += x.get(i).getLongitude();
                sumOfFeatures[ii][2] += x.get(i).getBrightness();
                sumOfFeatures[ii][3] += x.get(i).getConfidance();
                //hitung jumlah data yang ada di klaster
                sumOfFeatures[ii][4] += 1;
            }
        }
        return sumOfFeatures;
    }

    /*
    *   [klaster keberapa][fitur keberapa]
     */
    public static double[][] findCentroid(double[][] sumOfFeatures, int k) {
        // miinus 1 karena di centroid ga perlu ada jumlah data yang ada di klaster
        double[][] centroid = new double[k][sumOfFeatures[0].length - 1];
        for (int i = 0; i < sumOfFeatures.length; i++) {
            // kenapa -1 biar index jumlah data yang ada diklaster ga keakses
            for (int ii = 0; ii < sumOfFeatures[0].length - 1; ii++) {
                // kenapa -1 karena index array mulainya dari 0, kalo ga di min 1 nanti index array out of bounds
                centroid[i][ii] = sumOfFeatures[i][ii] / sumOfFeatures[i][sumOfFeatures[i].length - 1];
            }
        }
        return centroid;
    }

    /* urutan data sesuai dengan yang ada di ArrayList<Data> data
    * [data][klaster] = [data 1 klaster 1] [data 1 klaster 2] [data 1 klaster 3] [...] 
    *                   [data 2 klaster 1] [data 2 klaster 2] [data 2 klaster 3] [...]
    *                   [data 3 klaster 1] [data 3 klaster 2] [data 3 klaster 3] [...]
    *                   [..]               [..]               [..]               [...] 
     */
    public static double[][] calculateDistance(ArrayList<Data> data, double[][] centroid) {
        double[][] distance = new double[data.size()][centroid.length];
        for (int i = 0; i < data.size(); i++) {
            Data x = data.get(i);
            for (int ii = 0; ii < centroid.length; ii++) {
                double fitur[] = centroid[ii];
                // menggunakan manhattan distance                
                double dist = abs(x.getLatitude() - fitur[0]);
                dist += abs(x.getLongitude() - fitur[1]);
                dist += abs(x.getBrightness() - fitur[2]);
                dist += abs(x.getConfidance() - fitur[3]);
                // masukin ke array disrance
                distance[i][ii] = dist;
            }
        }
        return distance;
    }

    public static int[] renewCluster(ArrayList<Data> data, double[][] distance) {
        int klaster[] = new int[data.size()];
        for (int i = 0; i < distance.length; i++) {
            double min = distance[i][0];
            int idx = 0;
            for (int ii = 0; ii < distance[i].length; ii++) {
                if (distance[i][ii] < min) {
                    min = distance[i][ii];
                    idx = ii;
                }
            }
            data.get(i).setKlaster(idx + 1);
            //kenapa plus 1 karena index dimulai dari 0 sedangkan kita mau klaster dimulai dari 1
            klaster[i] = idx + 1;

        }
        return klaster;
    }

    public static double findFunction(double[][] distance, int[] renewCluster) {
        double sum[] = new double[distance[0].length];
        for (int i = 0; i < distance.length; i++) {
            for (int ii = 0; ii < renewCluster.length; ii++) {
                // kenapa renew cluster -1 karena saat di renewcuster,klasternya itu dimulai dari 1 bukand ari 0 jadi harus dikurang 1                
                sum[renewCluster[i] - 1] += distance[i][renewCluster[i] - 1];
            }
        }
        return Arrays.stream(sum).sum();
    }

    public static void KMeans(ArrayList<Data> data, int k, double threshold, double function) {
        ArrayList groupedData = ModelKMeans.groupByKlaster(data, k);
        double sumOfFeatures[][] = ModelKMeans.sumOfFeaturesForCentroid(groupedData);
        double centroid[][] = ModelKMeans.findCentroid(sumOfFeatures, k);
        double distance[][] = ModelKMeans.calculateDistance(data, centroid);
        int[] renewCluster = ModelKMeans.renewCluster(data, distance);
        double newFunction = ModelKMeans.findFunction(distance, renewCluster);
        double delta = abs(newFunction - function);
        if (delta > threshold) {
            KMeans(data, k, threshold, newFunction);
        }
    }
}
