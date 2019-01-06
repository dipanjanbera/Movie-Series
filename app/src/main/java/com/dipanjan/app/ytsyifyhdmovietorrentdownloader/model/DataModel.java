package com.dipanjan.app.ytsyifyhdmovietorrentdownloader.model;

/**
 * Created by LENOVO on 31-08-2018.
 */
public class DataModel {

    private SectionDataModel sectionDataModel;
    private SingleItemModel singleItemModel;
    private String urlLink;
    private String category;
    private String identifier;
    private String queryParameter;
    private String header;
    public DataModel(String urlLink, String category, String identifier,String queryParameter,String header) {
        super();
        this.urlLink = urlLink;
        this.category = category;
        this.identifier = identifier;
        this.queryParameter = queryParameter;
        this.header=header;
    }

    public DataModel() {
    }

    public String getQueryParameter() {
        return queryParameter;
    }


    public void setQueryParameter(String queryParameter) {
        this.queryParameter = queryParameter;
    }


    public String getIdentifier() {
        return identifier;
    }
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    public SectionDataModel getSectionDataModel() {
        return sectionDataModel;
    }
    public void setSectionDataModel(SectionDataModel sectionDataModel) {
        this.sectionDataModel = sectionDataModel;
    }
    public SingleItemModel getSingleItemModel() {
        return singleItemModel;
    }
    public void setSingleItemModel(SingleItemModel singleItemModel) {
        this.singleItemModel = singleItemModel;
    }
    public String getUrlLink() {
        return urlLink;
    }
    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "sectionDataModel=" + sectionDataModel +
                ", singleItemModel=" + singleItemModel +
                ", urlLink='" + urlLink + '\'' +
                ", category='" + category + '\'' +
                ", identifier='" + identifier + '\'' +
                ", queryParameter='" + queryParameter + '\'' +
                ", header='" + header + '\'' +
                '}';
    }
}