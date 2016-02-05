package com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiType;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.Element;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.ColorRect;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.ColorShape;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.utils.configs.GameConfig;
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
        this(ELocation.UNKNOWN.getId(), null, animatorController, PositionInBody.MIDDLE_MID, activated);
    }

    public ImageElement(String id, ColorShape body, PositionInBody position, boolean activated) {
        this(id, body, null, position, activated);
    }

    public ImageElement(String id, AnimatorController animatorController, boolean activated) {
        this(id, null, animatorController, PositionInBody.MIDDLE_MID, activated);
    }

    public ImageElement(ColorShape body, AnimatorController animatorController, PositionInBody position, boolean activated) {
        this(ELocation.UNKNOWN.getId(), body, animatorController, position, activated);
    }

    public ImageElement(String id, ColorShape body, AnimatorController animatorController, PositionInBody position, boolean activated) {
        super(EGuiType.IMAGE, id, body, position, activated);
        this.animatorController = animatorController;
        if (this.body == null && this.animatorController != null) {
            this.body = new ColorRect(new Rectangle(0, 0, this.animatorController.currentAnimation().getCurrentFrame().getWidth(),
                    this.animatorController.currentAnimation().getCurrentFrame().getHeight()));
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
            if (this.animatorController.currentAnimation() != null) {
                this.animatorController.currentAnimator().update(GameConfig.currentTimeLoop);
            }
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
            if (this.body.getMinX() != -1) {
                this.body.draw(g);

                if (this.animatorController != null && this.animatorController.isPrintable()) {
                    Pair<Float, Float> position = this.getChoicePosition(this.body);
                    this.drawCurrentImage(g, position.getV1() + decalX, position.getV2() + decalY);
                }
            }
        }
    }

    @Override
    public void draw(Graphics g, ColorShape body) {
        if (this.activated) {
            if (body.getMinX() != -1) {
                if (body.getColor() == null) {
                    body.setColor(this.body.getColor());
                }
                body.draw(g);
                if (this.animatorController != null && this.animatorController.isPrintable()) {
                    Pair<Float, Float> position = this.getChoicePosition(body);
                    this.drawCurrentImage(g, position.getV1(), position.getV2());
                }
            }
        }
    }

    private void drawCurrentImage(Graphics g, float x, float y) {
        int sizeX = (this.body.getSizeX() > this.animatorController.currentAnimation().getCurrentFrame().getWidth() ? this.animatorController.currentAnimation().getCurrentFrame().getWidth() : (int) this.body.getSizeX());
        int sizeY = (this.body.getSizeY() > this.animatorController.currentAnimation().getCurrentFrame().getHeight() ? this.animatorController.currentAnimation().getCurrentFrame().getHeight() : (int) this.body.getSizeY());
        g.drawImage(this.animatorController.currentAnimation().getCurrentFrame().getSubImage(0, 0, sizeX, sizeY), x, y);
    }

    private Pair<Float, Float> getChoicePosition(ColorShape body) {
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
        if (task instanceof ETaskType) {
            if (task == ETaskType.START_ACTIVITY) {
                this.start();
            } else if (task == ETaskType.STOP_ACTIVITY) {
                this.stop();
            }
        } else if (task instanceof Pair && ((Pair) task).getV1() instanceof ETaskType) {
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
    public boolean isActivated() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.animatorController == null || this.animatorController.isDeleted();
    }

    @Override
    public boolean isNull() {
        return (this.animatorController == null);
    }

    @Override
    public float getAbsoluteWidth() {
        if (this.animatorController == null || this.animatorController.currentAnimation() == null)
            return 0;
        return this.animatorController.currentAnimation().getWidth();
    }

    @Override
    public float getAbsoluteHeight() {
        if (this.animatorController == null || this.animatorController.currentAnimation() == null)
            return 0;
        return this.animatorController.currentAnimation().getHeight();
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
