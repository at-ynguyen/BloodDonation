package com.project.ync.blooddonation.api.body;

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
public class ChangePasswordBody {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
