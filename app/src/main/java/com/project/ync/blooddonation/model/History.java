package com.project.ync.blooddonation.model;

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
public class History {
    private int id;
    private User user;
    private long time;
    private String note;
}
