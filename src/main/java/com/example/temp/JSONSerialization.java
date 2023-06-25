package com.example.temp;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.Collection;

public class JSONSerialization<T> {
    private Collection<T> collection;

    /**
     * Information about serialization of game state to json format
     *
     * @param board
     */
    public void board2JSON(Board board) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            objectMapper.writeValue(new File("simple.json"), board);
            File file = new File("simple.json");

            objectMapper.writeValue(new File("simple2.json"), board.getArray());
            File file2 = new File("simple2.json");

//                for(int i = 0; i < board.getHeight(); i++){
//                    for(int j = 0; j < board.getWidth(); j++){
//                        xml.append(xmlMapper.writeValueAsString(board.getBoard(i, j)));
//                    }
//                }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Deserialization info about saved game from the files, read info about board
     *
     * @param filename
     * @return board or null
     */
    public Board JSONFileDeserialization(String filename) {
        if (filename != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();

                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                // read elementary info about board like height, width etc.
                Board board = objectMapper.readValue(new File("simple.json"), Board.class);

                // read info about Field in the board
                Field[][] tempBoard = objectMapper.readValue(new File("simple2.json"), Field[][].class);

                board.setBoardField(tempBoard);

                System.out.println(board.getHeight());
                System.out.println(board.getBoard(0,0).isClicked());

                return board;
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return null;
    }

    public Collection<T> getCollection() {
        return collection;
    }

    public void setCollection(Collection<T> collection) {
        this.collection = collection;
    }

}
