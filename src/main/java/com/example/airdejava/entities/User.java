package com.example.airdejava.entities;

public class User {
    // Members
    private String _username;
    private int _role; // 1 admin, 2 membre, guest 3
    private String _roleName = "guest";

    // Constructor
    public User(String username, int role){
        this._username = username;
        this._role = role;
        roleName();
    }

    // Function
    private void roleName(){
        if(_role == 1){
            _roleName = "admin";
        }else if(_role == 2){
            _roleName = "member";
        }else if(_role == 3 ){
            _roleName = "guest";
        }
    }

    // Setter & Getter
    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public int get_role() {
        return _role;
    }

    public void set_role(int _role) {
        this._role = _role;
    }

    public String get_roleName() {
        return _roleName;
    }

    public void set_roleName() {
        roleName();
    }
}
