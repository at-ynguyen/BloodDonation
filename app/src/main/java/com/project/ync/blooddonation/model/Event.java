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
public class Event {
    private int id;
    private User user;
    @SerializedName("eventName")
    private String eventName;
    private Organization organization;
    private boolean bloodType;
    private long time;
    private String content;
    private String address;
    private Town town;
    private boolean status;
    @SerializedName("createAt")
    private long createAt;
}
