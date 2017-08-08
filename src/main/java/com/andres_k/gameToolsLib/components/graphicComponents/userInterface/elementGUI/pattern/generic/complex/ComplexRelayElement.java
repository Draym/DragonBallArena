package com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.pattern.generic.complex;

import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.EStatus;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorShape;
import com.andres_k.gameToolsLib.components.taskComponent.CentralTaskManager;
import com.andres_k.custom.component.taskComponents.ELocation;
import com.andres_k.custom.component.taskComponents.ETaskType;
import com.andres_k.gameToolsLib.components.taskComponent.utils.TaskComponent;
import com.andres_k.gameToolsLib.utils.stockage.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by com.andres_k on 16/02/2016.
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
    public void reset() {
        super.reset();
        this.current = 0;
    }

    @Override
    public Object doTask(Object task) {
        if (task instanceof ETaskType) {
            if (task.equals(ETaskType.NEXT)) {
                this.current += 1;
                if (this.current == this.targets.size()) {
                    this.current = (this.loop ? 0 : -1);
                    if (!this.loop) {
                        this.tasks.stream().filter(it -> it.getV1() == EStatus.ON_FINISH).collect(Collectors.toList()).stream().filter(pair -> pair.getV2() instanceof TaskComponent).forEach(toDo -> CentralTaskManager.get().sendRequest((TaskComponent) toDo.getV2()));
                    }
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
