/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jakub
 */
public class Sender {

    public Sender() {

    }

    public void MultisendData(Object data, Socket sender, Socket[] clientSockets) throws IOException {
        for (int i = 0; i < clientSockets.length; i++) {

            if (!clientSockets[i].equals(sender)) {

                ObjectOutputStream out = new ObjectOutputStream(clientSockets[i].getOutputStream());
                out.writeObject(data);
                System.err.println("Send");
                out.flush();
            }

        }

    }

    public void SingelsendData(Object data, Socket reciver){

        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(reciver.getOutputStream());
            out.writeObject(data);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
