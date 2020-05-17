package pl.edu.agh.soa.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String login;

    private String hashedPassword;
}
