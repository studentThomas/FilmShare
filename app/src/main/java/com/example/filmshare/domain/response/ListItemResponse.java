package com.example.filmshare.domain.response;

import com.example.filmshare.domain.List;
import com.example.filmshare.domain.ListItem;
import com.google.gson.annotations.SerializedName;

public class ListItemResponse {
    @SerializedName("results")
    private java.util.List<ListItem> listItems;

    public java.util.List<ListItem> getListItems() {
        return listItems;
    }
}
