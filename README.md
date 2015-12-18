# DECOmap configuration GUI

This is a GUI application for easy configuration of our [DECOmap IF-MAP client](https://github.com/decoit/decomap). It allows importing existing configuration files from a local installation or via SSH from a remote server. The changed files can as well be exported locally or via SSH.

## Preparation ##

The following requirements must be met to compile and use this library:

* Java 7 or higher
* Maven 3

To compile this project the Oracle JDK is preferred but it may work as well on other JDK implementations. Any Java 8 compatible JRE (Oracle, OpenJDK, Apple) should be able to run the application.

## Installation ##

Follow these steps to compile the project:

* Open a command prompt and change directory to the root of this project
* Execute `mvn package`
* Unpack the contents of `target/decomap-config-gui-0.2.0.0.dist.zip` or `target/decomap-config-gui-0.2.0.0.dist.tar.gz` to your chosen installation directory

## Run the application ##

To run the application simple execute `java -jar decomap-config-gui-0.2.0.0.jar` inside the installation directory.

## License
The source code and all other contents of this repository are copyright by DECOIT GmbH and licensed under the terms of the [Apache License Version 2.0](http://www.apache.org/licenses/). A copy of the license may be found inside the LICENSE file.
