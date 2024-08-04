package com.finut.finut_server.config.auth.dto;

import com.finut.finut_server.domain.user.Role;
import com.finut.finut_server.domain.user.Users;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    private String accessToken;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture, String accessToken){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.accessToken = accessToken;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes, String accessToken) {

        if("naver".equals(registrationId)) {
            return ofNaver("id", attributes, accessToken);
        }
        else if("kakao".equals(registrationId)){
            return ofKakao("id", attributes, accessToken);
        }
        return ofGoogle(userNameAttributeName, attributes, accessToken);
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes, String accessToken) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .accessToken(accessToken)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes, String accessToken) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("properties");
        response.put("email", ((Map<String, Object>) attributes.get("kakao_account")).get("email"));
        response.put("id", attributes.get("id"));

        return OAuthAttributes.builder()
                .name((String) response.get("nickname"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .accessToken(accessToken)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes, String accessToken) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .accessToken(accessToken)
                .nameAttributeKey(userNameAttributeName).build();
    }

    public Users toEntity() {
        return Users.builder().name(name)
                .email(email)
                .picture(picture)
                .accessToken(accessToken)
                .role(Role.USER)
                .build();
    }
}
