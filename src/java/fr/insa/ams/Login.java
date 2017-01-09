package fr.insa.ams;

import java.io.UnsupportedEncodingException;
import java.security.*;

public class Login implements Databasable {

    private String password;
    private int id;
    private Group group;

    public Login() {}
    public Login(int id, String password, Group group) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(password.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(2*hash.length);
            for(byte b : hash){
                sb.append(String.format("%02x", b&0xff));
            }
            this.password = sb.toString();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            this.password = password;
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            this.password = password;
        }
        this.id = id;
        this.group = group;
    }

    public String getPassword() { return this.password;}
    public int getId() { return this.id;}
    public Group getGroup() { return this.group;}

    public void setPassword(String password) { this.password = password;}
    public void setId(int id) { this.id = id;}
    public void setGroup(Group group) { this.group = group;}
}
