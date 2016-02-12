package com.truthuniversal.tujasiri.truthuniversalapp;

/**
 * Created by tujasiri on 2/1/16.
 */
public class StateItem {
    String state_name = "";
    String state_abbreviation = "";

    public StateItem(){
        String state_name = "";
        String state_abbreviation = "";

        this.state_name = state_name;
        this.state_abbreviation = state_abbreviation;

    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getState_abbreviation() {
        return state_abbreviation;
    }

    public void setState_abbreviation(String state_abbreviation) {
        this.state_abbreviation = state_abbreviation;
    }
}
