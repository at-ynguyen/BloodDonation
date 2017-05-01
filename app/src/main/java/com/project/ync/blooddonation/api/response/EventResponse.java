package com.project.ync.blooddonation.api.response;

import com.google.gson.annotations.SerializedName;
import com.project.ync.blooddonation.model.Event;

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
public class EventResponse {
    @SerializedName("members")
    private int numbers;
    @SerializedName("event")
    private Event event;
}
