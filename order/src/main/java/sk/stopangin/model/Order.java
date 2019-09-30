package sk.stopangin.model;

import lombok.Data;

@Data
public class Order {

    private String id;
    private String cardId;
    private String item;
    private int pcs;
    private int price;
    private State state;

    public enum State{
        DRAFT,CANCELED, PROCESSED
    }


}
