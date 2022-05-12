# Comparing Android Static Analysis (CASA)

CASA is an end-to-end pipeline for comparing Android static analysis tools. This tool is currently being implemented.

# Running CASA

python2 casa.py -cg 0 -qk 0 -fd 0

# Running Individual Tools

## CryptoGuard

`https://github.com/CryptoGuardOSS/cryptoguard`

Requires Java 8 JDK installation

NOTE: Ensure that $JAVA_HOME $ANDROID_HOME are set! CryptoGuard will not run if they are not added to $PATH.

Usage:

`java -jar src/cryptoguard/cryptoguard.jar -in apk -s apks/air.pokemonquiz.apk`

[//]: # (`java -jar cryptoguard.jar -in apk -s ../../apks/air.pokemonquiz.apk -java /Users/Johnny/Library/Java/JavaVirtualMachines/corretto-1.8.0_332/Contents/Home`)

[//]: # (`java -jar src/cryptoguard/cryptoguard.jar -in apk -s apks/air.pokemonquiz.apk`)

[//]: # (`java -jar src/cryptoguard/cryptoguard.jar -in apk -s apks/com.alarmnet.tc2.apk`)

[//]: # (`java -jar cryptoguard.jar -in apk -s air.pokemonquiz/`)

## QARK

`https://github.com/linkedin/qark`

Requires Python 2 installation

Usage:

`python2 src/qark/qark-0.9-alpha.1/qark.py -p apks/air.pokemonquiz.apk --source=1 --exploit=0`

## FlowDroid

`https://github.com/secure-software-engineering/FlowDroid`

Requires Java 8 JDK installation 

Usage (Default):

`java -jar src/flowdroid/soot-infoflow-cmd-2.9.0-jar-with-dependencies.jar -a apks/air.pokemonquiz.apk -p src/flowdroid/android-sdk-macosx/platforms/ -s src/flowdroid/SourcesAndSinksOriginal.txt`

Usage (with Susi extenstion):

`java -jar src/flowdroid/soot-infoflow-cmd-2.9.0-jar-with-dependencies.jar -a apks/air.pokemonquiz.apk -p src/flowdroid/android-sdk-macosx/platforms/ -s src/flowdroid/SourcesAndSinksSusi.txt`

../flowdroid/android-sdk-macosx/platforms/




