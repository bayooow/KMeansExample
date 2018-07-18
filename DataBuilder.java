/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmeans;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author Bayu Aji Firmansyah
 */
public class DataBuilder {

    public static ArrayList<Data> build(String path) {
        ArrayList<Data> data = new ArrayList<Data>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            StringBuilder sb = new StringBuilder();
            String line = "1";

            while (line != null) {
                line = br.readLine();
                sb.append(line);
                sb.append(System.lineSeparator());
                if (line != null) {
                    Data temp = new Data();
                    String[] fitur = line.split("\\s+");
                    temp.setNo(Integer.parseInt(fitur[0]));
                    temp.setLatitude(Double.parseDouble(fitur[1]));
                    temp.setLongitude(Double.parseDouble(fitur[2]));
                    temp.setBrightness(Double.parseDouble(fitur[3]));
                    temp.setConfidance(Integer.parseInt(fitur[4]));
                    data.add(temp);
                }
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
        return data;
    }
}
