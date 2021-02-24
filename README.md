# Java API for mite.

mite-java provides a Java API to the mite.api https://mite.yo.lk/api/

## Usage

### Maven Dependency

    <dependency>
        <groupId>io.seventytwo.oss</groupId>
        <artifactId>mite-java</artifactId>
        <version>1.0.1</version>
    </dependency>

### Examples

    var miteClient = new MiteClient("<your host>", "<your api key>");
    var customers = miteClient.getCustomers(null, 50, 1);

### Limitations

Starting and stopping of the tracker is not yet supported.

## License
mite-java is open and free software under Apache License, Version 2: http://www.apache.org/licenses/LICENSE-2.0.html
