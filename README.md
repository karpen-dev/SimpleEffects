## SimpleEffects plugin

[![](https://jitpack.io/v/karpen-dev/SimpleEffects.svg)](https://jitpack.io/#karpen-dev/SimpleEffects)

Plugin versions 1.2 and higher only support game versions 1.21.4+  

Commands: ``/eff <effect name>``  
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
    <version>SEE VERSION IN GITHUB REPO</version>
</dependency>
```
</details> 

<details>
<summary>Gradle</summary>

```groovy
// Jitpack repo
repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

// Main dependency
implementation 'com.github.karpen-dev:SimpleEffects:SEE VERSION IN GITHUB REPO'
```
</details>

<details>
<summary>Gradle.kts</summary>

``` kotlin
// Jitpack repo
repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
repositories {
    mavenCentral()
	maven { url = uri("https://jitpack.io") }
}

// Main dependency
implementation("com.github.karpen-dev:SimpleEffects:SEE VERSION IN GITHUB REPO")
```
</details>

### Using api   

<details>
<summary>Java</summary>

```java
// Install api
SimpleEffectsApi api = SimpleEffects.getApi();

// Active cherry effect
api.activeEffectCherryToPlayer(player);
```
</details>

<details>
<summary>Kotlin</summary>

```kotlin
// Install api
val api = SimpleEffects.getApi();

// Active cherry effect
api.activeEffectCherryToPlayer(player);
```
</details>
