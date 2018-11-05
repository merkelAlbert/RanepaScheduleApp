package com.assistant.albert.ranepaschedule;

import java.util.ArrayList;
import java.util.List;

public class ScheduleItem {
    private String _dayOfWeek;
    private List<String> _times;
    private List<String> _subjects;

    public String getDayOfWeek() {
        return _dayOfWeek;
    }

    public List<String> getSubjects() {
        return _subjects;
    }

    public List<String> getTimes() {
        return _times;
    }

    public void setDayOfWeek(String _dayOfWeek) {
        this._dayOfWeek = _dayOfWeek;
    }

    public void setSubjects(List<String> _subjects) {
        this._subjects = _subjects;
    }

    public void setTimes(List<String> _times) {
        this._times = _times;
    }
}
