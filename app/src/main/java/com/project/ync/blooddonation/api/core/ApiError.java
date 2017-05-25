package com.project.ync.blooddonation.api.core;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Api Error.
 *
 * @author YNC
 */
@Data
@AllArgsConstructor(suppressConstructorProperties = true)
public class ApiError {
    private int code;
    private String message;
}
