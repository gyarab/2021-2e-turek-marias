/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jakub
 */
public class Receiver  {

    public Receiver() {

    }

    public Object read(Socket playerSocket) {

        ObjectInputStream in = null;
        Object o = null;
        try {
            in = new ObjectInputStream(playerSocket.getInputStream());

            try {
                o = in.readObject();
            } catch (ClassNotFoundException ex) {

            }
            return o;
        } catch (IOException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);

        }
        return o;
    }

}
