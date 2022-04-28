/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import shared.Card;

/**
 * FXML Controller class
 *
 * @author jakub
 */
public class GameScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    AnchorPane frontPane; // odkaz na AnchorPane definovaný pomocí Scene Builderu v .fxml souboru
    @FXML
    Label targetAreaLabel;
    @FXML
    GridPane backGrid;
    @FXML
    ImageView played1View;
    ImageView played2View;
    ImageView played3View;
    @FXML
    Label name1Label;
    @FXML
    Label name2Label;
    @FXML
    Label name3Label;
    @FXML
    Label name4Label;
    @FXML
    Label points1Label;
    @FXML
    Label points2Label;
    @FXML
    Label points3Label;
    @FXML
    Label points4Label;
    @FXML
    Label playWith1Label;
    @FXML
    Label playWith2Label;
    @FXML
    Label playWith3Label;
    @FXML
    Label trumphColorLabel;
    @FXML
    Label sateLabel;

    ImageViewWithCoordinates[] cardsImage;
    ImageView[] playedCatds;
    Label[] nameLabels;
    Label[] pointsLabels;
    Label[] playWithLabels;
    int numberPlayedCards;
    ArrayList<String> names;

    @Override
    // Zavolá se při otevření daného okna, takže zde volám metody, které mají přidat do okny prvky před jeho zobrazením
    public void initialize(URL url, ResourceBundle rb) {
        playedCatds = new ImageView[]{played1View, played2View, played2View};
        nameLabels = new Label[]{name4Label, name1Label, name2Label, name3Label};
        pointsLabels = new Label[]{points4Label, points1Label, points2Label, points3Label};
        playWithLabels = new Label[]{null, playWith1Label, playWith2Label, playWith3Label};
        cardsImage = initializeCards();
        addCards(cardsImage);
        deactivateAllCards();
        numberPlayedCards = 0;

    }

    private void handelOnMouseEntered(MouseEvent event) {
        ImageViewWithCoordinates i = (ImageViewWithCoordinates) event.getSource();

        if (!i.isInactive()) {
            i.setCursor(Cursor.HAND);
        }

    }

    private void handleOnMousePressed(MouseEvent event) {
        ImageViewWithCoordinates i = (ImageViewWithCoordinates) event.getSource();
        if (!i.isInactive()) {
            i.setCursorX(i.getLayoutX() - event.getSceneX());
            i.setCursorY(i.getLayoutY() - event.getSceneY());
            i.setCursor(Cursor.CLOSED_HAND);
        }
    }

    private void handleOnMouseDragged(MouseEvent event) {
        ImageViewWithCoordinates i = (ImageViewWithCoordinates) event.getSource();
        if (!i.isInactive()) {
            i.setLayoutX(event.getSceneX() + i.getCursorX());
            i.setLayoutY(event.getSceneY() + i.getCursorY());
        }
    }

    private void handleOnMouseReleased(MouseEvent event) {
        ImageViewWithCoordinates i = (ImageViewWithCoordinates) event.getSource();
        boolean b = i.getLayoutX() > 750 && i.getLayoutX() < 900;
        boolean a = i.getLayoutY() > 275 && i.getLayoutY() < 500;
        if (b && a && !i.isInactive()) {
            
            i.setLayoutX(300);
            i.setLayoutY(275);
            Client.getClientInstance().sendData(a);

        } else {
            i.setLayoutX(i.getDefaultX());
            i.setLayoutY(i.getDefaultY());
        }

    }

    @FXML
    private ImageViewWithCoordinates[] initializeCards() { // Nepříliš duležitá metoda, která vrátí pole ImageView s nastavenými parametry
        ImageViewWithCoordinates[] imageViews = new ImageViewWithCoordinates[8];
        InputStream u = getClass().getResourceAsStream("/pictures/card.png");
        Image image = new Image(u);

        for (int i = 0; i < 8; i++) {
            imageViews[i] = new ImageViewWithCoordinates(image);
            imageViews[i].setFitHeight(225);
            imageViews[i].setFitWidth(150);
            imageViews[i].setDefaultX(i * 150);
            imageViews[i].setDefaultY(525);
            imageViews[i].setOnMousePressed(event -> handleOnMousePressed(event));
            imageViews[i].setOnMouseDragged(event -> handleOnMouseDragged(event));
            imageViews[i].setOnMouseReleased(event -> handleOnMouseReleased(event));
            imageViews[i].setOnMouseEntered(event -> handelOnMouseEntered(event));

        }
        return imageViews;
    }

    @FXML
    private void addCards(ImageViewWithCoordinates[] images) { // metoda, která přidá prvky z pole vráceného předchozí metodou do AnchorPanu.

        for (ImageViewWithCoordinates image : images) {
            frontPane.getChildren().add(image); // přidání ImageView do AnchorPanu
            // Nastavení jejich umístění na požadované souřadnice
            image.setLayoutX(image.getDefaultX());
            image.setLayoutY(image.getDefaultY());
        }

    }

    @FXML
    public void deactivateAllCards() {
        for (ImageViewWithCoordinates card : cardsImage) {
            card.setInactive(true);
        }

    }

    @FXML
    public void activatePlayable(boolean[] indexes) {
        for (int i = 0; i < indexes.length; i++) {

            cardsImage[i].setInactive(!indexes[i]);

        }
    }

    @FXML
    public void activateAll() {
        for (ImageViewWithCoordinates i : cardsImage) {
            i.setInactive(false);
        }
    }

    @FXML
    public void dealCards(InputStream[] imagesURL) {
        for (int i = 0; i < imagesURL.length; i++) {

            cardsImage[i].setImage(new Image(imagesURL[i]));

        }

    }

    @FXML
    public void showPlayedCard(InputStream inputStream) {

        playedCatds[numberPlayedCards].setImage(new Image(inputStream));
        numberPlayedCards++;

    }

    @FXML
    public void initializePlayersInfo(List<String> names, int thisIndex) {

        nameLabels[thisIndex].setText("Jméno: " + names.get(thisIndex));
        for (int i = 0; i < 4; i++) {
            if (i != thisIndex) {
                nameLabels[i].setText("Jméno: " + names.get(i));

            }

        }

    }

    @FXML
    public void updatePoints(String playerName, int points) {
        String s = "Jméno: " + playerName;
        for (int i = 0; i < 4; i++) {

            if (nameLabels[i].getText().equals(s)) {
                pointsLabels[i].setText("Body: " + points);
            }

        }

    }

    @FXML
    public void setPlayWith(Card playWith, String playerName) {
        String s = "Jméno: " + playerName;
        for (int i = 0; i < 4; i++) {

            if (nameLabels[i].getText().equals(s)) {
                playWithLabels[i].setText("Hraje s: " + playWith.getColor() + " " + playWith.getValue());
            }

        }
    }

    public void setTrumphColor(String trumphColor) {
        trumphColorLabel.setText(trumphColorLabel.getText() + " " + trumphColor);
    }
    @FXML
    public void updateSateLabel(String message){
    
        sateLabel.setText("Stav: " + message);
    }
}
