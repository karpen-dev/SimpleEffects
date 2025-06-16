## SimpleEffects plugin

**The old API no longer works, use the new more convenient one according to the documentation below**

If you are using a version lower than 1.21.4, don't report issues.

Command: ``/eff``  
To enable effects, give permission `karpen.simpleEffects.eff`.  
   
Reload command: ``/eff-reload``.   
To reload, give permission `karpen.simpleEffects.reload`.     
   
Available at:
[Modrinth](https://modrinth.com/plugin/simpleeffects)
[Spigotmc](https://www.spigotmc.org/resources/simpleeffects.121141/)

## Api docs
### Add api to you project   

<details>
<summary>Maven</summary>

[![](https://jitpack.io/v/karpen-dev/SimpleEffects.svg)](https://jitpack.io/#karpen-dev/SimpleEffects)

```xml
<!-- Jitpack repo -->
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<!-- Main dependency -->
<dependency>
    <groupId>com.github.karpen-dev</groupId>
    <artifactId>SimpleEffects</artifactId>
    <version>YOU VERSION</version>
    <scope>provided</scope>
</dependency>
```
</details> 

<details>
<summary>Gradle</summary>

[![](https://jitpack.io/v/karpen-dev/SimpleEffects.svg)](https://jitpack.io/#karpen-dev/SimpleEffects)

```groovy
// Jitpack repo
repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

// Main dependency
compileOnly 'com.github.karpen-dev:SimpleEffects:YOU VERSION'
```
</details>

<details>
<summary>Gradle.kts</summary>

[![](https://jitpack.io/v/karpen-dev/SimpleEffects.svg)](https://jitpack.io/#karpen-dev/SimpleEffects)

``` kotlin
// Jitpack repo
repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
repositories {
    mavenCentral()
	maven { url = uri("https://jitpack.io") }
}

// Main dependency
compileOnly("com.github.karpen-dev:SimpleEffects:YOU VERSION")
```
</details>

### Using api
```yaml
# plugin.yml
# Register dependency
...
depend: [SimpleEffects]
# or
softDepend: [SimpleEffects]
...
```

<details>
<summary>Java</summary>

```java
// Install api
SimpleEffectsApi api = SimpleEffects.getApi();

// Active cherry effect
api.active(Type.CHERRY, player); // Type: CHERRY, ENDROD, TOTEM, PALE, HEART, PURPLE, NOTE, CLOUD
api.disable(Type.CHERRY, player); // Disable effect if its active
api.getEffect(player); // return Type enum
```
</details>

<details>
<summary>Kotlin</summary>

```kotlin
// Install api
val api = SimpleEffects.getApi();

// Active cherry effect
api.active(Type.CHERRY, player); // Type: CHERRY, ENDROD, TOTEM, PALE, HEART, PURPLE, NOTE, CLOUD
api.disable(Type.CHERRY, player); // Disable effect if its active
api.getEffect(player); // return Type enum
```
</details>
