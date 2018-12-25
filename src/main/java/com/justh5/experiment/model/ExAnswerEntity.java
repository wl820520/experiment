package com.justh5.experiment.model;

public class ExAnswerEntity {
    private Integer rownum;
    private Integer id;
    private  Integer main_id;
    private String mainName;
    private String mainTitle;
    private Integer user_id;
    private String userName;
    private Integer station_id;
    private String stationName;
    private Integer score;
    private String answer;
    private Long create_time;
    private Long end_time;
    private Integer isaddscore;
    private String pdf;
    private String usercode;
    private Integer main_type;

    public Integer getMain_type() {
        return main_type;
    }

    public void setMain_type(Integer main_type) {
        this.main_type = main_type;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public Integer getIsaddscore() {
        return isaddscore;
    }

    public void setIsaddscore(Integer isaddscore) {
        this.isaddscore = isaddscore;
    }

    public Long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Long end_time) {
        this.end_time = end_time;
    }

    public Integer getRownum() {
        return rownum;
    }

    public void setRownum(Integer rownum) {
        this.rownum = rownum;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMain_id() {
        return main_id;
    }

    public void setMain_id(Integer main_id) {
        this.main_id = main_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getStation_id() {
        return station_id;
    }

    public void setStation_id(Integer station_id) {
        this.station_id = station_id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
