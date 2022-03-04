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

    public DataLoading() {
        setup_dataSetOne();
    }

    void setup_dataSetOne(){
        try {
            Files.readAllLines(Path.of(RES_DIR + FILE_ONE_NAME)).forEach(line -> {
                String[] words = split_csv_line_to_arr(line);
                Storage.MAP_1.put(words[0], new Salary(words));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] split_csv_line_to_arr(String line){
        return line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
    }

}
