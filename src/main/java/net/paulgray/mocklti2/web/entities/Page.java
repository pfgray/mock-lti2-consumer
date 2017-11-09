package net.paulgray.mocklti2.web.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Page<T> {

    @JsonProperty("@context")
    String _context;
    @JsonProperty("@type")
    String _type;
    @JsonProperty("@id")
    String _id;

    String nextPage;

    // todo: wut.. why is this on Page???
    String differences;

    @JsonProperty
    T pageOf;

    public Page(String _id, String nextPage, String differences, T pageOf) {
        this._id = _id;
        this.nextPage = nextPage;
        this.differences = differences;
        this.pageOf = pageOf;
    }
}
