requires to add this to gradle

```gradle
task reloadServer {
    // https://github.com/Paradis4432/ParAutoRestart
    doLast {
        def url = new URL("http://localhost:10012/reload")
        def connection = url.openConnection()
        connection.requestMethod = 'GET'
        connection.connect()

        def responseCode = connection.responseCode
        if (responseCode == 200) {
            println "Server reloaded successfully."
        } else {
            println "Failed to reload server. Response code: $responseCode"
        }
    }
}

if (System.getenv('CI') == null) {
    build.dependsOn 'reloadServer'
}
```
make sure port matches and this plugin is built in the plugins folder

server must run in a loop like such:
```
@echo off
:loop
java -server -Xms3G -Xmx3G -jar file.jar nogui
timeout /t 3
goto loop
```

or for linux:
```shell
while true
do
  java -server -Xms3G -Xmx3G -jar file.jar nogui 
done
Pause
```