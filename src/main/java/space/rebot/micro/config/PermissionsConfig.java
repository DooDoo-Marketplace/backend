package space.rebot.micro.config;

import org.springframework.stereotype.Component;

import java.util.HashMap;

/*
This is permissions config to manage allowed urls.
Just add your link patterns (regex) to the hashmap.
 */
@Component
public class PermissionsConfig {
    public final HashMap<String, String[]> allowedUrls;

    public PermissionsConfig(){
        this.allowedUrls = new HashMap<>();

        //UNAUTHORIZED permissions
        String[] unauthorized = {
                ".*/api/v1/auth/login",
                ".*/api/v1/auth/code"
        };

        //USER permissions
        String[] user = {
                ".*/api/v1/hello.*",
                ".*/api/v1/user.*",
                ".*/api/v1/cart.*",
                ".*/api/v1/auth/logout.*"
        };

        //ADMIN permissions
        String[] admin = {

        };

        //Do not edit this part if you are not adding new role

        allowedUrls.put(RoleConfig.UNAUTHORIZED.toString(), unauthorized);
        allowedUrls.put(RoleConfig.ROLE_USER.toString(), user);
        allowedUrls.put(RoleConfig.ROLE_ADMIN.toString(), admin);

    }
}
