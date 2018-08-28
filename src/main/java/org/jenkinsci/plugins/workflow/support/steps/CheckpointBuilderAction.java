package org.jenkinsci.plugins.workflow.support.steps;
import hudson.model.*;

import java.io.IOException;

public class CheckpointBuilderAction implements Action{

    private Job job;

    private transient Run<?, ?> run;
    private transient TaskListener taskListener;


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
        return "/"+job.getUrl()+"build?USE_CHECKPOINT=true";

    }
}





