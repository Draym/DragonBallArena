package com.andres_k.components.graphicComponents.userInterface.items.elements;

import com.andres_k.components.gameComponents.animations.container.AnimatorController;
import com.andres_k.components.graphicComponents.userInterface.overlay.EnumOverlayElement;
import com.andres_k.components.graphicComponents.userInterface.items.tools.ColorRect;
import com.andres_k.components.taskComponent.EnumTask;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.stockage.Tuple;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 27/06/2015.
 */
public class ImageElement extends Element {
    private AnimatorController animatorController;
    private float sizeXMAX;

    public ImageElement(ColorRect body, AnimatorController animatorController, PositionInBody position) {
        this.init(body, "", position, EnumOverlayElement.IMAGE);
        this.animatorController = animatorController;
        this.sizeXMAX = body.getSizeX();
    }

    public ImageElement(ColorRect body, AnimatorController animatorController, String id, PositionInBody position) {
        this.init(body, id, position, EnumOverlayElement.IMAGE);
        this.animatorController = animatorController;
        this.sizeXMAX = body.getSizeX();
    }

    public ImageElement(ColorRect body, String id, PositionInBody position) {
        this.init(body, id, position, EnumOverlayElement.IMAGE);
        this.animatorController = null;
        this.sizeXMAX = body.getSizeX();
    }

    public ImageElement(AnimatorController animatorController, String id, PositionInBody position) {
        this.init(null, id, position, EnumOverlayElement.IMAGE);
        this.animatorController = animatorController;
        this.sizeXMAX = 0;
    }


    @Override
    public void leave() {
    }

    public void draw(Graphics g) {
        if (this.body != null && this.body.getMinX() != -1) {
            this.body.draw(g);

            if (this.animatorController != null && this.animatorController.isPrintable()) {
                Pair<Float, Float> position = this.getChoicePosition(this.body);
                g.drawAnimation(this.animatorController.currentAnimation(), position.getV1(), position.getV2());
            }
        }
    }

    @Override
    public void draw(Graphics g, ColorRect body) {
        if (body.getMinX() != -1) {
            if (this.body != null && body.getColor() == null) {
                body.setColor(this.body.getColor());
            }
            body.draw(g);
            if (this.animatorController != null && this.animatorController.isPrintable()) {
                Pair<Float, Float> position = this.getChoicePosition(body);
                g.drawAnimation(this.animatorController.currentAnimation(), position.getV1(), position.getV2());
            }
        }
    }

    private Pair<Float, Float> getChoicePosition(ColorRect body) {
        float x = body.getMinX();
        float y = body.getMinY();

        if (this.position == PositionInBody.MIDDLE_MID) {
            float sizeX = (body.getSizeX() / 2) - (this.animatorController.currentAnimation().getWidth() / 2);
            float sizeY = (body.getSizeY() / 2) - (this.animatorController.currentAnimation().getHeight() / 2);

            sizeX = (sizeX < 0 ? 0 : sizeX);
            sizeY = (sizeY < 0 ? 0 : sizeY);
            x += sizeX;
            y += sizeY;
        } else if (this.position == PositionInBody.RIGHT_MID) {
            float sizeX = (body.getSizeX() - this.animatorController.currentAnimation().getWidth());
            float sizeY = (body.getSizeY() / 2) - (this.animatorController.currentAnimation().getHeight() / 2);

            sizeX = (sizeX < 0 ? 0 : sizeX);
            sizeY = (sizeY < 0 ? 0 : sizeY);
            x += sizeX;
            y += sizeY;
        } else if (this.position == PositionInBody.MIDDLE_DOWN) {
            float sizeX = (body.getSizeX() / 2) - (this.animatorController.currentAnimation().getWidth() / 2);
            float sizeY = (body.getSizeY() - this.animatorController.currentAnimation().getHeight());

            sizeX = (sizeX < 0 ? 0 : sizeX);
            sizeY = (sizeY < 0 ? 0 : sizeY);
            x += sizeX;
            y += sizeY;
        } else if (this.position == PositionInBody.RIGHT_DOWN) {
            float sizeX = (body.getSizeX() - this.animatorController.currentAnimation().getWidth());
            float sizeY = (body.getSizeY() - this.animatorController.currentAnimation().getHeight());

            sizeX = (sizeX < 0 ? 0 : sizeX);
            sizeY = (sizeY < 0 ? 0 : sizeY);
            x += sizeX;
            y += sizeY;
        } else if (this.position == PositionInBody.LEFT_DOWN) {
            float sizeY = (body.getSizeY() - this.animatorController.currentAnimation().getHeight());

            sizeY = (sizeY < 0 ? 0 : sizeY);
            y += sizeY;
        } else if (this.position == PositionInBody.MIDDLE_UP) {
            float sizeX = (body.getSizeX() / 2) - (this.animatorController.currentAnimation().getWidth() / 2);

            sizeX = (sizeX < 0 ? 0 : sizeX);
            x += sizeX;
        } else if (this.position == PositionInBody.RIGHT_UP) {
            float sizeX = (body.getSizeX() - this.animatorController.currentAnimation().getWidth());

            sizeX = (sizeX < 0 ? 0 : sizeX);
            x += sizeX;
        }

        return new Pair<>(x, y);
    }

    @Override
    public void update() {
        if (this.animatorController != null) {
            if (this.animatorController.needUpdate() && this.animatorController.isActivated()) {
                this.animatorController.updateAnimator(true, true);
            }
        }
    }

    @Override
    public boolean replace(Element element) throws SlickException {
        if (element.getType() == EnumOverlayElement.IMAGE) {
            this.animatorController = new AnimatorController(((ImageElement) element).animatorController);
            return true;
        }
        return false;
    }

    @Override
    public Object doTask(Object task) {

        if (task instanceof EnumTask) {
            if (task == EnumTask.START) {
                this.start();
            } else if (task == EnumTask.STOP) {
                this.stop();
            }
        } else if (task instanceof Long) {
            this.animatorController.updateAnimator(false, false);
            this.animatorController.startTimer((Long) task);

        } else if (task instanceof Tuple && ((Tuple) task).getV1() instanceof EnumTask) {
            EnumTask order = (EnumTask) ((Tuple) task).getV1();
            Object target = ((Tuple) task).getV2();
            Object value = ((Tuple) task).getV3();

            if (order == EnumTask.SETTER) {
                if (target.equals("index") && value instanceof Integer && this.animatorController != null) {
                    this.animatorController.setIndex((Integer) value);
                }
            } else if (order == EnumTask.CUT) {
                if (target.equals("body") && value instanceof Float) {
                    float percent = (float) value;
                    if (percent >= 1) {
                        this.body.setPrintable(true);
                        this.body.setSizes(this.sizeXMAX, this.body.getSizeY());
                    } else if (percent > 0) {
                        if (!this.id.contains(EnumOverlayElement.BORDER.getValue())) {
                            this.body.setPrintable(true);
                            this.body.setSizes(this.sizeXMAX * percent, this.body.getSizeY());
                        }
                    } else {
                        this.body.setPrintable(false);
                        this.body.setSizes(0, this.body.getSizeY());
                    }
                }
            } else if (order == EnumTask.EVENT) {
                if (target instanceof Integer && (int) target <= Input.KEY_NUMPAD9 && (int) target >= Input.KEY_NUMPAD0) {
                    this.animatorController.setIndex((Integer) value);
                }
            }

        }
        return null;
    }

    @Override
    public String toString() {
        return "imageType: " + (this.animatorController != null ? this.animatorController.currentAnimationType() : "");
    }

    private void start() {
        this.animatorController.restart();
    }

    private void stop() {
        this.animatorController.restart();
    }

    //GETTERS
    @Override
    public boolean isActivated() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        if (this.animatorController == null || this.animatorController.isDeleted()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isNull() {
        return (this.animatorController == null);
    }

    @Override
    public float getAbsoluteWidth() {
        return this.animatorController.currentAnimation().getWidth();
    }

    @Override
    public float getAbsoluteHeight() {
        return this.animatorController.currentAnimation().getHeight();
    }

    // SETTERS
    public void setBody(ColorRect body) {
        if (this.body != null) {
            if (body.getColor() == null) {
                body.setColor(this.body.getColor());
            }
        }
        this.body = body;
        this.sizeXMAX = this.body.getSizeX();
    }
}
