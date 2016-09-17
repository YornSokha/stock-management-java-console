
package kh.com.kshrd.dbutility;

import java.io.Serializable;

public class DbConfiguration implements Serializable{
    private int port_number;
    private String username;
    private String password;
    private String database_name;

    /**
     * @return the port_number
     */
    public int getPort_number() {
        return port_number;
    }

    /**
     * @param port_number the port_number to set
     */
    public void setPort_number(int port_number) {
        this.port_number = port_number;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the database_name
     */
    public String getDatabase_name() {
        return database_name;
    }

    /**
     * @param database_name the database_name to set
     */
    public void setDatabase_name(String database_name) {
        this.database_name = database_name;
    }
}
