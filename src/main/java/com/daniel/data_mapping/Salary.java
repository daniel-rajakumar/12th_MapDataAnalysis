package com.daniel.data_mapping;

public class Salary {

    private final String starting_salary;
    private final String salary_10th_percentage;
    private final String salary_25th_percentage;
    private final String salary_75th_percentage;
    private final String salary_90th_percentage;

    Salary(String[] words){
        this.starting_salary = words[1];
        this.salary_10th_percentage = words[4];
        this.salary_25th_percentage = words[5];
        this.salary_75th_percentage = words[6];
        this.salary_90th_percentage = words[7];
    }

    public String getStarting_salary() {
        return starting_salary;
    }

    public String getSalary_10th_percentage() {
        return salary_10th_percentage;
    }

    public String getSalary_25th_percentage() {
        return salary_25th_percentage;
    }

    public String getSalary_75th_percentage() {
        return salary_75th_percentage;
    }

    public String getSalary_90th_percentage() {
        return salary_90th_percentage;
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
