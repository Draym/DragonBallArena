package com.andres_k.components.graphicComponents.userInterface.tools.elements;

import com.andres_k.components.gameComponents.animations.Animator;
import com.andres_k.components.graphicComponents.userInterface.overlay.EnumOverlayElement;
import com.andres_k.components.graphicComponents.userInterface.tools.items.ColorRect;
import com.andres_k.components.taskComponent.EnumTask;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.stockage.Tuple;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres_k on 27/06/2015.
 */
public class StringToImageElement extends Element {
    private String alphabet[] = {"0123456789", "abcdefghijklmnopqrstuvwxyz- "};
    private Animator animator;
    private List<Integer> images;
    private float sizeXMAX;
    private String value;

    public StringToImageElement(ColorRect body, Animator animator, String value, String id, PositionInBody position) {
        this.init(body, id, position, EnumOverlayElement.IMAGE);
        this.animator = animator;
        this.sizeXMAX = body.getSizeX();
        this.images = new ArrayList<>();
        this.value = value.toLowerCase();
        this.constructImages();
    }

    public StringToImageElement(Animator animator, String value, String id, PositionInBody position) {
        this.init(null, id, position, EnumOverlayElement.IMAGE);
        this.animator = animator;
        this.sizeXMAX = 0;
        this.images = new ArrayList<>();
        this.value = value.toLowerCase();
        this.constructImages();
    }


    @Override
    public void leave() {
        this.images.clear();
    }

    private void constructImages() {
        this.images.clear();
        for (int i = 0; i < this.value.length(); ++i) {
            int posNum = this.alphabet[0].indexOf(value.charAt(i));
            if (posNum != -1) {
                this.images.add(posNum);
            } else {
                int posLet = this.alphabet[1].indexOf(value.charAt(i));
                if (posLet != -1) {
                    this.images.add(posLet + 10);
                }
            }
        }
    }

    private void drawImages(Graphics g, float x, float y) {
        int addY;
        int addX = 25;
        for (int i = 0; i < this.images.size(); ++i) {
            Animation tmp = this.animator.getAnimation(this.animator.getCurrentAnimation(), this.images.get(i));
            if (tmp != null) {
                if (this.images.get(i) == this.alphabet[1].indexOf("-") + 10) {
                    addY = 10;
                    addX = 25;
                } else {
                    addY = 0;
                    addX = tmp.getWidth() - 4;
                }
                g.drawAnimation(tmp, x, y + addY);
            }
            x += addX;
        }
    }

    public void draw(Graphics g) {
        if (this.body != null && this.body.getMinX() != -1) {
            this.body.draw(g);

            if (this.animator != null && this.animator.isPrintable()) {
                Pair<Float, Float> position = this.getChoicePosition(this.body);
                this.drawImages(g, position.getV1(), position.getV2());
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
            if (this.animator != null && this.animator.isPrintable()) {
                Pair<Float, Float> position = this.getChoicePosition(body);
                this.drawImages(g, position.getV1(), position.getV2());
            }
        }
    }

    private Pair<Float, Float> getChoicePosition(ColorRect body) {
        float x = body.getMinX();
        float y = body.getMinY();

        if (this.position == PositionInBody.MIDDLE_MID) {
            float sizeX = (body.getSizeX() / 2) - (this.getAbsoluteWidth() / 2);
            float sizeY = (body.getSizeY() / 2) - (this.getAbsoluteHeight() / 2);

            sizeX = (sizeX < 0 ? 0 : sizeX);
            sizeY = (sizeY < 0 ? 0 : sizeY);
            x += sizeX;
            y += sizeY;
        } else if (this.position == PositionInBody.RIGHT_MID) {
            float sizeX = (body.getSizeX() - this.getAbsoluteWidth());
            float sizeY = (body.getSizeY() / 2) - (this.getAbsoluteHeight() / 2);

            sizeX = (sizeX < 0 ? 0 : sizeX);
            sizeY = (sizeY < 0 ? 0 : sizeY);
            x += sizeX;
            y += sizeY;
        } else if (this.position == PositionInBody.MIDDLE_DOWN) {
            float sizeX = (body.getSizeX() / 2) - (this.getAbsoluteWidth() / 2);
            float sizeY = (body.getSizeY() - this.getAbsoluteHeight());

            sizeX = (sizeX < 0 ? 0 : sizeX);
            sizeY = (sizeY < 0 ? 0 : sizeY);
            x += sizeX;
            y += sizeY;
        } else if (this.position == PositionInBody.RIGHT_DOWN) {
            float sizeX = (body.getSizeX() - this.getAbsoluteWidth());
            float sizeY = (body.getSizeY() - this.getAbsoluteHeight());

            sizeX = (sizeX < 0 ? 0 : sizeX);
            sizeY = (sizeY < 0 ? 0 : sizeY);
            x += sizeX;
            y += sizeY;
        } else if (this.position == PositionInBody.LEFT_DOWN) {
            float sizeY = (body.getSizeY() - this.getAbsoluteHeight());

            sizeY = (sizeY < 0 ? 0 : sizeY);
            y += sizeY;
        } else if (this.position == PositionInBody.MIDDLE_UP) {
            float sizeX = (body.getSizeX() / 2) - (this.getAbsoluteWidth() / 2);

            sizeX = (sizeX < 0 ? 0 : sizeX);
            x += sizeX;
        } else if (this.position == PositionInBody.RIGHT_UP) {
            float sizeX = (body.getSizeX() - this.getAbsoluteWidth());

            sizeX = (sizeX < 0 ? 0 : sizeX);
            x += sizeX;
        }

        return new Pair<>(x, y);
    }

    @Override
    public void update() {
        if (this.animator != null) {
            if (this.animator.needUpdate() && this.animator.isActivated()) {
                this.animator.updateAnimator(true, true);
            }
        }
    }

    @Override
    public boolean replace(Element element) throws SlickException {
        if (element.getType() == EnumOverlayElement.IMAGE) {
            this.animator = new Animator(((StringToImageElement) element).animator);
            this.images.clear();
            this.images.addAll(((StringToImageElement) element).images);
            this.constructImages();
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
            this.animator.updateAnimator(false, false);
            this.animator.startTimer((Long) task);

        } else if (task instanceof Tuple && ((Tuple) task).getV1() instanceof EnumTask) {
            EnumTask order = (EnumTask) ((Tuple) task).getV1();
            Object target = ((Tuple) task).getV2();
            Object value = ((Tuple) task).getV3();

            if (order == EnumTask.SETTER) {
                if (target.equals("value") && value instanceof String && this.animator != null) {
                    this.value = ((String) value).toLowerCase();
                    this.constructImages();
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
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "MultipleImageType: " + (this.animator != null ? this.animator.getCurrentAnimation() : "");
    }

    private void start() {
        this.animator.restart();
    }

    private void stop() {
        this.animator.restart();
    }

    //GETTERS
    @Override
    public boolean isActivated() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        if (this.animator == null || this.animator.isDeleted()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isNull() {
        return (this.animator == null);
    }

    @Override
    public float getAbsoluteWidth() {
        float size = 0;
        for (int i = 0; i < this.images.size(); ++i) {
            Animation tmp = this.animator.getAnimation(this.animator.getCurrentAnimation(), this.images.get(i));
            if (tmp != null) {
                size += tmp.getWidth() - 4;
            } else {
                size += 25;
            }
        }
        return size;
    }

    @Override
    public float getAbsoluteHeight() {
        float size = 0;
        for (int i = 0; i < this.images.size(); ++i) {
            Animation tmp = this.animator.getAnimation(this.animator.getCurrentAnimation(), this.images.get(i));
            if (tmp != null) {
                if (size < tmp.getHeight())
                    size = tmp.getHeight();
            }
        }
        return size;
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
