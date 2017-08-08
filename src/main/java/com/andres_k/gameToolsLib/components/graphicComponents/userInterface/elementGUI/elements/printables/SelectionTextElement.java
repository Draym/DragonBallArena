package com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.elements.printables;

import com.andres_k.custom.component.eventComponents.EInput;
import com.andres_k.gameToolsLib.components.eventComponents.input.InputEvent;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.EStatus;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.tools.StringTimer;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorRect;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorShape;
import com.andres_k.gameToolsLib.components.resourceComponents.fonts.EFont;
import com.andres_k.custom.component.taskComponents.ELocation;
import com.andres_k.custom.component.taskComponents.ETaskType;
import com.andres_k.gameToolsLib.utils.stockage.Pair;
import com.andres_k.gameToolsLib.utils.stockage.Tuple;
import com.andres_k.gameToolsLib.utils.tools.ColorTools;
import org.newdawn.slick.Color;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by com.andres_k on 18/03/2016.
 */
public class SelectionTextElement extends TextElement {

    public SelectionTextElement(StringTimer textTimer, Color textColor, boolean activated) {
        this(ELocation.UNKNOWN.getId(), textTimer, textColor, activated);
    }

    public SelectionTextElement(String id, StringTimer textTimer, Color textColor, boolean activated) {
        this(id, textTimer, textColor, EFont.BASIC, 16, activated);
    }

    public SelectionTextElement(StringTimer textTimer, Color textColor, EFont font, float size, boolean activated) {
        this(ELocation.UNKNOWN.getId(), textTimer, textColor, font, size, null, PositionInBody.LEFT_UP, activated);
    }

    public SelectionTextElement(String id, StringTimer textTimer, Color textColor, EFont font, float size, boolean activated) {
        this(id, textTimer, textColor, font, size, null, PositionInBody.LEFT_UP, activated);
    }

    public SelectionTextElement(StringTimer textTimer, Color textColor, EFont font, float size, float decalX, float decalY, PositionInBody position, boolean activated) {
        this(ELocation.UNKNOWN.getId(), textTimer, textColor, font, size, decalX, decalY, position, activated);
    }

    public SelectionTextElement(String id, StringTimer textTimer, Color textColor, EFont font, float size, float decalX, float decalY, PositionInBody position, boolean activated) {
        this(id, textTimer, textColor, font, size, new ColorRect(new Rectangle(decalX, decalY, textTimer.getAbsoluteWidth(size), textTimer.getAbsoluteHeight(size))), position, activated);
    }

    public SelectionTextElement(String id, StringTimer textTimer, Color textColor, EFont font, float size, ColorShape body, PositionInBody position, boolean activated) {
        super(id, textTimer, textColor, font, size, body, position, activated);
        this.addTask(new Pair<>(EStatus.ON_CLICK, new Tuple<>(ETaskType.SETTER, "color", ColorTools.Colors.TRANSPARENT_YELLOW)));
        this.addTask(new Pair<>(EStatus.OFF_CLICK, new Tuple<>(ETaskType.SETTER, "color", ColorTools.Colors.NONE)));
        this.addTask(new Pair<>(EStatus.ON_KILL, new Tuple<>(ETaskType.SETTER, "color", ColorTools.Colors.NONE)));
    }

    @Override
    public boolean event(InputEvent input) {
        boolean result = false;
        if (this.isActivated() && this.isClicked() && input.type == EInput.KEY_RELEASED) {
            if (input.key == Input.KEY_BACK) {
                this.textTimer.delete(this.textTimer.getValue().length() - 1, 1);
                result = true;
            } else if (input.value >= 32 && input.value <= 126) {
                this.textTimer.add(this.textTimer.getValue().length(), String.valueOf(input.value));
                result = true;
            }
        }
        return result;
    }

}
