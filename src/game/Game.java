/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import shared.Sender;
import shared.Receiver;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.Card;

/**
 *
 * @author jakub
 */
public class Game extends Thread {

    private ServerSocket server;
    private Socket[] playerSockets;
    private final Receiver receiver;
    private final Player[] players;
    private String trumphColor;
    private Card playWith;
    Sender sender;
    GameManager manager;
    ServerInfoSender infoSender;
    int port;
    String name;

    public Game(int port, String name) throws IOException {
        receiver = new Receiver();

        players = new Player[]{new Player(0, null), new Player(0, null),
            new Player(0, null), new Player(0, null)};
        manager = new GameManager();
        this.port = port;
        this.name = name;

    }

    private void creatGame(int port, String name) throws UnknownHostException, IOException {
        String message = name + ":" + String.valueOf(port) + ":" + InetAddress.getLocalHost().getHostAddress().trim();
        infoSender = new ServerInfoSender(message);
        infoSender.start();

        server = new ServerSocket(port);

        connectPlayers();
        sender = new Sender();
        trumphColor = null;

    }

    private void connectPlayers() {

        Socket[] sockets = new Socket[4];
        for (int i = 0; i < sockets.length; i++) {
            try {
                sockets[i] = server.accept();
            } catch (IOException ex) {
                continue;
            }

            System.err.println("Join");

        }
        infoSender.interrupt();
        playerSockets = sockets;
    }

    private void setAndSendNames() throws IOException {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            String name = (String) receiver.read(playerSockets[i]);
            players[i].setName(name);
            names.add(name);

        }
        names.add(4, players[0].getName());
        sender.MultisendData(names, null, playerSockets);
    }

    private void sendPlayersCards() throws IOException {

        for (int i = 0; i < 4; i++) {

            players[i].setCards(manager.getPlayersCardsSet(i));
            System.out.println(players[i].getCards().size());
            sender.SingelsendData(players[i].getCards(), playerSockets[i]);

        }

    }

    private void initialize() throws IOException {

        try {
            setAndSendNames();
            sendPlayersCards();
            boolean b = true;
            sender.SingelsendData(b, playerSockets[0]);
            playWith = (Card) receiver.read(playerSockets[0]);
            trumphColor = stringToColor((String) receiver.read(playerSockets[0]));
            manager.setTRUMPHCOLOR(trumphColor);

            sender.MultisendData(trumphColor, null, playerSockets);
            sender.MultisendData(playWith, null, playerSockets);

        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void run() {

        try {
            creatGame(port, name);
            initialize();
            startGameLoop();
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private Card play(int index) {
        boolean startPlay = true;
        try {
            sender.SingelsendData(startPlay, playerSockets[index]);
            sender.MultisendData("Hraje: " + players[index].getName(), playerSockets[index], playerSockets);
            sender.SingelsendData("Jste na tahu", playerSockets[index]);
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        Card c = (Card) receiver.read(playerSockets[index]);
        players[index].updateCards(c);
        try {
            sender.MultisendData(c, playerSockets[index], playerSockets);
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

    private List<Card> playOneRound(int startIndex) {
        List<Card> playedCards = new ArrayList<>();
        for (int i = startIndex; i < playerSockets.length; i++) {
            playedCards.set(i, play(i));
        }
        for (int i = 0; i < startIndex; i++) {
            playedCards.set(i, play(i));
        }
        return playedCards;
    }

    private void evaluateRoud(List<Card> playedCards) {

        int indexOfWin = manager.getWinerOfRound(playedCards, 4);
        int points = manager.getRoundPoints(playedCards);
        players[indexOfWin].updatePoints(points);
        try {
            sender.MultisendData(points, null, playerSockets);
            sender.MultisendData(players[indexOfWin].getName(), null, playerSockets);
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void startGameLoop() throws IOException {
        int startIndex = 0;
        for (int i = 0; i < 8; i++) {
            if (startIndex == 4) {
                startIndex = 0;
            }

            evaluateRoud(playOneRound(startIndex));
            startIndex++;
        }
        sender.MultisendData(evaluateGame(), null, playerSockets);

    }

    private String evaluateGame() {
        int indexOfplayWith = getPlayWithIndex(cratPlayWith(playWith));
        String team2Names = "";
        int team2Points = 0;
        int team1Points = 0;
        String team1Names = "";
        if (indexOfplayWith != -1) {
            team1Points = players[0].getPoints() + players[indexOfplayWith].getPoints();
            team1Names = players[0].getName() + ", " + players[indexOfplayWith].getName();
        } else {
            team1Points = players[0].getPoints();
            team1Names = players[0].getName();

        }

        for (int i = 1; i < players.length; i++) {
            if (i != indexOfplayWith) {
                team2Points = team2Points + players[i].getPoints();
                team2Names = team2Names + ", " + players[i].getName();
            }
        }
        if (team1Points > team2Points) {

            return "Vyhrává: " + team1Names;
        } else {
            return "Vyhrává: " + team2Names;
        }

    }

    private Card cratPlayWith(Card playWithFrom) {
        String color;
        String value;
        if (playWithFrom.getValue().equals("Spodek")) {
            value = "B";
        } else if (playWithFrom.getValue().equals("Svršek")) {
            value = "T";
        } else {
            value = playWithFrom.getValue().substring(0, 1);
        }
        color = stringToColor(playWithFrom.getColor());

        return new Card(value, color);

    }

    private String stringToColor(String colorString) {
        String color;
        if (colorString.equals("Žaludy")) {
            color = "Ž";

        } else {
            color = colorString.substring(0, 1);
        }
        return color;
    }

    private int getPlayWithIndex(Card playWith) {

        for (int i = 0; i < players.length; i++) {
            if (manager.getPlayersCardsSet(i).contains(playWith)) {
                return i;
            }
        }
        return -1;
    }

}
