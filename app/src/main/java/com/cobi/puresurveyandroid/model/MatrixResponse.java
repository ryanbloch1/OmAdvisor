package com.cobi.puresurveyandroid.model;

import java.util.List;

/**
 * Created by admin on 2018/04/13.
 */

public class MatrixResponse extends BaseResponse {
    List<MatrixItem> data;

    public List<MatrixItem> getItems() {
        return data;
    }

    public void setItems(List<MatrixItem> items) {
        this.data = items;
    }
}
