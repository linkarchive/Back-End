package project.linkarchive.backend.jwt;

public interface JwtProperties {

    String SECRET = "qwertyuiopasdfghjkl123qwertyuiopasdfghjkl123";
    int EXPIRATION_TIME =  864000000;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";

}