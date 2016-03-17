package com.andres_k.components.gameComponents.gameObject.objects;

import com.andres_k.components.eventComponent.events.EventController;
import com.andres_k.components.eventComponent.input.EInput;
import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.gameComponents.animations.details.AnimationRepercussionItem;
import com.andres_k.components.gameComponents.collisions.PhysicalObject;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.commands.comboComponent.ComboController;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiElement;
import com.andres_k.components.taskComponent.CentralTaskManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.components.taskComponent.TaskFactory;
import com.andres_k.utils.configs.GlobalVariable;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.stockage.Tuple;
import org.newdawn.slick.SlickException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andres_k on 10/07/2015.
 */
public class Player extends PhysicalObject {
    protected Map<String, Method> specialActions;
    protected Map<EAnimation, Method> checkBeforeLaunch;
    protected EventController event;
    protected ComboController comboController;
    private long score;
    protected final int maxKi;
    protected int currentKi;
    protected final int maxEnergy;
    protected int currentEnergy;

    public Player(AnimatorController animatorController, EGameObject type, String id, float x, float y, float life, float damage, float speed, float weight) {
        super(animatorController, type, id, x, y, life, damage, speed, weight);

        this.event = new EventController();
        this.event.addEvent(EInput.MOVE_UP);
        this.event.addEvent(EInput.MOVE_DOWN);
        this.event.addEvent(EInput.MOVE_LEFT);
        this.event.addEvent(EInput.MOVE_RIGHT);
        this.event.addEvent(EInput.ATTACK_A);
        this.event.addEvent(EInput.ATTACK_B);
        this.event.addEvent(EInput.ATTACK_C);
        this.event.addEvent(EInput.ATTACK_D);
        this.event.addEvent(EInput.ATTACK_SPE);

        this.specialActions = new HashMap<>();
        this.checkBeforeLaunch = new HashMap<>();
        try {
            this.comboController = new ComboController(this.type);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            this.comboController = null;
            e.printStackTrace();
        }
        this.maxKi = 600;
        this.maxEnergy = 500;
        this.currentKi = this.maxKi;
        this.currentEnergy = this.maxEnergy;
    }

    @Override
    public void clear() {
        this.resetActions();
    }

    @Override
    public void update() throws SlickException {
        this.comboController.update();
        this.animatorController.update();
        this.executeLastActionEvent();
        this.animatorController.doCurrentAction(this);
        this.movement.update();
        if (this.animatorController.canSwitchCurrent()) {
            if (this.event.allInactive()) {
                this.moveFall();
            } else {
                this.executeLastDirectionEvent();
            }
        }
    }

    @Override
    public void resetActions() {
        this.comboController.reset();
        this.event.reset();
    }

    // ACTIONS

    private boolean moveFall() throws SlickException {
        if (!this.isOnEarth()
                && this.animatorController.currentAnimationType() != EAnimation.FALL
                && this.animatorController.currentAnimationType() != EAnimation.RECEIPT
                && this.animatorController.currentAnimationType() != EAnimation.TOUCHED_FALL
                && this.animatorController.currentAnimationType() != EAnimation.TOUCHED_RECEIPT
                && this.animatorController.canSwitchCurrent()) {
            this.animatorController.changeAnimation(EAnimation.FALL);
            this.movement.resetGravity();
            return true;
        }
        return false;
    }

    private boolean moveRight() throws SlickException {
        if (this.movement.getMoveDirection() != EDirection.RIGHT || this.animatorController.currentAnimationType() != EAnimation.RUN) {
            this.animatorController.setEyesDirection(EDirection.RIGHT);
            this.animatorController.changeAnimation(EAnimation.RUN);
            this.movement.setMoveDirection(EDirection.RIGHT);
            this.event.addStackEvent(EInput.MOVE_RIGHT);
            if (this.event.isActivated(EInput.MOVE_UP))
                this.moveUp();
            return true;
        }
        return false;
    }

    private boolean moveLeft() throws SlickException {
        if (this.movement.getMoveDirection() != EDirection.LEFT || this.animatorController.currentAnimationType() != EAnimation.RUN) {
            this.animatorController.setEyesDirection(EDirection.LEFT);
            this.animatorController.changeAnimation(EAnimation.RUN);
            this.movement.setMoveDirection(EDirection.LEFT);
            this.event.addStackEvent(EInput.MOVE_LEFT);
            if (this.event.isActivated(EInput.MOVE_UP))
                this.moveUp();
            return true;
        }
        return false;
    }

    private boolean moveDown() throws SlickException {
        if (this.isOnEarth())
            this.animatorController.changeAnimation(EAnimation.DEFENSE);
        else
            this.animatorController.changeAnimation(EAnimation.FALL);
        this.event.addStackEvent(EInput.MOVE_DOWN);
        return true;
    }

    private boolean moveUp() throws SlickException {
        this.changeDirection();
        this.animatorController.changeAnimation(EAnimation.JUMP);
        if (!this.isOnEarth())
            this.animatorController.forceCurrentAnimationIndex(1);
        this.setOnEarth(false);
        this.movement.resetGravity();
        this.event.addStackEvent(EInput.MOVE_UP);
        return true;
    }

    private void changeDirection() {
        EInput recentMove = this.event.getMoreRecentEventBetween(EInput.MOVE_RIGHT, EInput.MOVE_LEFT);
        if (recentMove == EInput.MOVE_RIGHT) {
            this.movement.setMoveDirection(EDirection.RIGHT);
        } else if (recentMove == EInput.MOVE_LEFT) {
            this.movement.setMoveDirection(EDirection.LEFT);
        } else {
            this.movement.setMoveDirection(EDirection.NONE);
        }
    }

    private boolean executeLastDirectionEvent() throws SlickException {
        if (this.animatorController.canSwitchCurrent()) {
            EInput last = this.event.getTheLastEvent();

            if (last != EInput.NOTHING) {
                if (last == EInput.MOVE_RIGHT) {
                    return this.moveRight();
                } else if (last == EInput.MOVE_LEFT) {
                    return this.moveLeft();
                } else if (last == EInput.MOVE_DOWN) {
                    return this.moveDown();
                } else if (last == EInput.MOVE_UP) {
                    return this.moveUp();
                }
            }
        }
        return false;
    }

    private boolean executeLastActionEvent() {
        EInput last = this.event.consumeStackEvent();

        if (last == EInput.NOTHING) {
            last = this.event.getTheLastEvent();
        }
        if (last != EInput.NOTHING && this.comboController != null) {
            if (this.comboController.nextComboStep(this.animatorController, last)) {
                Pair<EAnimation, Integer> nextAnim = this.comboController.getCurrentAnimation();

                if (this.canLaunchAction(nextAnim.getV1())) {
                    try {
                        this.animatorController.changeAnimation(nextAnim.getV1(), nextAnim.getV2());
                        return true;
                    } catch (SlickException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    private boolean canLaunchAction(EAnimation action) {
        if (this.checkBeforeLaunch.containsKey(action)) {
            try {
                Object result = this.checkBeforeLaunch.get(action).invoke(this);

                if (result instanceof Boolean) {
                    return (boolean) result;
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    // EVENT
    @Override
    public void eventPressed(EInput input) {
        if (this.isAlive()) {
            this.event.setActivated(input.getContainer(), true);
        }
    }

    @Override
    public void eventReleased(EInput input) {
        if (this.isAlive()) {
            this.event.setActivated(input.getContainer(), false);
        }
    }

    @Override
    public Object doTask(Object task) {
        if (task instanceof Pair && ((Pair) task).getV1() instanceof ETaskType) {
            Pair<ETaskType, Object> received = (Pair<ETaskType, Object>) task;

            if (received.getV1() == ETaskType.UPGRADE_SCORE && received.getV2() instanceof Integer) {
                this.score += (int) received.getV2();
            } else if (received.getV1() == ETaskType.CREATE && received.getV2() instanceof String) {
                if (this.specialActions.containsKey(received.getV2())) {
                    try {
                        this.specialActions.get(received.getV2()).invoke(this);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            } else if (received.getV1() == ETaskType.NEXT && received.getV2().equals("frame")) {
                if (this.animatorController != null) {
                    this.animatorController.forceNextFrame();
                }
            }
        } else if (task instanceof Tuple) {
            if (((Tuple) task).getV1() instanceof ETaskType && ((Tuple) task).getV2() instanceof String) {
                Tuple<ETaskType, String, Object> received = (Tuple<ETaskType, String, Object>) task;

                if (received.getV1().equals(ETaskType.ADD)) {
                    if (received.getV2().equals("ki")) {
                        this.incrementCurrentKi((Integer) received.getV3());
                    } else if (received.getV2().equals("energy")) {
                        this.incrementCurrentEnergy((Integer) received.getV3());
                    }
                }
            }
        }
        return null;
    }

    // GETTERS

    public long getScore() {
        return this.score;
    }

    public String getPseudo() {
        if (this.id.contains(GlobalVariable.id_delimiter)) {
            return this.id.substring(this.id.indexOf(GlobalVariable.id_delimiter) + 1, this.id.length());
        } else {
            return this.id;
        }
    }

    public int getIdIndex() {
        if (this.id.contains(GlobalVariable.id_delimiter)) {
            int index = this.id.indexOf(GlobalVariable.id_delimiter);
            return Integer.valueOf(this.id.substring(index - 1, index));
        } else {
            return -1;
        }
    }

    @Override
    public void manageGetHit(GameObject enemy) {
        if (!this.wasHit) {
            float enemyDamage = enemy.getDamage();
            enemy.powerAfterDoDamage((enemyDamage - this.currentLife < 0 ? 0 : enemyDamage - this.currentLife));
            this.incrementCurrentLife(-enemyDamage);

            AnimationRepercussionItem repercussionItem = enemy.getAnimatorController().getCurrentContainer().getRepercussion();
            if (repercussionItem != null) {
                repercussionItem.applyAnimationRepercussion(this);
                repercussionItem.applyEffectRepercussion(enemy, this);
            }
            this.setLastAttacker(enemy);
            this.wasHit = true;
            this.resetHitStatus();
        }
    }

    // SETTERS

    @Override
    public boolean setCurrentLife(float value) {
        if (super.setCurrentLife(value)) {
            CentralTaskManager.get().sendRequest(TaskFactory.createTask(ELocation.UNKNOWN, (this.getIdIndex() == 0 ? ELocation.GAME_GUI_State_AlliedPlayers : ELocation.GAME_GUI_State_EnemyPlayers), new Tuple<>(ETaskType.RELAY, this.getId() + GlobalVariable.id_delimiter + EGuiElement.STATE_PLAYER, new Tuple<>(ETaskType.SETTER, "life", value / this.maxLife))));
            return true;
        }
        return false;
    }

    public void setCurrentKi(int value) {
        this.currentKi = value;
        CentralTaskManager.get().sendRequest(TaskFactory.createTask(ELocation.UNKNOWN, (this.getIdIndex() == 0 ? ELocation.GAME_GUI_State_AlliedPlayers : ELocation.GAME_GUI_State_EnemyPlayers), new Tuple<>(ETaskType.RELAY, this.getId() + GlobalVariable.id_delimiter + EGuiElement.STATE_PLAYER, new Tuple<>(ETaskType.SETTER, "ki", (float)this.currentKi * 100 / (float)this.maxKi))));
    }

    public void setCurrentEnergy(int value) {
        this.currentEnergy = value;
        CentralTaskManager.get().sendRequest(TaskFactory.createTask(ELocation.UNKNOWN, (this.getIdIndex() == 0 ? ELocation.GAME_GUI_State_AlliedPlayers : ELocation.GAME_GUI_State_EnemyPlayers), new Tuple<>(ETaskType.RELAY, this.getId() + GlobalVariable.id_delimiter + EGuiElement.STATE_PLAYER, new Tuple<>(ETaskType.SETTER, "energy", (float)this.currentEnergy * 100 / (float)this.maxEnergy))));
    }

    public void incrementCurrentKi(int value) {
        this.currentKi += value;
        if (this.currentKi < 0) {
            this.setCurrentKi(0);
        } else if (this.currentKi > this.maxKi) {
            this.setCurrentKi(this.maxKi);
        }
        this.setCurrentKi(this.currentKi);
    }

    public void incrementCurrentEnergy(int value) {
        this.currentEnergy += value;
        if (this.currentEnergy < 0) {
            this.setCurrentEnergy(0);
        } else if (this.currentEnergy > this.maxEnergy) {
            this.setCurrentEnergy(this.maxEnergy);
        }
        this.setCurrentEnergy(this.currentEnergy);
    }
}
