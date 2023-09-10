# LibKeyHook

A minimal, Java based, global key press listener.

## Maven Dependency

Include the library in your project by adding the following dependency to your pom.xml

```
<dependency>
	<groupId>com.mclarkdev.tools</groupId>
	<artifactId>libkeyhook</artifactId>
	<version>1.5.1</version>
</dependency>
```

## Example

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

# License

Open source & free for all. ❤
