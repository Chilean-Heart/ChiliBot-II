package com.team2576.auto.actions;

public class WaitForToteAction extends TimeoutAction {

    public WaitForToteAction(double timeout) {
        super(timeout);
    }

    @Override
    public boolean isFinished() {

        return intake.getBreakbeamTriggered() || super.isFinished();
    }

}


