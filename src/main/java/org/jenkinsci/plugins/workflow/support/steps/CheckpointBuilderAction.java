package org.jenkinsci.plugins.workflow.support.steps;
import hudson.model.Action;
import hudson.model.Job;
import hudson.model.Project;

public class CheckpointBuilderAction implements Action{

    private Job job;

    public CheckpointBuilderAction(Job job) {
        this.job = job;
    }



    @Override
    public String getIconFileName() {
        return "clock.png";
    }

    @Override
    public String getDisplayName() {
        return "Build using Checkpoint";
    }

    @Override
    public String getUrlName() {
        return "/"+job.getUrl()+"build";
    }
}





