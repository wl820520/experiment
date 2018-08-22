package com.justh5.experiment.domain.result;

import java.util.List;

public class FormalModel {
    private List<PreviewModel> previewModels;
    private List<TxtModel> txtModels;
    private List<TableModel> tableModels;

    public List<PreviewModel> getPreviewModels() {
        return previewModels;
    }

    public void setPreviewModels(List<PreviewModel> previewModels) {
        this.previewModels = previewModels;
    }

    public List<TxtModel> getTxtModels() {
        return txtModels;
    }

    public void setTxtModels(List<TxtModel> txtModels) {
        this.txtModels = txtModels;
    }

    public List<TableModel> getTableModels() {
        return tableModels;
    }

    public void setTableModels(List<TableModel> tableModels) {
        this.tableModels = tableModels;
    }
}
