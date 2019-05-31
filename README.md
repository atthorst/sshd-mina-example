# SSH Java Example

Very simple project that shows a SSH client example with [Apache Mina SSHD](https://mina.apache.org/sshd-project/) as the backing engine.

Note that while Apache Mina can provide a whole SSH server, this is a simple client side example project.

## Getting Started

Open the [SshUtils.java](src/main/java/thorst/ssh_example/SshUtils.java) file to look at the code example.

### Prerequisites

This was build in eclipse with maven.  Note the sshd dependency in the pom.xml.  Update to have the right version for your project.

## Built With

* [Apahce Mina SSHD](https://mina.apache.org/sshd-project/) - The SSH java backend
* [Maven](https://maven.apache.org/) - Dependency Management
* [Eclipse](https://eclipse.org) - Editor

## Authors

* **Drew Thorstensen** - *Initial work* 

## License

This project is licensed under the Apache 2 license - see the [LICENSE](LICENSE) file for details