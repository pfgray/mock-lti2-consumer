package net.paulgray.mocklti2.web.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Person {

    @JsonProperty("@type")
    String _type;

    @JsonProperty("sourcedId")
    String sourcedId;

    @JsonProperty("userId")
    String userId;

    @JsonProperty("email")
    String email;

    @JsonProperty("familyName")
    String familyName;

    @JsonProperty("name")
    String name;

    @JsonProperty("givenName")
    String givenName;

    @JsonProperty("image")
    String image;

    public Person(String sourcedId, String userId, String email, String familyName, String name, String givenName, String image) {
        this._type="LISPerson";
        this.sourcedId = sourcedId;
        this.userId = userId;
        this.email = email;
        this.familyName = familyName;
        this.name = name;
        this.givenName = givenName;
        this.image = image;
    }
}
