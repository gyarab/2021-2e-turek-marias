/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jakub
 */
public class ExistingGameFinder extends Thread {

    private final byte[] buffer;
    private final List<ServerPrametr> existingGames;
    MulticastSocket socket;
    InetAddress group;
    boolean interupted;
    private final int port;

    public ExistingGameFinder() {
        port = 49152;
        try {
            socket = new MulticastSocket(port);
           
        } catch (IOException ex) {

        }
        existingGames = new ArrayList<>();
        buffer = new byte[1024];
        interupted = false;
    }

    @Override
    public void run() {

      
        while (!interupted) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            try {
                socket.receive(packet);
            } catch (IOException ex) {
                Logger.getLogger(ExistingGameFinder.class.getName()).log(Level.SEVERE, null, ex);
            }
            String[] s = new String(packet.getData(), 0, packet.getLength()).split(":");
            ServerPrametr parametr = new ServerPrametr(s[0], s[2], Integer.parseInt(s[1]));         
            if (!existingGames.contains(parametr)) {
                existingGames.add(parametr);
            }

        }
        socket.close();

    }

    public synchronized List<ServerPrametr> getExistingGames() {
        List<ServerPrametr> sp = List.copyOf(existingGames);
        existingGames.clear();
        return sp;
    }

    @Override
    public synchronized void interrupt() {
        interupted = true;
    }

}
