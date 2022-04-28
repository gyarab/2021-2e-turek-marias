/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.util.Objects;

/**
 *
 * @author jakub
 */
public class ServerPrametr {

    private final String name;
    private final String ip;
    private final int port;

    public ServerPrametr(String name, String ip, int port) {
        this.name = name;
        this.ip = ip;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    @Override
    public boolean equals(Object obj) {
        ServerPrametr par = (ServerPrametr) obj;
        return ip.equals(par.getIp()) && port == par.getPort() && name.equals(par.getName());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + Objects.hashCode(this.ip);
        hash = 47 * hash + this.port;
        return hash;
    }

}
