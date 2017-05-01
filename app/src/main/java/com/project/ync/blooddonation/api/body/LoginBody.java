package com.project.ync.blooddonation.api.body;

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
public class loginBody {
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    public loginBody() {
    }
}
