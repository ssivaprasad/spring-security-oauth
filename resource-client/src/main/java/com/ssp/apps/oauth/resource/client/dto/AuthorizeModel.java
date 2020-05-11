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
public class AuthorizeModel {
    private String response_type;
    private String client_id;
    private String scope;
    private String redirect_uri;
}
