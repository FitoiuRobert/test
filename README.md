HOW TO USE:

I) In order to start a build with skipped checkpoint do the following:
1. Add "checkpoint STAGE_NAME" as a last step in every stage you want to skip (or in a post-success).
2. Build your job, and if in the Console Output the following line appears 'Checkpoint made in: {checkpointFolderPath}', if not, something went wrong.
3. Go to Configure > Check 'This project is parameterized', and add a String parameter with the exact name "buildWithCheckpoint" (the default value doesn't matter).
4. Now in the job page, build your job again by using Build with Parameters.
5. The stage with checkpoints from the previous build should be now skipped. (You can check in Console Output if "Skipping stage Build" appears)

II) In order to build without checkpoints do the following:
1. If you have a parameter named "buildWithCheckpoint" remove it.
2. Build the job normally.


