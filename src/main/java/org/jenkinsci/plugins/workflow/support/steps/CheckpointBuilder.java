package org.jenkinsci.plugins.workflow.support.steps;

import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.*;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import jenkins.tasks.SimpleBuildStep;
import net.sf.json.JSONObject;
import org.jenkinsci.Symbol;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.Stapler;
import org.kohsuke.stapler.StaplerRequest;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CheckpointBuilder extends Builder implements SimpleBuildStep {

    private final String stageName;


    @DataBoundConstructor
    public CheckpointBuilder(String stageName) {
        this.stageName = stageName;
    }

    public String getStageName() {
        return stageName;
    }

    private void createCheckpointFile(String jobName, TaskListener taskListener) {
        String dirPath = System.getenv("JENKINS_HOME") + "/workspace/" + jobName + "@checkpoint";

        if (!createCheckpointDirIfDoesNotExist(dirPath)) { return; }

        if (!checkpointFileExist(dirPath)) {
            File checkpointFile = new File(dirPath + "/" + stageName);
            createStageNameFile(checkpointFile);
            taskListener.getLogger().println("Checkpoint made in: " + dirPath);
        }
    }

    private boolean createCheckpointDirIfDoesNotExist(String dirPath) {
        File dir = new File(dirPath);
        if(!dir.exists()){
             if (!dir.mkdir()) {
                 System.out.println("Error when creating directory");
                 return false;
             }
        }
        return true;
    }

    private boolean checkpointFileExist(String path) {
        Path source = Paths.get(path);
        try {
            return Files.walk(source).filter(Files::isRegularFile).anyMatch(file -> file.getFileName().toString().equals(stageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void createStageNameFile(File checkpointFile) {
        try {
            boolean fileCreated = checkpointFile.createNewFile();
            if (!fileCreated) {
                System.out.println("Couldn't create file");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void perform(@Nonnull Run<?, ?> run, @Nonnull FilePath filePath, @Nonnull Launcher launcher, @Nonnull TaskListener taskListener) throws InterruptedException, IOException {
        EnvVars envVars = run.getEnvironment(taskListener);
        String jobName = envVars.get("JOB_BASE_NAME");
        //run.addAction(new CheckpointBuilderAction(run.getParent()));
        StaplerRequest req = Stapler.getCurrentRequest();

        taskListener.getLogger().println("URL from run: "+ run.getUrl());

        if(run.getUrl().contains("useCheckpoint")) {
            taskListener.getLogger().println("BUILDER FOUND CHECKPOINT");
            envVars.put("useCheckpoint","usingCheckpoint");
        }else taskListener.getLogger().println("BUILDER CHECKPOINT ABSENT");

        if(run.getUrl().contains("Woot")) taskListener.getLogger().println("BUILDER FOUND Woot");
        else taskListener.getLogger().println("BUILDER CHECKPOINT Woot");
        createCheckpointFile(jobName, taskListener);

    }

    @Symbol("checkpoint")
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return "checkpoint";
        }

    }
}
