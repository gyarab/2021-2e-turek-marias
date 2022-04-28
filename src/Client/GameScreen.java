/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import shared.Card;

/**
 *
 * @author jakub
 */
public class GameScreen {

    public GameScreen() {

    }

   

   

    public boolean[] findPlayableCards(String Color, String Value, List<Card> cards) {
        boolean[] indexes = new boolean[8];
        Arrays.fill(indexes, false);
        boolean oneBiggerLast = false;
        for (int i = 0; i < cards.size(); i++) {

            boolean b = cards.get(i).compareTo(Value) > 0;
            if (cards.get(i).getColor().equals(Color) && b) {
                oneBiggerLast = true;
                indexes[i] = true;

            } else if (cards.get(i).getColor().equals(Color) && !oneBiggerLast) {
                indexes[i] = true;

            }

        }

        return indexes;

    }

    public InputStream[] getImagesStreams(List<Card> cards) {
        InputStream[] ImagesStreams = new InputStream[cards.size()];
        for (int i = 0; i < cards.size(); i++) {
           ImagesStreams[i] = getClass().getResourceAsStream("/pictures/" + cards.get(i).toString() + ".png");

        }
        return ImagesStreams;
    }
    public InputStream getImageInputStream(Card card){
    
        return getClass().getResourceAsStream("/pictures/" + card.toString() + ".png");
    
    }

   

   

   

   
}
