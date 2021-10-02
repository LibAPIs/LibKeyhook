# KeyHook

A Java/Native global keyboard hook.

## Usage

Using the library is as simple as adding a Maven dependency and a few lines to handle the events.

### Maven

```
<dependency>
    <groupId>com.mclarkdev.tools</groupId>
    <artifactId>libkeyhook</artifactId>
    <version>1.0</version>
</dependency>
```

### Java

Create an EventListener to handle the keyboard events.

```
private EventListener eventListener = new EventListener() {

    @Override
    public void onEvent(ArrayList<Integer> keyMap) {

        String keys = keyMap.stream().map(Object::toString)//
                .collect(Collectors.joining(", "));

        System.out.println("Keys:" + keys);
    }
};
```

Attach the listener to the LibKeyhookManager.

```
LibKeyhookManager.getInstance().attachListener(eventListener);
```

