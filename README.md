swt-jlink-demo
==============
Demo of a self-contained [SWT](https://www.eclipse.org/swt/) app, built using
[jlink](https://docs.oracle.com/en/java/javase/11/tools/jlink.html) and
[ModiTect](https://github.com/moditect/moditect-gradle-plugin).

## Building
You'll need JDK 11 or higher installed. Use the following command to build:
```shell script
./gradlew imageZip
```

The generated `demo.zip` should be found inside your `build` folder. It weighs
around **30mb** and comes with a minimized Java runtime, making it completely
standalone. Extract this zip, and execute the `demo` inside its `bin` folder to
see it running.