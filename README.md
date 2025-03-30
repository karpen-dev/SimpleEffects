## SimpleEffects plugin

Plugin versions 1.2 and higher only support game versions 1.21.4+  

Commands: ``/eff <effect name>``  
To enable effects, give permission `karpen.simpleEffects.eff`.  
   
Reload command: ``/eff-reload``.   
To reload, give permission `karpen.simpleEffects.reload`.     
   
Available at:
[Modrinth](https://modrinth.com/plugin/simpleeffects)
[Spigotmc](https://www.spigotmc.org/resources/simpleeffects.121141/)

### Api docs
1. Add api to you project   

Maven:
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
    <version>1.2.2</version>
</dependency>
```
Gradle
```groovy
// Jitpack repo
repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

// Main dependency
implementation 'com.github.karpen-dev:SimpleEffects:1.2.2'
```
Gradle.kts:
``` kotlin
// Jitpack repo
repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
repositories {
    mavenCentral()
	maven { url = uri("https://jitpack.io") }
}

// Main dependency
implementation("com.github.karpen-dev:SimpleEffects:1.2.2")
```
2. Using api   

Java:
```java
// Install api
SimpleEffectsApi api = SimpleEffects.getApi();

// Active cherry effect
api.activeEffectCherryToPlayer(player);
```
Kotlin:
```kotlin
// Install api
val api = SimpleEffects.getApi();

// Active cherry effect
api.activeEffectCherryToPlayer(player);
```