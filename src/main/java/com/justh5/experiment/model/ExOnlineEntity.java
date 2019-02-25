package com.justh5.experiment.model;

import lombok.Data;

import java.io.Serializable;
@Data
public class ExOnlineEntity  implements Serializable {
    private Integer id;
    private Integer uid;
    private Integer main_type;
    private Integer ex_status;
    private Integer version;
    private Integer bonus_num;

}
