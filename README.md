# Steps

#### Produce resources.txt file
**resources.txt** file is a file that will be generated if we build an app with shrinkResources enabled. It contains information about unused resources that we can remove. To produce it we need to do these:  
1. Empty DFM list in app/build.gradle → dynamicFeatures = []
2. Enable ShrinkResources in app/build.gradle → shrinkResources = true
3. Build the app → ./gradlew app:assemble<Variant> 
4. Wait until the build is successful
5. Locate resources.txt in app/build/outputs/mapping/<variant>/resources.txt
#### Modify resources.txt to the expected format
1. Remove all lines except line with format **Skipped unused resource res/drawable-xxhdpi-v4/ic_launcher_background.png: 767 bytes (replaced with small dummy file of size 67 bytes)**
2. Modify those lines, extract only **res/drawable-xxhdpi-v4/ic_launcher_background.png**
#### Run ResourceCleaner.jar
1. It takes 2 arguments, 
2. First argument is for the path of resources.txt
3. Second argument is for the path of the android project, if it's not specified the default value for it is **"."**
4. The command is java -jar <path to the jar>/ResourceCleaner.jar [resources.txt path] [android project path] resources.txt**
#### Revert changes in Produce resources file step
#### Try build the project
If the build is failed, there's a possibility that some resources are used by a method or a class that is unused. You can delete the method or the class and try to rebuild again.
#### Commit, Push and Create PR