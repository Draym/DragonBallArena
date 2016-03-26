package com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiType;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EStatus;
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
    private float sizeYMAX;
    private boolean flip;
    private Pair<EAnimation, Integer> saveAnimation;

    public ImageElement(AnimatorController animatorController, boolean activated) {
        this(ELocation.UNKNOWN.getId(), null, animatorController, PositionInBody.LEFT_UP, false, activated);
    }

    public ImageElement(String id, AnimatorController animatorController, boolean activated) {
        this(id, null, animatorController, PositionInBody.LEFT_UP, false, activated);
    }

    public ImageElement(ColorShape body, boolean activated) {
        this(ELocation.UNKNOWN.getId(), body, null, PositionInBody.LEFT_UP, false, activated);
    }

    public ImageElement(String id, ColorShape body, boolean activated) {
        this(id, body, null, PositionInBody.LEFT_UP, false, activated);
    }

    public ImageElement(ColorShape body, AnimatorController animatorController, boolean activated) {
        this(ELocation.UNKNOWN.getId(), body, animatorController, PositionInBody.LEFT_UP, false, activated);
    }

    public ImageElement(String id, ColorShape body, AnimatorController animatorController, boolean flip, boolean activated) {
        this(id, body, animatorController, PositionInBody.LEFT_UP, flip, activated);
    }

    public ImageElement(String id, ColorShape body, AnimatorController animatorController, PositionInBody position, boolean flip, boolean activated) {
        super(EGuiType.IMAGE, id, body, position, activated);
        this.animatorController = animatorController;
        this.flip = flip;
        if (this.animatorController != null) {
            this.animatorController.updateAnimator(activated, activated);
            if (this.body == null) {
                this.body = new ColorRect(new Rectangle(0, 0, this.animatorController.currentSizeAnimation().getV1(), this.animatorController.currentSizeAnimation().getV2()));
            } else if (this.body.getSizeX() <= 0 && this.body.getSizeY() <= 0) {
                this.body.setSizes(this.animatorController.currentSizeAnimation().getV1(), this.animatorController.currentSizeAnimation().getV2());
            }
            this.saveAnimation = new Pair<>(this.animatorController.currentAnimationType(), this.animatorController.getIndex());
        }
        this.sizeXMAX = 0;
        this.sizeYMAX = 0;
        if (this.body != null) {
            this.sizeXMAX = this.body.getSizeX();
            this.sizeYMAX = this.body.getSizeY();
        }
        this.tasks.add(new Pair<>(EStatus.ON_CLICK, new Tuple<>(ETaskType.SETTER, "animation", EAnimation.ON_CLICK)));
        this.tasks.add(new Pair<>(EStatus.OFF_CLICK, new Tuple<>(ETaskType.SETTER, "animation", EAnimation.IDLE)));
    }

    @Override
    public void init() {
        this.reset();
    }

    @Override
    public void enter() {
        this.reset();
    }

    @Override
    public void leave() {
    }

    @Override
    public void reset() {
        super.reset();
        if (this.animatorController != null) {
            try {
                this.animatorController.changeAnimation(this.saveAnimation.getV1(), this.saveAnimation.getV2());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deactivate() {
        super.deactivate();
        if (this.animatorController != null) {
            try {
                this.animatorController.changeAnimation(this.saveAnimation.getV1(), this.saveAnimation.getV2());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update() {
        if (this.animatorController != null) {
            if (this.animatorController.needUpdate() && !this.animatorController.timerIsRunning()) {
                this.animatorController.updateAnimator(this.animatorController.timerIsActivated(), this.animatorController.timerIsActivated());
                this.setActivated(this.animatorController.timerIsActivated());
            }
            this.animatorController.update();
            this.animatorController.updateAnimation();
        }
    }

    @Override
    public void draw(Graphics g) {
        if (this.isActivated()) {
            this.draw(g, 0, 0);
        }
    }

    @Override
    public void draw(Graphics g, float decalX, float decalY) {
        if (this.isActivated()) {
            this.body.cloneAndDecalFrom(decalX, decalY).draw(g);

            if (this.animatorController != null && this.animatorController.isPrintable()) {
                Pair<Float, Float> position = this.getChoicePosition(this.body);
                this.drawCurrentImage(g, position.getV1() + decalX, position.getV2() + decalY);
            }
        }
    }

    @Override
    public void draw(Graphics g, ColorShape container) {
        if (this.isActivated()) {
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
        try {
            int posX = 0;
            int sizeX = (this.body.getSizeX() > this.animatorController.currentAnimation().getCurrentFrame().getWidth() ? this.animatorController.currentAnimation().getCurrentFrame().getWidth() : (int) this.body.getSizeX());

            int posY = (this.body.getSizeY() > this.animatorController.currentAnimation().getCurrentFrame().getHeight() ? 0 : this.animatorController.currentAnimation().getCurrentFrame().getHeight() - (int) this.body.getSizeY());
            int sizeY = this.animatorController.currentAnimation().getCurrentFrame().getHeight() - posY;

            this.animatorController.setEyesDirection((this.flip? EDirection.LEFT : EDirection.RIGHT));
            this.animatorController.drawSubImage(g, (this.flip? x + this.sizeXMAX - sizeX : x), y, posX, posY, sizeX, sizeY);
/*
            if (this.flip) {
                g.drawImage(this.animatorController.currentAnimation().getCurrentFrame().getSubImage(0, posY, sizeX, sizeY).getFlippedCopy(this.flip, false), x + this.sizeXMAX - sizeX, y);
            } else {
                g.drawImage(this.animatorController.currentAnimation().getCurrentFrame().getSubImage(0, posY, sizeX, sizeY), x, y);
            }
  */
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        if (task instanceof Pair && ((Pair) task).getV1() instanceof ETaskType) {
            if (((Pair) task).getV1() == ETaskType.START_TIMER) {
                this.setActivated(!this.isActivated());
                this.animatorController.updateAnimator(this.isActivated(), this.isActivated());
                this.animatorController.startTimer((Integer) ((Pair) task).getV2());
            }

        } else if (task instanceof Tuple && ((Tuple) task).getV1() instanceof ETaskType) {
            ETaskType order = (ETaskType) ((Tuple) task).getV1();
            Object target = ((Tuple) task).getV2();
            Object value = ((Tuple) task).getV3();

            if (order == ETaskType.SETTER) {
                if (target.equals("index") && value instanceof Integer && this.animatorController != null) {
                    this.animatorController.forceCurrentAnimationIndex((Integer) value);
                } else if (target.equals("animation") && value instanceof EAnimation && this.animatorController != null) {
                    try {
                        this.animatorController.changeAnimation((EAnimation) value);
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (target.equals("flip") && value instanceof Boolean) {
                    this.flip = (boolean) value;
                    return true;
                }
            } else if (order == ETaskType.CUT) {
                if (target.equals("body_X") && value instanceof Float) {
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
                    return true;
                } else if (target.equals("body_Y") && value instanceof Float) {
                    float percent = (float) value;
                    if (percent >= 1) {
                        this.body.setPrintable(true);
                        this.body.setSizes(this.body.getSizeX(), this.sizeYMAX);
                    } else if (percent > 0) {
                        if (!this.id.contains(EGuiType.BORDER.getValue())) {
                            this.body.setPrintable(true);
                            this.body.setSizes(this.body.getSizeX(), this.sizeYMAX * percent);
                        }
                    } else {
                        this.body.setPrintable(false);
                        this.body.setSizes(this.body.getSizeX(), 0);
                    }
                    return true;
                }
            } else if (order == ETaskType.EVENT) {
                if (target instanceof Integer && (int) target <= Input.KEY_NUMPAD9 && (int) target >= Input.KEY_NUMPAD0) {
                    this.animatorController.forceCurrentAnimationIndex((Integer) value);
                    return true;
                }
            }
        }
        return super.doTask(task);
    }

    @Override
    public String toString() {
        return "(" + super.toString() + " imageType: " + (this.animatorController != null ? this.animatorController.currentAnimationType() : ")");
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
        try {
            if (this.animatorController != null && this.animatorController.currentAnimation() != null) {
                return this.animatorController.currentAnimation().getWidth();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.getAbsoluteWidth();
    }

    @Override
    public float getAbsoluteHeight() {
        try {
            if (this.animatorController != null && this.animatorController.currentAnimation() != null) {
                return this.animatorController.currentAnimation().getHeight();
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public void setFlip(boolean flip) {
        this.flip = flip;
    }
}
