package org.jenkinsci.plugins.workflow.support.steps;

import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.*;
import hudson.tasks.BuildStepMonitor;
import jenkins.model.TransientActionFactory;
import hudson.model.ParameterValue;
import jenkins.tasks.SimpleBuildStep;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Extension
public class CheckpointBuilderActionFactory extends TransientActionFactory<Job>{

    private transient Run<?, ?> run;
    private transient TaskListener taskListener;




    @Override
    public Class<Job> type() {
        return Job.class;
    }


    @Nonnull
    @Override
    public Collection<? extends Action> createFor(@Nonnull Job job) {
         //new ParameterValue().withId("useCheckpoint").withStringValue("true");

        /*
        if(run.getUrl().contains("useCheckpoint")) {
            taskListener.getLogger().println("FACTORY FOUND CHECKPOINT");
            //envVars.put("useCheckpoint","usingCheckpoint");
        }else taskListener.getLogger().println("FACTORY CHECKPOINT ABSENT");
        */

        return Collections.singleton(new CheckpointBuilderAction(job));

    }

}

