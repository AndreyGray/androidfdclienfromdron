package andreyskakunenko.androidfdclienfromdron.Models;

public class User {
    private String user_id;
    private String userName;
    private String txtEmail;
    private String txtBirthday;
    private String txtFriends;
    private String avatarUrl;

    public User() {
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTxtEmail() {
        return txtEmail;
    }

    public void setTxtEmail(String txtEmail) {
        this.txtEmail = txtEmail;
    }

    public String getTxtBirthday() {
        return txtBirthday;
    }

    public void setTxtBirthday(String txtBirthday) {
        this.txtBirthday = txtBirthday;
    }

    public String getTxtFriends() {
        return txtFriends;
    }

    public void setTxtFriends(String txtFriends) {
        this.txtFriends = txtFriends;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
