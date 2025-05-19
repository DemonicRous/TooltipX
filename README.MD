## After installation:

### Configuration:
- In IntelliJ IDEA, go to **File -> Project Structure** and change the "Compile output" path to "\<project_path>/build/out".
- Verify that the **Sources** are properly configured for the Gradle library `forgeSRC`.
- Go to **File -> Settings -> Build, Execution, Deployment -> Build Tools -> Gradle** and change the following settings:
    - **Build and run using**: Gradle -> IntelliJ IDEA
    - **Run tests using**: Gradle -> IntelliJ IDEA
    -
- Go to the **Run / Debug Configurations**, and change "-cp ExampleProject" to "ExampleProject.main" for both *Minecraft Client* and *Minecraft Server*.
- Change the **Working Directory** to <project_path/run/client> or <project_path/run/server>.

### VM Options:
For the client and server:

```
-Dfml.coreMods.load=com.demonicrous.tooltipx.mixins.TooltipXMixinLoaderPlugin
```
- Option specifies the coremod to load (Mixin Loader in our case)
```
-Dmixin.debug.export=true
```
- When debugging mixins, it's useful to be able to see the finalized classes with your changes and injects inserted.

### Program Options:
- For the client: `--username=NichName --width=1280 --height=720`
- For the server: `nogui`