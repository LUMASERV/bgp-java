# bgp-java
BGP 4 Connector that can be used for network analysis.

## Usage
```java
BGPServer server = new BGPServer();
server.getSessionConfigurations().add(new BGPSessionConfiguration(
        "demosession",
        1234,
        ip("1.2.3.4"),
        4321,
        ip("4.3.2.1"),
        new BGPListener() {
            public void onOpen(BGPSession session) {
                // DO WHAT YOU WANT
            }
            public void onUpdate(BGPSession session, BGPUpdate update) {
                // DO WHAT YOU WANT
            }
            public void onClose(BGPSession session) {
                // NOT YET IMPLEMENTED
            }
        }
));
server.run();
```

## Maven
```xml
<repository>
    <id>lumaserv</id>
    <url>https://maven.lumaserv.cloud</url>
</repository>
```
```xml
<dependency>
    <groupId>com.lumaserv</groupId>
    <artifactId>bgp-java</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```