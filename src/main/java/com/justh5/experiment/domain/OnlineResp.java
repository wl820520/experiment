package com.justh5.experiment.domain;

import com.justh5.experiment.model.ExExTypeEntity;
import com.justh5.experiment.model.ExMainEntity;
import com.justh5.experiment.model.ExOnlineEntity;
import lombok.Data;

import java.util.List;

@Data
public class OnlineResp {
    private List<ExExTypeEntity> exExTypeEntities;
    private ExOnlineEntity exOnlineEntity;

}
