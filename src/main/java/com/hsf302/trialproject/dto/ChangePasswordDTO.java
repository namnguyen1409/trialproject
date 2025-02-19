package com.hsf302.trialproject.dto;

import com.hsf302.trialproject.validation.annotation.FieldsEqual;
import com.hsf302.trialproject.validation.annotation.PasswordStrength;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldsEqual(field1 = "password", field2 = "confirmPassword", message = "Mật khẩu không khớp")
public class ChangePasswordDTO {
    @NotBlank(message = "Mật khẩu cũ không được để trống")
    private String oldPassword;
    @NotBlank(message = "Mật khẩu không được để trống")
    @PasswordStrength(message = "Mật khẩu không đủ mạnh")
    private String password;
    private String confirmPassword;
}
