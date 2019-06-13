package com.example.graduationproject.Bean;

public class SignInBean {
    private String account;
    private String experiment;
    private String score;

    public SignInBean(String account, String experiment, String score) {
        this.account = account;
        this.experiment = experiment;
        this.score = score;
    }
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getExperiment(){ return  experiment;}

    public void setExperiment(String experiment){ this.experiment = experiment;}

    public String getScore(){ return score;}

    public void setScore(String score){this.score = score;}

}
