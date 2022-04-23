# PlayerHider-1.8

This addon allows you to talk in the LabyMod VoiceChat continuously!

[![Build Addon](https://github.com/PrincessAkira/PlayerHider-1.8/actions/workflows/release.yml/badge.svg)](https://github.com/PrincessAkira/PlayerHider-1.8/actions/workflows/release.yml)

## Help needed

```
- Mute Players in Voice when they are hidden
It just crashes whenever i try to set it 
in PlayerEventHandler.java
```

## Setup Workspace

```
gradlew setupDecompWorkspace 
```

#### Setup for InteliJ

```
gradlew idea
```

#### Setup for Eclipse

```
gradlew eclipse
```

#### Build Addon

```
gradlew build 
```

#### Debug Addon

```
-Dfml.coreMods.load=net.labymod.core.asm.LabyModCoreMod -DdebugMode=true -Daddonresources=addon.json
```

For more information you can check out the
LabyMod [documentation](https://docs.labymod.net/pages/create-addons/introduction/).
