package com.daniel.data_mapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class DataLoading {

    final String ROOT_DIR = System.getProperty("user.dir") + "/";
    final String RES_DIR = ROOT_DIR + "res/";
    final String FILE_ONE_NAME = "major_salary.csv";
    final String FILE_TWO_NAME = "unemployment.csv";
    int count = 0;

    public DataLoading() {
        try {
            setup_dataSetOne();
            setup_dataSetTwo();
        } catch (IOException e) { e.printStackTrace(); }

        Storage.MAP_1.forEach((k, v) -> {
            Storage.MAP_2.forEach((k2, v2) -> {
                if (k.equals(k2))
                    System.out.println(++count + ") k -> " + k + " | v -> " + k2);
            });
        });

    }

    void setup_dataSetOne() throws IOException {
        Files.readAllLines(Path.of(RES_DIR + FILE_ONE_NAME)).forEach(line -> {
            String[] words = split_csv_line_to_arr(line);
            Storage.MAP_1.put(words[0], new Salary(words));
        });
    }

    void setup_dataSetTwo() throws IOException {
        Files.readAllLines(Path.of(RES_DIR + FILE_TWO_NAME)).forEach(line -> {
            String[] words = split_csv_line_to_arr(line);
            Storage.MAP_2.put(words[0], new Unemployment(words));
        });
    }

    public String[] split_csv_line_to_arr(String line){
        return line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
    }

}
