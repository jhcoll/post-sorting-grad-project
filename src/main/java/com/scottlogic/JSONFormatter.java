package com.scottlogic;
import com.scottlogic.filters.AuthorPostFilter;
import com.scottlogic.filters.DatePostFilter;
import com.scottlogic.filters.KeywordPostFilter;
import com.scottlogic.filters.LikePostFilter;
import com.scottlogic.sorts.AuthorPostSorter;
import com.scottlogic.sorts.DatePostSorter;
import com.scottlogic.sorts.KeywordPostSorter;
import com.scottlogic.sorts.LikesPostSorter;
import org.json.JSONObject;
import org.json.JSONArray;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JSONFormatter {
    public JSONObject jsonObject;


    public JSONFormatter(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }


    public void getDataFromObject(){
        JSONObject filters = new JSONObject(jsonObject.getJSONObject("filters"));
        List<PostFilter> postFilters = new ArrayList<>(getFilter(filters));
        JSONObject sorters = new JSONObject(jsonObject.getJSONObject("sorters"));
        getSorter(sorters);
    }

    public List<PostFilter> getFilter(JSONObject filters){
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
            }
        );
        return filterList;
    }

    public List<PostSorter> getSorter(JSONObject sorters){
        List<PostSorter> sorterList = new ArrayList<>();
        sorters.keySet().forEach(key -> {
                    switch (key) {
                        case "author":
                            sorterList.add(new AuthorPostSorter());
                        case "likes":
                            sorterList.add(new LikesPostSorter());
                        case "Date":
                            sorterList.add(new DatePostSorter());
                        case "keyword":
                            String keyword = sorters.getString(key);
                            sorterList.add(new KeywordPostSorter(new KeywordPostFilter(keyword)));

                        default:
                    }
                }
        );
        return sorterList;
    }
}
