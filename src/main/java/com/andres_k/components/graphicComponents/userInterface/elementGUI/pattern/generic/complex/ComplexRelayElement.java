package com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.generic.complex;

import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorShape;
import com.andres_k.components.taskComponent.CentralTaskManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.components.taskComponent.utils.TaskComponent;
import com.andres_k.utils.stockage.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres_k on 16/02/2016.
 */
public class ComplexRelayElement extends ComplexElement {
    private List<ELocation> targets;
    private int current;
    private boolean loop;

    public ComplexRelayElement(String id, ColorShape container, boolean loop, boolean activated) {
        super(id, container, activated);
        this.loop = loop;
        this.current = 0;
        this.targets = new ArrayList<>();
    }

    public void addTarget(ELocation target) {
        this.targets.add(target);
    }

    public void addTargets(List<ELocation> targets) {
        this.targets.addAll(targets);
    }

    @Override
    public Object doTask(Object task) {
        if (task instanceof ETaskType) {
            if (task.equals(ETaskType.NEXT)) {
                this.current += 1;
                if (this.current == this.targets.size()) {
                    this.current = (this.loop ? 0 : -1);
                }
                return true;
            }
        }
        else if (task instanceof Pair && ((Pair) task).getV1() instanceof ETaskType) {
            if (((Pair) task).getV1().equals(ETaskType.RELAY)) {
                if (this.current >= 0 && this.current < this.targets.size()) {
                    CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.getEnumByLocation(this.getId()), this.targets.get(this.current), ((Pair) task).getV2()));
                    return true;
                }
            }
        }
        return super.doTask(task);
    }
}
