/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jakub
 */
public class ServerInfoSender extends Thread {

    private DatagramSocket socket;
    private byte[] buffer;
    private  InetAddress adress;
    private boolean interupted;
    private final int COLDOWN;
    private final int port;

    public ServerInfoSender(String message) {
        try {
            socket = new DatagramSocket();
            socket.setBroadcast(true);
            adress = InetAddress.getByName("255.255.255.255");
        } catch (SocketException ex) {
            Logger.getLogger(ServerInfoSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServerInfoSender.class.getName()).log(Level.SEVERE, null, ex);
        }
        buffer = message.getBytes();
        interupted = false;
        COLDOWN = 2000;
        port =49152;
    }

    @Override
    public void run() {
        while (!interupted) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, adress, port);
            try {
                socket.send(packet);

            } catch (IOException ex) {
                Logger.getLogger(ServerInfoSender.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                Thread.sleep(COLDOWN);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerInfoSender.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        socket.close();
    }

    @Override
    public void interrupt() {
        interupted = true;
    }

}
