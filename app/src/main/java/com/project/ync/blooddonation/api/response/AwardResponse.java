package com.project.ync.blooddonation.api.response;

import com.google.gson.annotations.SerializedName;
import com.project.ync.blooddonation.model.User;

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
public class AwardResponse {
    @SerializedName("number")
    private int number;
    @SerializedName("user")
    private User user;
}
