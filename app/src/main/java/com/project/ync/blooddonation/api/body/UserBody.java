package com.project.ync.blooddonation.api.body;

import com.google.gson.annotations.SerializedName;
import com.project.ync.blooddonation.model.Town;

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
public class UserBody {
    @SerializedName("email")
    private String email;
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("cardId")
    private String cardId;
    @SerializedName("fullName")
    private String fullName;
    @SerializedName("birthDay")
    private String birthDay;
    @SerializedName("address")
    private String address;
    @SerializedName("town")
    private Town town;
    @SerializedName("gender")
    private boolean gender;
    @SerializedName("weight")
    private float weight;
    @SerializedName("bloodType")
    private String bloodType;
}
