package com.andres_k.components.networkComponents.messages;

import com.andres_k.components.graphicComponents.userInterface.overlay.EnumOverlayElement;
import com.andres_k.components.networkComponents.MessageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres_k on 10/07/2015.
 */
public class MessageGameNew extends MessageModel {
    EnumOverlayElement type;
    EnumOverlayElement target;
    List<String> values;

    public MessageGameNew(String pseudo, String id, EnumOverlayElement type, EnumOverlayElement target){
        super(pseudo, id);

        this.type = type;
        this.target = target;
        this.values = new ArrayList<>();
    }

    public void addValue(String object){
        this.values.add(object);
    }

    // GETTERS
    public EnumOverlayElement getType(){
        return this.type;
    }

    public List<String> getValues(){
        return this.values;
    }
}
