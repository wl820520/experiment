package com.justh5.experiment.domain;

import com.justh5.experiment.model.ExMainEntity;
import com.justh5.experiment.model.ExOnlineEntity;

import java.util.List;

public class OnlineResp {
    private List<ExMainEntity> exMainEntities;
    private ExOnlineEntity exOnlineEntity;

    public List<ExMainEntity> getExMainEntities() {
        return exMainEntities;
    }

    public void setExMainEntities(List<ExMainEntity> exMainEntities) {
        this.exMainEntities = exMainEntities;
    }

    public ExOnlineEntity getExOnlineEntity() {
        return exOnlineEntity;
    }

    public void setExOnlineEntity(ExOnlineEntity exOnlineEntity) {
        this.exOnlineEntity = exOnlineEntity;
    }
}
