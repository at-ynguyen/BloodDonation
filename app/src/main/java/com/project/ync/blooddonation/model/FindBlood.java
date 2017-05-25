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
public class FindBlood {
    private int id;
    @SerializedName("user")
    private User user;
    @SerializedName("postName")
    private String postName;
    @SerializedName("postContent")
    private String postContent;
    @SerializedName("bloodType")
    private String bloodType;
    @SerializedName("address")
    private String address;
    @SerializedName("image")
    private String image;
    @SerializedName("approved")
    private boolean approved;
    @SerializedName("done")
    private boolean done;
    @SerializedName("status")
    private boolean status;
    @SerializedName("createAt")
    private long createAt;
}
