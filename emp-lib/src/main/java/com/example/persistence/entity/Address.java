package com.example.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="ADDRESS")
public class Address {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    @JsonIgnore
    private Integer addrId;

    @JsonProperty("addrText")
    @Column(name="addr_text")
    private String addrText;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("zip")
    private Integer zip;

    public Integer getAddrId() {
        return addrId;
    }

    public void setAddrId(Integer id) {
        this.addrId = id;
    }
}
