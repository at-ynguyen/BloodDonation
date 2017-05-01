package com.project.ync.blooddonation.model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author YNC
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(suppressConstructorProperties = true)
public class User {
    private int id;
    private String email;
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("cardId")
    private String cardId;
    @SerializedName("fullName")
    private String fullName;
    @SerializedName("birthDay")
    private long birthDay;
    @SerializedName("address")
    private String address;
    private Town town;
    private boolean gender;
    private float weight;
    @SerializedName("bloodType")
    private String bloodType;
}
