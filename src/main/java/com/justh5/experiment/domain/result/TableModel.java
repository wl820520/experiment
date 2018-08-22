package com.justh5.experiment.domain.result;

import java.util.List;

public class TableModel {
    private Integer id;
    private String title;
    private TableSubModel[][] tableSubModels;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TableSubModel[][] getTableSubModels() {
        return tableSubModels;
    }

    public void setTableSubModels(TableSubModel[][] tableSubModels) {
        this.tableSubModels = tableSubModels;
    }
}
