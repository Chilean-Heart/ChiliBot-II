package com.team2576.auto.actions;

public class WaitForDriveAction extends TimeoutAction {
    public WaitForDriveAction(double timeout) {
        super(timeout);
    }

    @Override
    public boolean isFinished() {
        return drive.controllerOnTarget() || super.isFinished();
    }

}
