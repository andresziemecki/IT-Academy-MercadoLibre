public class Token {
    public String username;
    public String password;
    public String token;

    public Token(String username, String password, String token) {
        this.username = username;
        this.password = password;
        this.token = token;
    }
    public Token(){}

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "{"+
                "\"username\":\"" + username + '\"' + "," +
                "\"token\":\"" + token + '\"' +
                '}';
    }
}
