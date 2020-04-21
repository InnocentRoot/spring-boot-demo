package com.root.demo.request.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignUpRequest {

    @NotEmpty
    @NotNull
    private String firstName;

    @NotEmpty
    @NotNull
    @Size(min = 3, max = 10)
    private String lastName;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 6)
    private String password;

    @NotEmpty
    @Size(min = 3, max = 10)
    private String userName;
}
