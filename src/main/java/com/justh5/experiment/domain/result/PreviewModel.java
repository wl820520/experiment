package com.justh5.experiment.domain.result;

import java.util.List;

public class PreviewModel {
    private Integer id;
    private String title;
    private Integer ispic;
    private List<AnswerModel> answer;

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

    public Integer getIspic() {
        return ispic;
    }

    public void setIspic(Integer ispic) {
        this.ispic = ispic;
    }

    public List<AnswerModel> getAnswer() {
        return answer;
    }

    public void setAnswer(List<AnswerModel> answer) {
        this.answer = answer;
    }
}
