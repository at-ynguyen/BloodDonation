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
public class RegisterBody {
    private String email;
    private String password;
    @SerializedName("full_name")
    private String fullName;
    @SerializedName("card_id")
    private String cardId;
    @SerializedName("gender")
    private boolean gender;

    public RegisterBody() {
    }
}
