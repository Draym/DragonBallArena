package com.andres_k.components.graphicComponents.userInterface.overlay.windowOverlay;

import com.andres_k.components.gameComponents.animations.AnimatorOverlayData;
import com.andres_k.components.gameComponents.controllers.ScoreData;
import com.andres_k.components.graphicComponents.input.EnumInput;
import com.andres_k.components.graphicComponents.input.InputData;
import com.andres_k.components.graphicComponents.userInterface.elements.InterfaceElement;
import com.andres_k.components.graphicComponents.userInterface.elements.generic.GenericElement;
import com.andres_k.components.graphicComponents.userInterface.elements.table.TableMenuElement;
import com.andres_k.components.graphicComponents.userInterface.overlay.EnumOverlayElement;
import com.andres_k.components.graphicComponents.userInterface.overlay.Overlay;
import com.andres_k.components.graphicComponents.userInterface.tools.elements.*;
import com.andres_k.components.graphicComponents.userInterface.tools.items.ColorRect;
import com.andres_k.components.graphicComponents.userInterface.tools.items.StringTimer;
import com.andres_k.components.networkComponents.MessageModel;
import com.andres_k.components.soundComponents.MusicController;
import com.andres_k.components.soundComponents.SoundController;
import com.andres_k.components.taskComponent.EnumTargetTask;
import com.andres_k.components.taskComponent.EnumTask;
import com.andres_k.components.taskComponent.TaskFactory;
import com.andres_k.utils.configs.GlobalVariable;
import com.andres_k.utils.configs.WindowConfig;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.stockage.Tuple;
import com.andres_k.utils.tools.ColorTools;
import com.andres_k.utils.tools.ConsoleWrite;
import com.andres_k.utils.tools.StringTools;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * Created by andres_k on 20/06/2015.
 */
public class InterfaceOverlay extends Overlay {

    public InterfaceOverlay() throws JSONException {
        super();

        this.initElements();
        this.initPreference();
    }

    @Override
    public void initElements() {
        float menuX = (WindowConfig.w1_sX / 2) - 150;
        float menuY = (WindowConfig.w1_sY / 2) - 150;

        this.elements.put(EnumOverlayElement.TABLE_MENU_CONTROLS, new TableMenuElement(EnumOverlayElement.TABLE_MENU_CONTROLS, this.genericSendTask,
                new ColorRect(new Rectangle(menuX, menuY, 400, 320), ColorTools.get(ColorTools.Colors.TRANSPARENT_GREY))));
        this.elements.put(EnumOverlayElement.TABLE_MENU_SETTINGS, new GenericElement(EnumOverlayElement.TABLE_MENU_SETTINGS,
                new ColorRect(new Rectangle(menuX, menuY, 300, 310), ColorTools.get(ColorTools.Colors.TRANSPARENT_GREY)), new Pair<>(false, true), false, new boolean[]{true, true}));

        this.elements.put(EnumOverlayElement.HIGHSCORE, new GenericElement(EnumOverlayElement.HIGHSCORE,
                new ColorRect(new Rectangle(menuX - 20, menuY, 600, 400), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLACK)), new Pair<>(false, true), false, new boolean[]{true, true}));
        this.elements.put(EnumOverlayElement.TABLE_MENU_NEWGAME, new GenericElement(EnumOverlayElement.TABLE_MENU_NEWGAME, this.genericSendTask,
                new ColorRect(new Rectangle(menuX, menuY, 300, 150), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLACK)), new Pair<>(false, true), false, new boolean[]{true, true}));
        this.elements.put(EnumOverlayElement.TABLE_MENU_NEWGAME_LAUNCH, new GenericElement(EnumOverlayElement.TABLE_MENU_NEWGAME_LAUNCH, this.genericSendTask,
                new ColorRect(new Rectangle(menuX, menuY, 300, 180), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLACK)), new Pair<>(false, true), false, new boolean[]{true, true}));
        this.elements.put(EnumOverlayElement.TABLE_MENU, new GenericElement(EnumOverlayElement.TABLE_MENU, this.genericSendTask,
                new ColorRect(new Rectangle(menuX, menuY, 300, 240), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLACK)), new Pair<>(true, true), false, new boolean[]{true, true}));


        this.elements.put(EnumOverlayElement.TABLE_MENU_HOME, new GenericElement(EnumOverlayElement.TABLE_MENU_HOME, this.genericSendTask,
                new ColorRect(new Rectangle(50, 220, 350, 170), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLACK)), new Pair<>(false, false), true, new boolean[]{true, true}));
    }

    @Override
    public void initElementsComponent(AnimatorOverlayData animatorOverlayData) throws SlickException {
        this.animatorOverlayData = animatorOverlayData;

        this.initTableMenu();
        this.initTableMenuHome();
        this.initTableMenuControls();
        this.initTableMenuSettings();
    }

    @Override
    public void initElement(EnumOverlayElement element) throws SlickException {
        if (element == EnumOverlayElement.TABLE_MENU_CONTROLS) {
            this.elements.get(element).clearData();
            this.initTableMenuControls();
        } else if (element == EnumOverlayElement.TABLE_MENU_NEWGAME_LAUNCH) {
            this.elements.get(element).clearData();
            this.initTableMenuNewGameLaunch();
        } else if (element == EnumOverlayElement.TABLE_MENU_NEWGAME) {
            this.elements.get(element).clearData();
            this.initTableMenuNewGame();
        } else if (element == EnumOverlayElement.TABLE_MENU_SETTINGS) {
            this.elements.get(element).clearData();
            this.initTableMenuSettings();
        } else if (element == EnumOverlayElement.HIGHSCORE) {
            this.elements.get(element).clearData();
            this.initHighScore();
        }
    }

    @Override
    public void enter() throws SlickException {
        this.initElement(EnumOverlayElement.TABLE_MENU_CONTROLS);
        this.initElement(EnumOverlayElement.TABLE_MENU_SETTINGS);
        this.initElement(EnumOverlayElement.HIGHSCORE);
    }

    private void initTableMenuHome() throws SlickException {
        InterfaceElement table = this.elements.get(EnumOverlayElement.TABLE_MENU_HOME);

        table.doTask(new ButtonElement(new ImageElement(new ColorRect(new Rectangle(table.getBody().getMinX() + 20, table.getBody().getMinY() + 20, table.getBody().getSizeX() - 40, 60), ColorTools.get(ColorTools.Colors.TRANSPARENT_GREYBLACK)),
                this.animatorOverlayData.getAnimator(EnumOverlayElement.NEW_GAME), Element.PositionInBody.MIDDLE_MID), EnumOverlayElement.TABLE_MENU_NEWGAME));
        table.doTask(new ButtonElement(new ImageElement(new ColorRect(new Rectangle(table.getBody().getMinX() + 20, table.getBody().getMinY() + 90, table.getBody().getSizeX() - 40, 60), ColorTools.get(ColorTools.Colors.TRANSPARENT_GREYBLACK)),
                this.animatorOverlayData.getAnimator(EnumOverlayElement.HIGHSCORE), Element.PositionInBody.MIDDLE_MID), EnumOverlayElement.HIGHSCORE));
    }

    public void initHighScore() throws SlickException {
        InterfaceElement table = this.elements.get(EnumOverlayElement.HIGHSCORE);

        float posX = table.getBody().getMinX() + 20;
        float posY = table.getBody().getMinY() + 20;
        float sizeX = table.getBody().getSizeX() - 40;
        float sizeY = table.getBody().getSizeY() - 40;

        table.doTask(new ImageElement(new ColorRect(new Rectangle(posX, posY + 70, sizeX, sizeY - 70), ColorTools.get(ColorTools.Colors.TRANSPARENT_GREY)), EnumOverlayElement.BACKGROUND.getValue(), Element.PositionInBody.MIDDLE_MID));
        table.doTask(new ImageElement(new ColorRect(new Rectangle(posX, posY, sizeX, 70), ColorTools.get(ColorTools.Colors.TRANSPARENT_GREYBLACK)), this.animatorOverlayData.getAnimator(EnumOverlayElement.TOPSCORE), EnumOverlayElement.BACKGROUND.getValue(), Element.PositionInBody.MIDDLE_MID));
        posY += 90;
        List<Pair<String, String>> data = ScoreData.getDataScore();
        for (int i = 0; i < data.size() && i < 5; ++i) {
            table.doTask(new StringToImageElement(new ColorRect(new Rectangle(posX, posY, sizeX, 50)), this.animatorOverlayData.getAnimator(EnumOverlayElement.ALPHABET), data.get(i).getV1() + "  -  " + data.get(i).getV2(), EnumOverlayElement.SCORE.getValue() + String.valueOf(i), Element.PositionInBody.MIDDLE_MID));
            posY += 50;
        }
    }

    public void initTableMenuNewGame() throws SlickException {
        InterfaceElement tableMenuNew = this.elements.get(EnumOverlayElement.TABLE_MENU_NEWGAME);

        float posX = tableMenuNew.getBody().getMinX();
        float posY = tableMenuNew.getBody().getMinY();
        float sizeX = tableMenuNew.getBody().getSizeX();
        float sizeY = tableMenuNew.getBody().getSizeY();

        tableMenuNew.doTask(new ImageElement(new ColorRect(new Rectangle(posX + 15, posY + 15, sizeX - 30, sizeY - 30), ColorTools.get(ColorTools.Colors.TRANSPARENT_GREY)), EnumOverlayElement.BACKGROUND.getValue(), Element.PositionInBody.MIDDLE_MID));
        posY += (StringTools.charSizeY());
        tableMenuNew.doTask(new StringElement(new ColorRect(new Rectangle(posX + 20, posY + 10, (sizeX - 30) / 2, StringTools.charSizeY())), new StringTimer("Player Size"), Color.black, Element.PositionInBody.RIGHT_MID));
        tableMenuNew.doTask(new SelectionStringField(new ColorRect(new Rectangle(posX + 160, posY + 10, (sizeX - 30) / 4, StringTools.charSizeY()), ColorTools.get(ColorTools.Colors.TRANSPARENT_GREYBLACK)),
                new StringElement(new StringTimer(String.valueOf(GlobalVariable.currentPlayer)), Color.black, Element.PositionInBody.LEFT_MID), EnumOverlayElement.SELECT_FIELD.getValue() + "playerSize", true));

        posY += (StringTools.charSizeY());

        tableMenuNew.doTask(new ButtonElement(new ImageElement(new ColorRect(new Rectangle(posX + 20, posY + 40, tableMenuNew.getBody().getSizeX() - 40, 50)),
                this.animatorOverlayData.getAnimator(EnumOverlayElement.NEXT), Element.PositionInBody.MIDDLE_MID), EnumOverlayElement.NEXT, EnumOverlayElement.TABLE_MENU_NEWGAME_LAUNCH));
    }

    public void initTableMenuNewGameLaunch() throws SlickException {
        InterfaceElement tableMenuNew = this.elements.get(EnumOverlayElement.TABLE_MENU_NEWGAME_LAUNCH);

        float posX = tableMenuNew.getBody().getMinX();
        float posY = tableMenuNew.getBody().getMinY();
        float sizeX = tableMenuNew.getBody().getSizeX();
        float sizeY = tableMenuNew.getBody().getSizeY();

        tableMenuNew.doTask(new ImageElement(new ColorRect(new Rectangle(posX + 15, posY + 15, sizeX - 30, sizeY - 30), ColorTools.get(ColorTools.Colors.TRANSPARENT_GREY)), EnumOverlayElement.BACKGROUND.getValue(), Element.PositionInBody.MIDDLE_MID));
        posY += (StringTools.charSizeY());
        posY += 10;
        tableMenuNew.doTask(new StringElement(new ColorRect(new Rectangle(posX + 20, posY, (sizeX - 30) / 2, StringTools.charSizeY())), new StringTimer("Speed Game"), Color.black, Element.PositionInBody.RIGHT_MID));
        tableMenuNew.doTask(new SelectionStringField(new ColorRect(new Rectangle(posX + 160, posY, (sizeX - 30) / 2 - 10, StringTools.charSizeY()), ColorTools.get(ColorTools.Colors.TRANSPARENT_GREY)),
                new StringElement(new StringTimer(String.valueOf(GlobalVariable.currentSpeed)), Color.black, Element.PositionInBody.LEFT_MID), EnumOverlayElement.SELECT_FIELD.getValue() + EnumOverlayElement.NEW.getValue() + "speedGame", true));
        for (int i = 0; i < GlobalVariable.currentPlayer; ++i) {
            posY += (StringTools.charSizeY());
            tableMenuNew.doTask(new StringElement(new ColorRect(new Rectangle(posX + 20, posY, (sizeX - 30) / 2, StringTools.charSizeY())), new StringTimer("Pseudo P" + String.valueOf(i + 1)), Color.black, Element.PositionInBody.RIGHT_MID));
            tableMenuNew.doTask(new SelectionStringField(new ColorRect(new Rectangle(posX + 160, posY, (sizeX - 30) / 2 - 10, StringTools.charSizeY()), ColorTools.get(ColorTools.Colors.TRANSPARENT_GREY)),
                    new StringElement(new StringTimer("unknown"), Color.black, Element.PositionInBody.LEFT_MID), EnumOverlayElement.SELECT_FIELD.getValue() + EnumOverlayElement.NEW.getValue() + "pseudo" + String.valueOf(i), true));
        }
        tableMenuNew.doTask(new ButtonElement(new ImageElement(new ColorRect(new Rectangle(posX + (tableMenuNew.getBody().getSizeX() / 2) - 105, posY + 40, 220, 50)),
                this.animatorOverlayData.getAnimator(EnumOverlayElement.GO), Element.PositionInBody.MIDDLE_MID), EnumOverlayElement.GO, EnumOverlayElement.BUTTON));
    }

    private void initTableMenu() throws SlickException {
        InterfaceElement tableMenu = this.elements.get(EnumOverlayElement.TABLE_MENU);

        tableMenu.doTask(new ButtonElement(new ImageElement(new ColorRect(new Rectangle(tableMenu.getBody().getMinX() + 20, tableMenu.getBody().getMinY() + 20, tableMenu.getBody().getSizeX() - 40, 60), ColorTools.get(ColorTools.Colors.TRANSPARENT_GREY)),
                this.animatorOverlayData.getAnimator(EnumOverlayElement.CONTROLS), Element.PositionInBody.MIDDLE_MID), EnumOverlayElement.TABLE_MENU_CONTROLS));
        tableMenu.doTask(new ButtonElement(new ImageElement(new ColorRect(new Rectangle(tableMenu.getBody().getMinX() + 20, tableMenu.getBody().getMinY() + 90, tableMenu.getBody().getSizeX() - 40, 60), ColorTools.get(ColorTools.Colors.TRANSPARENT_GREY)),
                this.animatorOverlayData.getAnimator(EnumOverlayElement.SETTINGS), Element.PositionInBody.MIDDLE_MID), EnumOverlayElement.TABLE_MENU_SETTINGS));
        tableMenu.doTask(new ButtonElement(new ImageElement(new ColorRect(new Rectangle(tableMenu.getBody().getMinX() + 20, tableMenu.getBody().getMinY() + 160, tableMenu.getBody().getSizeX() - 40, 60), ColorTools.get(ColorTools.Colors.TRANSPARENT_GREY)),
                this.animatorOverlayData.getAnimator(EnumOverlayElement.EXIT), Element.PositionInBody.MIDDLE_MID), EnumOverlayElement.EXIT));
    }

    private void initTableMenuControls() throws SlickException {
        InterfaceElement tableMenuControls = this.elements.get(EnumOverlayElement.TABLE_MENU_CONTROLS);

        tableMenuControls.doTask(new ButtonElement(new StringElement(new StringTimer("Controls"), Color.black,
                EnumOverlayElement.CONTROLS.getValue() + ":" + EnumOverlayElement.CONTROLS.getValue(), Element.PositionInBody.MIDDLE_MID), EnumOverlayElement.BUTTON));

        for (Map.Entry<EnumInput, String> entry : InputData.getAvailableInput().entrySet()) {
            tableMenuControls.doTask(new ButtonElement(new StringElement(new StringTimer(entry.getKey().getValue() + ":" +
                    StringTools.duplicateString(" ", 14 - entry.getKey().getValue().length()) + entry.getValue() +
                    StringTools.duplicateString(" ", 18 - entry.getValue().length())), Color.black,
                    EnumOverlayElement.CONTROLS.getValue() + ":" + entry.getKey().getValue(), Element.PositionInBody.MIDDLE_MID), EnumOverlayElement.CONTROLS));
        }
    }

    private void initTableMenuSettings() throws SlickException {
        InterfaceElement tableMenuSettings = this.elements.get(EnumOverlayElement.TABLE_MENU_SETTINGS);
        float posX = tableMenuSettings.getBody().getMinX();
        float posY = tableMenuSettings.getBody().getMinY();
        float sizeX = tableMenuSettings.getBody().getSizeX();

        tableMenuSettings.doTask(new StringElement(new ColorRect(new Rectangle(posX, posY, sizeX, StringTools.charSizeY())), new StringTimer("Settings"), Color.black, Element.PositionInBody.MIDDLE_MID));

        posY += (StringTools.charSizeY() * 2);
        tableMenuSettings.doTask(new StringElement(new ColorRect(new Rectangle(posX, posY, tableMenuSettings.getBody().getSizeX() / 2, StringTools.charSizeY())), new StringTimer("Sounds"), Color.black, Element.PositionInBody.MIDDLE_MID));
        posY += (StringTools.charSizeY() * 2);
        tableMenuSettings.doTask(new StringElement(new ColorRect(new Rectangle(posX, posY, (int) (tableMenuSettings.getBody().getSizeX() / 1.1), StringTools.charSizeY())), new StringTimer(String.valueOf((int) (SoundController.getVolume() * 100))), Color.black, EnumOverlayElement.SOUNDS_VALUE.getValue(), Element.PositionInBody.RIGHT_MID));
        tableMenuSettings.doTask(new ImageElement(new ColorRect(new Rectangle(posX + 10, posY + 4, 202, 12), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLACK)), EnumOverlayElement.SOUNDS_GRAPH.getValue() + EnumOverlayElement.BORDER.getValue(), Element.PositionInBody.LEFT_MID));
        tableMenuSettings.doTask(new ImageElement(new ColorRect(new Rectangle(posX + 11, posY + 5, 200, 10), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLUE)), EnumOverlayElement.SOUNDS_GRAPH.getValue(), Element.PositionInBody.LEFT_MID));

        posY += 50;
        tableMenuSettings.doTask(new StringElement(new ColorRect(new Rectangle(posX, posY, tableMenuSettings.getBody().getSizeX() / 2, StringTools.charSizeY())), new StringTimer("Musics"), Color.black, Element.PositionInBody.MIDDLE_MID));
        posY += (StringTools.charSizeY() * 2);
        tableMenuSettings.doTask(new StringElement(new ColorRect(new Rectangle(posX, posY, (int) (tableMenuSettings.getBody().getSizeX() / 1.1), StringTools.charSizeY())), new StringTimer(String.valueOf((int) (MusicController.getVolume() * 100))), Color.black, EnumOverlayElement.MUSICS_VALUE.getValue(), Element.PositionInBody.RIGHT_MID));
        tableMenuSettings.doTask(new ImageElement(new ColorRect(new Rectangle(posX + 10, posY + 4, 202, 12), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLACK)), EnumOverlayElement.MUSICS_GRAPH.getValue() + EnumOverlayElement.BORDER.getValue(), Element.PositionInBody.LEFT_MID));
        tableMenuSettings.doTask(new ImageElement(new ColorRect(new Rectangle(posX + 11, posY + 5, 200, 10), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLUE)), EnumOverlayElement.MUSICS_GRAPH.getValue(), Element.PositionInBody.LEFT_MID));

        tableMenuSettings.doTask(new Pair<>(EnumOverlayElement.SOUNDS_GRAPH.getValue(), new Tuple<>(EnumTask.CUT, "body", SoundController.getVolume() / SoundController.getMaxVolume())));
        tableMenuSettings.doTask(new Pair<>(EnumOverlayElement.MUSICS_GRAPH.getValue(), new Tuple<>(EnumTask.CUT, "body", MusicController.getVolume() / MusicController.getMaxVolume())));
    }

    // TASK
    @Override
    public void update(Observable o, Object arg) {
        try {
            if (arg instanceof Tuple) {
                Tuple<EnumTargetTask, EnumTargetTask, Object> received = (Tuple<EnumTargetTask, EnumTargetTask, Object>) arg;

                if (received.getV1().equals(EnumTargetTask.WINDOWS) && received.getV2().isIn(EnumTargetTask.INTERFACE_OVERLAY)) {

                    ConsoleWrite.debug("\nOVERLAY RECEIVED tuple: " + arg);
                    if (received.getV3() instanceof Pair && ((Pair) received.getV3()).getV1() instanceof EnumOverlayElement) {
                        Pair<EnumOverlayElement, Object> task = (Pair<EnumOverlayElement, Object>) received.getV3();

                        List<EnumOverlayElement> targets = new ArrayList<>();
                        targets.addAll(EnumOverlayElement.getChildren(task.getV1()));
                        for (EnumOverlayElement target : targets) {
                            ConsoleWrite.debug("CHIDL: " + targets.size() + " -> send to " + target);
                            if (this.elements.containsKey(target)) {
                                ConsoleWrite.debug("send " + task.getV2() + " to " + target);
                                this.elements.get(target).doTask(task.getV2());
                            }
                        }
                    } else {
                        ConsoleWrite.debug("\n*************\nWARNING!\nyou shouldn't call this method like this : " + received.getV3());
                        for (Map.Entry<EnumOverlayElement, InterfaceElement> entry : this.elements.entrySet()) {
                            entry.getValue().doTask(received.getV3());
                        }
                    }
                }
            } else if (arg instanceof Pair) {
                ConsoleWrite.debug("OVERLAY RECEIVED pair: " + arg);
                if (((Pair) arg).getV2() instanceof MessageModel) {
                    this.setChanged();
                    this.notifyObservers(TaskFactory.createTask(EnumTargetTask.INTERFACE_OVERLAY, EnumTargetTask.INTERFACE, ((Pair) arg).getV2()));
                } else if (((Pair) arg).getV1() instanceof EnumOverlayElement && ((Pair) arg).getV2() instanceof EnumOverlayElement) {
                    Pair<EnumOverlayElement, EnumOverlayElement> received = (Pair<EnumOverlayElement, EnumOverlayElement>) arg;

                    if ((received.getV2() == EnumOverlayElement.EXIT || received.getV2() == EnumOverlayElement.GO) && this.elements.containsKey(received.getV1())) {
                        this.elements.get(received.getV1()).stop();
                        this.setChanged();
                        this.notifyObservers(TaskFactory.createTask(EnumTargetTask.INTERFACE_OVERLAY, EnumTargetTask.INTERFACE, received.getV2()));
                    } else if (this.elements.containsKey(received.getV1()) && this.elements.containsKey(received.getV2())) {
                        this.elements.get(received.getV1()).stop();
                        this.initElement(received.getV2());
                        this.elements.get(received.getV2()).start();
                    }
                } else if (((Pair) arg).getV2() instanceof Pair) {
                    Pair<EnumOverlayElement, Pair> received = (Pair<EnumOverlayElement, Pair>) arg;
                    if (this.elements.containsKey(received.getV1())) {
                        this.elements.get(received.getV1()).doTask(received.getV2());
                        this.overlayConfigs.setAvailableInput(received.getV1(), this.elements.get(received.getV1()).getReachable());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // FUNCTIONS

    public void doTask(Object task) throws SlickException {
        if (task instanceof Integer) {
            if ((Integer) task == EnumInput.OVERLAY_1.getIndex() || (Integer) task == EnumInput.OVERLAY_2.getIndex()) {
                String value = EnumInput.getEnumByIndex((Integer) task).getValue();
                this.current = Integer.valueOf(value.substring(value.indexOf("_") + 1)) - 1;
                this.current = (this.current < 0 ? 0 : this.current);
                this.current = (this.current > 1 ? 1 : this.current);
                this.elements.get(EnumOverlayElement.TABLE_MENU_SCREEN).doTask(this.current);
            }
        }
    }

    public boolean event(int key, char c, EnumInput type) {
//        Debug.debug("\n NEW EVENT: " + Input.getKeyName(key) + " (" + type + ")");
        try {
            for (Map.Entry<EnumOverlayElement, InterfaceElement> entry : this.elements.entrySet()) {
                boolean[] reachable = entry.getValue().getReachable();
                if (reachable[this.current]) {
                    Object result = null;
                    if (type == EnumInput.PRESSED) {
                        result = entry.getValue().eventPressed(key, c);
                    } else if (type == EnumInput.RELEASED) {
                        result = entry.getValue().eventReleased(key, c);
                    }
                    // TODO: A ENVOYER AUX AUTRE JOUEURS
                    if (result instanceof MessageModel) {
                        this.setChanged();
                        this.notifyObservers(TaskFactory.createTask(EnumTargetTask.INTERFACE_OVERLAY, EnumTargetTask.INTERFACE, result));
                        return true;
                    } else if (result instanceof Boolean) {
                        return (Boolean) result;
                    } else if (result instanceof Pair) {
                        Pair<Object, Object> task = (Pair<Object, Object>) result;

                        if (task.getV1() instanceof EnumInput && task.getV2() instanceof String) {
                            if (InputData.setAvailableInput((EnumInput) task.getV1(), (String) task.getV2())) {
                                this.elements.get(EnumOverlayElement.TABLE_MENU_CONTROLS).doTask(task.getV2());
                            }
                        }
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
