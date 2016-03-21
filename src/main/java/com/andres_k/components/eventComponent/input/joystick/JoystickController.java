package com.andres_k.components.eventComponent.input.joystick;

import com.andres_k.components.eventComponent.input.EInput;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;

/**
 * Created by andres_k on 21/03/2016.
 */
public class JoystickController {
    private enum JoystickDirection {
        NOTHING(EInput.NOTHING, EDirection.NONE),
        MOVE_UP(EInput.MOVE_UP, EDirection.TOP),
        MOVE_DOWN(EInput.MOVE_DOWN, EDirection.DOWN),
        MOVE_LEFT(EInput.MOVE_LEFT, EDirection.LEFT),
        MOVE_RIGHT(EInput.MOVE_RIGHT, EDirection.RIGHT);

        private EInput value;
        private EDirection key;
        private JoystickDirection(EInput value, EDirection key) {
            this.value = value;
            this.key = key;
        }

        public EInput getValue() {
            return this.value;
        }
        public EDirection getKey() {
            return this.key;
        }

        public static JoystickDirection getInput(EDirection key) {
            JoystickDirection[] inputs = JoystickDirection.values();

            for (JoystickDirection input : inputs) {
                if (input.key == key) {
                    return input;
                }
            }
            return NOTHING;
        }
    }

    private enum JoystickInput {
        NOTHING(EInput.NOTHING, 0),
        ATTACK_A(EInput.ATTACK_A, 1),
        ATTACK_B(EInput.ATTACK_B, 2),
        ATTACK_C(EInput.ATTACK_C, 3),
        ATTACK_D(EInput.ATTACK_D, 4),
        ATTACK_SPE1(EInput.ATTACK_SPE, 5),
        ATTACK_SPE2(EInput.ATTACK_SPE, 6);

        private EInput value;
        private int key;
        private JoystickInput(EInput value, int key) {
            this.value = value;
            this.key = key;
        }

        public EInput getValue() {
            return this.value;
        }
        public int getKey() {
            return this.key;
        }

        public static JoystickInput getInput(int key) {
            JoystickInput[] inputs = JoystickInput.values();

            for (JoystickInput input : inputs) {
                if (input.key == key) {
                    return input;
                }
            }
            return NOTHING;
        }
    }

    public static EInput transformButton(int controller, int button) {
        controller += 1;

        return EInput.getEnumByValue(JoystickInput.getInput(button).getValue() + "_P" + controller);
    }

    public static EInput transformDirection(int controller, EDirection direction) {
        controller += 1;
        return EInput.getEnumByValue(JoystickDirection.getInput(direction).getValue() + "_P" + controller);
    }
}
