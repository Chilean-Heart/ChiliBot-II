package com.team2576.auto.actions;

public class WaitForTopCarriageSensorAction extends TimeoutAction {
    public WaitForTopCarriageSensorAction(double timeout) {
        super(timeout);
    }

    @Override
    public boolean isFinished() {

        return top_carriage.getBreakbeamTriggered() || super.isFinished();
    }

}


