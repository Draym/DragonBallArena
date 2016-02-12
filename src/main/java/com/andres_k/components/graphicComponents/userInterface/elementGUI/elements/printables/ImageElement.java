package com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiType;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.Element;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorRect;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorShape;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.stockage.Tuple;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by andres_k on 04/02/2016.
 */
public class ImageElement extends Element {
    private AnimatorController animatorController;
    private float sizeXMAX;

    public ImageElement(AnimatorController animatorController, boolean activated) {
        this(ELocation.UNKNOWN.getId(), null, animatorController, PositionInBody.LEFT_UP, activated);
    }

    public ImageElement(String id, AnimatorController animatorController, boolean activated) {
        this(id, null, animatorController, PositionInBody.LEFT_UP, activated);
    }

    public ImageElement(ColorShape body, boolean activated) {
        this(ELocation.UNKNOWN.getId(), body, null, PositionInBody.LEFT_UP, activated);
    }

    public ImageElement(String id, ColorShape body, boolean activated) {
        this(id, body, null, PositionInBody.LEFT_UP, activated);
    }

    public ImageElement(ColorShape body, AnimatorController animatorController, boolean activated) {
        this(ELocation.UNKNOWN.getId(), body, animatorController, PositionInBody.LEFT_UP, activated);
    }

    public ImageElement(String id, ColorShape body, AnimatorController animatorController, PositionInBody position, boolean activated) {
        super(EGuiType.IMAGE, id, body, position, activated);
        this.animatorController = animatorController;
        if (this.animatorController != null) {
            if (this.body == null) {
                this.body = new ColorRect(new Rectangle(0, 0, this.animatorController.currentSizeAnimation().getV1(), this.animatorController.currentSizeAnimation().getV2()));
            } else if (this.body.getSizeX() <= 0 && this.body.getSizeY() <= 0) {
                this.body.setSizes(this.animatorController.currentSizeAnimation().getV1(), this.animatorController.currentSizeAnimation().getV2());
            }
        }
        if (this.body != null) {
            this.sizeXMAX = this.body.getSizeX();
        }
    }

    @Override
    public void init() {
    }

    @Override
    public void enter() {
    }

    @Override
    public void leave() {
    }

    @Override
    public void update() {
        if (this.animatorController != null) {
            if (this.animatorController.needUpdate() && this.animatorController.isActivated()) {
                this.animatorController.updateAnimator(true, true);
            }
            this.animatorController.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        if (this.activated) {
            this.draw(g, 0, 0);
        }
    }

    @Override
    public void draw(Graphics g, float decalX, float decalY) {
        if (this.activated) {
            this.body.cloneAndDecalFrom(decalX, decalY).draw(g);

            if (this.animatorController != null && this.animatorController.isPrintable()) {
                Pair<Float, Float> position = this.getChoicePosition(this.body);
                this.drawCurrentImage(g, position.getV1() + decalX, position.getV2() + decalY);
            }
        }
    }

    @Override
    public void draw(Graphics g, ColorShape container) {
        if (this.activated) {
            ColorShape body = container.cloneIt();
            if (body.getColor() == null) {
                body.setColor(this.body.getColor());
            }
            if (this.body != null) {
                body.setPosition(body.getMinX() + this.body.getMinX(), body.getMinY() + this.body.getMinY());
            }
            body.draw(g);
            if (this.animatorController != null && this.animatorController.isPrintable()) {
                Pair<Float, Float> position = this.getChoicePosition(body);
                this.drawCurrentImage(g, position.getV1(), position.getV2());
            }
        }
    }

    private void drawCurrentImage(Graphics g, float x, float y) {
        int sizeX = (this.body.getSizeX() > this.animatorController.currentAnimation().getCurrentFrame().getWidth() ? this.animatorController.currentAnimation().getCurrentFrame().getWidth() : (int) this.body.getSizeX());
        int sizeY = (this.body.getSizeY() > this.animatorController.currentAnimation().getCurrentFrame().getHeight() ? this.animatorController.currentAnimation().getCurrentFrame().getHeight() : (int) this.body.getSizeY());
        this.animatorController.drawImage(g, this.animatorController.currentAnimation().getCurrentFrame().getSubImage(0, 0, sizeX, sizeY), x, y);
    }

    @Override
    public boolean replace(Element element) throws SlickException {
        if (element.getType() == EGuiType.IMAGE) {
            this.animatorController = new AnimatorController(((ImageElement) element).animatorController);
            return true;
        }
        return false;
    }

    @Override
    public Object doTask(Object task) {
        Object result;

        if ((result = super.doTask(task)) != null) {
            return result;
        }
       if (task instanceof Pair && ((Pair) task).getV1() instanceof ETaskType) {
            if (((Pair) task).getV1() == ETaskType.START_TIMER) {
                this.animatorController.updateAnimator(false, false);
                this.animatorController.startTimer((Long) ((Pair) task).getV2());
            }

        } else if (task instanceof Tuple && ((Tuple) task).getV1() instanceof ETaskType) {
            ETaskType order = (ETaskType) ((Tuple) task).getV1();
            Object target = ((Tuple) task).getV2();
            Object value = ((Tuple) task).getV3();

            if (order == ETaskType.SETTER) {
                if (target.equals("index") && value instanceof Integer && this.animatorController != null) {
                    this.animatorController.setCurrentAnimationIndex((Integer) value);
                }
            } else if (order == ETaskType.CUT) {
                if (target.equals("body") && value instanceof Float) {
                    float percent = (float) value;
                    if (percent >= 1) {
                        this.body.setPrintable(true);
                        this.body.setSizes(this.sizeXMAX, this.body.getSizeY());
                    } else if (percent > 0) {
                        if (!this.id.contains(EGuiType.BORDER.getValue())) {
                            this.body.setPrintable(true);
                            this.body.setSizes(this.sizeXMAX * percent, this.body.getSizeY());
                        }
                    } else {
                        this.body.setPrintable(false);
                        this.body.setSizes(0, this.body.getSizeY());
                    }
                }
            } else if (order == ETaskType.EVENT) {
                if (target instanceof Integer && (int) target <= Input.KEY_NUMPAD9 && (int) target >= Input.KEY_NUMPAD0) {
                    this.animatorController.setCurrentAnimationIndex((Integer) value);
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
    public boolean isOnFocus(float x, float y) {
        super.isOnFocus(x, y);

        if (this.animatorController != null) {
            if (this.focused) {
                if (this.animatorController.currentAnimationType() != EAnimation.ON_FOCUS) {
                    this.animatorController.changeAnimation(EAnimation.ON_FOCUS);
                }
            } else if (this.animatorController.currentAnimationType() != EAnimation.IDLE) {
                this.animatorController.changeAnimation(EAnimation.IDLE);
            }
        }
        return this.focused;
    }

    public boolean isEmpty() {
        return this.animatorController == null || this.animatorController.isDeleted();
    }

    @Override
    public boolean isNull() {
        return (this.animatorController == null);
    }

    @Override
    public float getAbsoluteWidth() {
        if (this.animatorController != null && this.animatorController.currentAnimation() != null) {
            return this.animatorController.currentAnimation().getWidth();
        }
        return super.getAbsoluteWidth();
    }

    @Override
    public float getAbsoluteHeight() {
        if (this.animatorController != null && this.animatorController.currentAnimation() != null) {
            return this.animatorController.currentAnimation().getHeight();
        }
        return super.getAbsoluteHeight();
    }

    // SETTERS
    public void setBody(ColorShape body) {
        if (this.body != null) {
            if (body.getColor() == null) {
                body.setColor(this.body.getColor());
            }
        }
        this.body = body;
        this.sizeXMAX = this.body.getSizeX();
    }
}
