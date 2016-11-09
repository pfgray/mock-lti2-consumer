package net.paulgray.mocklti2.web.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.paulgray.mocklti2.web.entities.Value;

/**
 * Created by nicole on 11/8/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

    @JsonProperty("http://purl.imsglobal.org/vocab/lis/v2/outcomes#totalScore")
    private Value totalScore;

    @JsonProperty("http://purl.imsglobal.org/vocab/lis/v2/outcomes#resultAgent")
    private Agent student;

    public Result() {
    }

    public Value getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Value totalScore) {
        this.totalScore = totalScore;
    }

    public Agent getStudent() {
        return student;
    }

    public void setStudent(Agent student) {
        this.student = student;
    }
}
