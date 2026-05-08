# Folder Inspector

A JavaFX desktop application that calculates the sizes of folders inside a given directory and sorts them in descending order. Navigate into subdirectories to drill down further, then jump back when you're done.

## Features

- Asynchronous, parallel size calculation for large trees
- Sorted view of children by total size
- Drill-down navigation with a back button
- File / directory / symbolic-link distinction with icons
- Address bar showing the current path

## Requirements

- JDK 21 (Temurin recommended)
- Maven 3.9+

## Run

```sh
mvn clean javafx:run
```

This launches the GUI. Enter an absolute path in the input field and click **Inspect**.

## Build

Run tests, checkstyle, and produce a runnable shaded jar:

```sh
mvn clean verify
```

The shaded jar is written to `target/folder-inspector-<version>.jar` and can be launched with:

```sh
java -jar target/folder-inspector-1.0.0-SNAPSHOT.jar
```

## Project layout

```
src/main/java/osmosis/folder/inspector/
  Main.java, FolderInspector.java        # entry points
  calculation/                           # size-calculation tasks
  container/                             # file / directory / symlink model
  controllers/                           # JavaFX controllers
  panes/                                 # custom JavaFX panes
  constants/                             # constants and resource paths
  dimensions/, formatter/, exceptions/   # supporting classes
src/main/resources/osmosis/
  folder/inspector/*.fxml                # views
  icons/*.png                            # toolbar icons
src/test/java/                           # JUnit Jupiter tests
```

## Contributing

Open issues and pull requests are tracked on GitHub. The CI workflow at `.github/workflows/verify.yaml` runs `mvn clean verify` on every PR — make sure that passes locally first.
