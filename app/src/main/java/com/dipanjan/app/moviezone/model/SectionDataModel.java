package com.dipanjan.app.moviezone.model;

import java.util.ArrayList;

/**
 * Created by pratap.kesaboyina on 30-11-2015.
 */
public class SectionDataModel {



    private String headerTitle;
    private String movieIdentifier;
    private ArrayList<Movie> allItemsInSection;


    public SectionDataModel() {

    }
    public SectionDataModel(String headerTitle, ArrayList<Movie> allItemsInSection, String movieIdentifier) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;
        this.movieIdentifier=movieIdentifier;
    }


    public String getMovieIdentifier() {
        return movieIdentifier;
    }

    public void setMovieIdentifier(String movieIdentifier) {
        this.movieIdentifier = movieIdentifier;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<Movie> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<Movie> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }

    @Override
    public String toString() {
        return "SectionDataModel{" +
                "headerTitle='" + headerTitle + '\'' +
                ", movieIdentifier='" + movieIdentifier + '\'' +
                ", allItemsInSection=" + allItemsInSection +
                '}';
    }
}
