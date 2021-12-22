package com.scottlogic;

import com.scottlogic.filters.AuthorPostFilter;
import com.scottlogic.filters.DatePostFilter;
import com.scottlogic.filters.KeywordPostFilter;
import com.scottlogic.filters.LikePostFilter;
import com.scottlogic.sorts.*;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class JSONFormatter {
    public JSONObject jsonObject;


    public JSONFormatter(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public void getDataFromObject() {
        //TODO this obviously won't end up like this
        JSONObject filters = new JSONObject(jsonObject.getJSONObject("filters"));
        List<PostFilter> postFilters = new ArrayList<>(getFilter(filters));
        JSONObject sorters = new JSONObject(jsonObject.getJSONObject("sorters"));
        List<PostSorter> postSorters = new ArrayList<>(getSorter(sorters));
    }

    public List<PostFilter> getFilter(JSONObject filters) {
        List<PostFilter> filterList = new ArrayList<>();
        filters.keySet().forEach(key -> {
            switch (key) {
                case "author":
                    String author = filters.getString(key);
                    filterList.add(new AuthorPostFilter(author));
                case "likes":
                    filterList.add(new LikePostFilter());
                case "Date":
                    OffsetDateTime startDate = OffsetDateTime.parse(filters.getJSONObject(key).getString("startDate"));
                    OffsetDateTime endDate = OffsetDateTime.parse(filters.getJSONObject(key).getString("endDate"));
                    filterList.add(new DatePostFilter(startDate, endDate));
                case "keyword":
                    String keyword = filters.getString(key);
                    filterList.add(new KeywordPostFilter(keyword));
                default:
            }
        });
        return filterList;
    }

    public List<PostSorter> getSorter(JSONObject sorters) {
        //TODO this obviously needs changing to allow sort order to be stored, unless everything has the same sortorder, then another method??
        List<PostSorter> sorterList = new ArrayList<>();

        sorters.keySet().forEach(key -> {

            switch (key) {
                case "author":
                    String sortOrder = sorters.getString(key);
                    sorterList.add(new AuthorPostSorter());
                case "likes":
                    sortOrder = sorters.getString(key);
                    sorterList.add(new LikesPostSorter());
                case "Date":
                    sortOrder = sorters.getString(key);
                    sorterList.add(new DatePostSorter());
                case "keyword":
                    String keyword = sorters.getJSONObject(key).getString("keyword");
                    sortOrder = sorters.getJSONObject(key).getString("sortOrder");
                    sorterList.add(new KeywordPostSorter(new KeywordPostFilter(keyword)));
                case "topic":
                    sortOrder = sorters.getString(key);
                    sorterList.add(new TopicPostSorter());
                default:
            }
        });
        return sorterList;
    }
}
