package net.paulgray.mocklti2.web;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

/**
 * Created by nicole on 11/5/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LineItem {

    @JsonProperty("@id")
    String _id;

    @JsonProperty("@type")
    String _type;

    @JsonProperty("http://purl.imsglobal.org/vocab/lis/v2/outcomes#assignedActivity")
    JsonNode activity;

    @JsonProperty("http://purl.imsglobal.org/vocab/lis/v2/outcomes#label")
    JsonNode label;

    @JsonProperty("http://purl.imsglobal.org/vocab/lis/v2/outcomes#lineItemOf")
    JsonNode lineItemOf;

    @JsonProperty("http://purl.imsglobal.org/vocab/lis/v2/outcomes#scoreConstraints")
    JsonNode scoreConstraints;

    @JsonProperty("http://purl.imsglobal.org/vocab/lis/v2/outcomes#results")
    String results;

    @JsonProperty("https://www.learningobjects.com/vocab/lis/v2/outcomes#evaluationType")
    JsonNode evaluationType;

    @JsonProperty("http://purl.imsglobal.org/vocab/lis/v2/outcomes#results")
    Optional<String> resultsUrl;

    public LineItem() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public JsonNode getActivity() {
        return activity;
    }

    public void setActivity(JsonNode activity) {
        this.activity = activity;
    }

    public JsonNode getLabel() {
        return label;
    }

    public void setLabel(JsonNode label) {
        this.label = label;
    }

    public JsonNode getLineItemOf() {
        return lineItemOf;
    }

    public void setLineItemOf(JsonNode lineItemOf) {
        this.lineItemOf = lineItemOf;
    }

    public JsonNode getScoreConstraints() {
        return scoreConstraints;
    }

    public void setScoreConstraints(JsonNode scoreConstraints) {
        this.scoreConstraints = scoreConstraints;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public JsonNode getEvaluationType() {
        return evaluationType;
    }

    public void setEvaluationType(JsonNode evaluationType) {
        this.evaluationType = evaluationType;
    }

    public Optional<String> getResultsUrl() {
        return resultsUrl;
    }

    public void setResultsUrl(Optional<String> resultsUrl) {
        this.resultsUrl = resultsUrl;
    }
}
