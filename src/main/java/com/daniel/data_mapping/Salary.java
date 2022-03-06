package com.daniel.data_mapping;

public class Salary {

    private final float starting_salary;
    private final float salary_10th_percentage;
    private final float salary_25th_percentage;
    private final float salary_75th_percentage;
    private final float salary_90th_percentage;

    Salary(String[] words){
        this.starting_salary = cleanUpString(words[1]);
        this.salary_10th_percentage = cleanUpString(words[4]);
        this.salary_25th_percentage = cleanUpString(words[5]);
        this.salary_75th_percentage = cleanUpString(words[6]);
        this.salary_90th_percentage = cleanUpString(words[7]);
    }

    public float getStarting_salary() {
        return starting_salary;
    }

    public float getSalary_10th_percentage() {
        return salary_10th_percentage;
    }

    public float getSalary_25th_percentage() {
        return salary_25th_percentage;
    }

    public float getSalary_75th_percentage() {
        return salary_75th_percentage;
    }

    public float getSalary_90th_percentage() {
        return salary_90th_percentage;
    }

    public float cleanUpString(String word){
        return Float.parseFloat(word
                                .substring(2, word.length() - 2)
                                .replaceAll(",", ""));
    }
    @Override
    public String toString() {
        return "Salary{" +
                "starting_salary='" + starting_salary + '\'' +
                ", salary_10th_percentage='" + salary_10th_percentage + '\'' +
                ", salary_25th_percentage='" + salary_25th_percentage + '\'' +
                ", salary_75th_percentage='" + salary_75th_percentage + '\'' +
                ", salary_90th_percentage='" + salary_90th_percentage + '\'' +
                '}';
    }
}
