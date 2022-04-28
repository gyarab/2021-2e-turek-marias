/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import shared.Sender;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import shared.Card;
import shared.CardManager;
import shared.Receiver;

/**
 *
 * @author jakub
 */
public class Client extends Thread {

    private static Client client;
    private Socket clientSocket;
    private List<String> playersNames;
    private final String name;
    private final Sender sender;
    private final Receiver receiver;
    private final GameScreen gameScreen;
    private final ScreenManager screenManager;
    private Card lastCard;
    private Card playWith;
    private List<Card> cards;
    private String trumphColor;

    GameScreenController controller;

    private Client(String address, int port, String name, GameScreenController cont) {
        try {
            this.clientSocket = new Socket(address, port);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.name = name;
        receiver = new Receiver();
        sender = new Sender();
        controller = cont;
        playersNames = new ArrayList<>();
        lastCard = null;
        gameScreen = new GameScreen();
        screenManager = new ScreenManager();

    }

    public static Client getClientInstance(String address, int port, String name, GameScreenController cont) {
        if (client == null) {
            client = new Client(address, port, name, cont);
        }
        return client;

    }

    public static Client getClientInstance() {
        return client;
    }

    public void sendData(Object Data) {

        sender.SingelsendData(Data, clientSocket);

    }

    private void initialize() {
        sender.SingelsendData(name, clientSocket);
        playersNames = (ArrayList<String>) receiver.read(clientSocket);
        cards = (List<Card>) receiver.read(clientSocket);

        Platform.runLater(
                () -> {
                    controller.initializePlayersInfo(playersNames, playersNames.indexOf(name));
                    controller.dealCards(gameScreen.getImagesStreams(cards));
                }
        );

    }

    @Override
    public void run() {
        initialize();
        String roundColor = null;
        int playedRound = 0;
        while (playedRound < 8) {
            try {
                Object data = receiver.read(clientSocket);
                if (data.getClass() == Class.forName("java.lang.Boolean")) {
                    if (trumphColor == null) {
                        Platform.runLater(
                                () -> {
                                    try {
                                        screenManager.showDialog("trumphDialog.fxml");
                                        screenManager.showDialog("playWithDialog.fxml");
                                    } catch (IOException ex) {
                                        screenManager.showExceptio(ex);
                                    }
                                }
                        );
                    }
                    if (roundColor == null) {
                        Platform.runLater(
                                () -> {
                                    activatePlayableCards("O", "O", "O", cards);
                                }
                        );

                    } else {

                        Platform.runLater(
                                () -> {

                                    activatePlayableCards(roundColor, lastCard.getColor(), lastCard.getValue(), cards);
                                }
                        );

                    }
                }
                if (data.getClass() == Class.forName("shared.Card")) {
                    Card c = (Card) data;
                    if (Arrays.asList(CardManager.COLORS_OF_CARDS).contains(c.getColor())) {
                        lastCard = c;
                        Platform.runLater(
                                () -> {
                                    showPlayedCard(c);
                                }
                        );

                    } else {
                        Platform.runLater(
                                () -> {
                                    setPlayWith(c, (String) receiver.read(clientSocket));
                                });
                    }

                }
                if (data.getClass() == Class.forName("java.lang.Integer")) {
                    int points = (int) data;
                    Platform.runLater(
                            () -> {
                                updatePoints((String) receiver.read(clientSocket), points);
                            }
                    );

                    playedRound++;
                }
                if (data.getClass() == Class.forName("java.lang.String")) {
                    Platform.runLater(
                            () -> {
                                controller.updateSateLabel((String) receiver.read(clientSocket));
                            }
                    );

                }

            } catch (ClassNotFoundException ex) {
                screenManager.showExceptio(ex);
            }
            waitUntilNextTurn();
        }

    }

    public void activatePlayableCards(String roundColor, String lastColor, String lastValue, List<Card> cards) {
        boolean[] indexes = null;
        boolean oneActivate = false;
        if (lastColor.equals(roundColor)) {
            indexes = gameScreen.findPlayableCards(roundColor, lastValue, cards);
        }
        if (Arrays.asList(indexes).indexOf(true) != -1) {
            if (lastColor.equals(trumphColor)) {
                indexes = gameScreen.findPlayableCards(trumphColor, lastValue, cards);
            } else {
                indexes = gameScreen.findPlayableCards(trumphColor, "O", cards);
            }
        }
        if (!oneActivate) {
            Arrays.fill(indexes, true);
        }
        controller.activatePlayable(indexes);
    }

    public void waitUntilNextTurn() {
        controller.deactivateAllCards();
    }

    public void showPlayedCard(Card playedCard) {
        controller.showPlayedCard(gameScreen.getImageInputStream(playedCard));

    }

    public void updatePoints(String playerName, int points) {
        controller.updatePoints(playerName, points);
    }

    public void setPlayWith(Card playWith, String playerName) {
        controller.setPlayWith(playWith, playerName);
    }

}
