package com.ssp.apps.oauth.resource.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccessTokenModel {
    private String code;
    private String grant_type;
    private String redirect_uri;
    private String client_Id;
    private String client_secret;
}
