# Offset Well Analysis - XML Data Extraction CLI

## Requirements

- Java 11;
- Maven 3.x;

## Running app

First of all you need to package the app with the following command:

```console
~$ mvn clean package
```

In order to run the app you should run:

```console
~$ java -jar ./target/offset-well-analysis-jar-with-dependencies.jar
```

Once you do that you will get the usage documentation, that explains all configurations that you can do in order to run the app:

```
Missing required options: '--inputFile=INPUT_FILE', '--outputDirectory=OUTPUT_DIRECTORY'
Usage: <main class> [-hnp] [-e=ENCODING] -f=INPUT_FILE -o=OUTPUT_DIRECTORY
                    [-i=IGNORED_TAGS[,IGNORED_TAGS...]]...
  -e, --encoding=ENCODING   the encoding of the input file. The same will be
                              used for output
                              Default: UTF-8
  -f, --inputFile=INPUT_FILE
                            the input file name (XML format)
  -h, --help                display a help message
                              Default: false
  -i, --ignoredTags=IGNORED_TAGS[,IGNORED_TAGS...]
                            the tags that should be ignored
  -n, --ignoreNullValues    true if JSON output should ignore null values,
                              false otherwise
                              Default: true
  -o, --outputDirectory=OUTPUT_DIRECTORY
                            the output directory to store the result
  -p, --prettyPrint         true if JSON output should be formatted, false
                              otherwise
                              Default: true
```

The following table explains all options available (only `--inputFile` and `--outputDirectory` are required).

|  Short option name |  Long option name | Default value | Required? | Description                                                      |
| :----------------: | :---------------: | :-----------: | :-------: | ---------------------------------------------------------------- |
| -h                 | --help            |       -       | false     | display a help message                                           |
| -f                 | --inputFile       |       -       | true      | the input file name (XML format)                                 |
| -o                 | --outputDirectory |       -       | true      | the output directory to store the result                         |
| -e                 | --encoding        | UTF-8         | false     | the encoding of the input file. The same will be used for output |
| -i                 | --ignoredTags     |       -       | false     | the tags that should be ignored                                  |
| -n                 | --ignoreNullValues| true          | false     | true if JSON output should ignore null values, false otherwise   |
| -p                 | --prettyPrint     | true          | false     | true if JSON output should be formatted, false otherwise         |

There are validations done for some options:
* The input file must exist;
* The input file must not be a directory;
* The output directory must exist;
* The output directory must not be a regular file (it should be a directory);
* The given encoding must be valid and supported by JVM.

Example of usage with all options filled:

```console
~$ java -jar ./target/offset-well-analysis-jar-with-dependencies.jar -f test_files/input/scenario01.xml -o output -e UTF-8 -i xpto -n true -p true
```


## Notes

* The children of the tags ignored are still handled by the parsers and taken into account for output, once they might not be one of the ignored tags. That means we only don't write the JSON for the tags that should be ignored;
* The tags that are ignored do not have their respective output JSON file, but they might appear as child of other tags if it occurs (we can't ignore the child of tags that are NOT ignored).

## Documentation

In order to verify the code is properly documented, please run the following command:

```console
~$ mvn checkstyle:check
```

The error(s) shown is not valid. You can check it out by yourself to make sure.

## Verification Steps

### Valid scenarios

* Tags without any children and attributes (expected output: `test_files/expected_output/scenario01/`):

```console
~$ java -jar ./target/offset-well-analysis-jar-with-dependencies.jar -f test_files/input/scenario01.xml -o output
```

* Tags with no children but some attributes (expected output: `test_files/expected_output/scenario02/`):

```console
~$ java -jar ./target/offset-well-analysis-jar-with-dependencies.jar -f test_files/input/scenario02.xml -o output
```

* Tags with some children and some attributes (expected output: `test_files/expected_output/scenario03/`):

```console
~$ java -jar ./target/offset-well-analysis-jar-with-dependencies.jar -f test_files/input/scenario03.xml -o output
```

* Tags with some nested children (expected output: `test_files/expected_output/scenario04/`):

```console
~$ java -jar ./target/offset-well-analysis-jar-with-dependencies.jar -f test_files/input/scenario04.xml -o output
```

* Tags with some nested children with repetition (expected output: `test_files/expected_output/scenario05/`):

```console
~$ java -jar ./target/offset-well-analysis-jar-with-dependencies.jar -f test_files/input/scenario05.xml -o output
```
* Tags nested in itself should be handled properly (expected output: `test_files/expected_output/scenario06/`):

```console
~$ java -jar ./target/offset-well-analysis-jar-with-dependencies.jar -f test_files/input/scenario06.xml -o output
```

* Tag `A` and `B` ignored (expected output: `test_files/expected_output/scenario07/`):

```console
~$ java -jar ./target/offset-well-analysis-jar-with-dependencies.jar -f test_files/input/scenario07.xml -o output -i A,B
```

### Invalid scenarios 

#### Invalid XML:

```console
~$ java -jar ./target/offset-well-analysis-jar-with-dependencies.jar -f test_files/input_failure/invalidXml.xml -o output
```

Expected output:

```
Some error ocurred while parsing XML input file: ParseError at [row,col]:[5,5]
Message: The element type "B" must be terminated by the matching end-tag "</B>".
Usage: <main class> [-h] [-n[=<ignoreNullValues>]] [-p[=<prettyPrint>]]
                    [-e=ENCODING] -f=INPUT_FILE -o=OUTPUT_DIRECTORY
                    [-i=IGNORED_TAGS[,IGNORED_TAGS...]]...
  -e, --encoding=ENCODING   the encoding of the input file. The same will be
                              used for output
                              Default: UTF-8
  -f, --inputFile=INPUT_FILE
                            the input file name (XML format)
  -h, --help                display a help message
                              Default: false
  -i, --ignoredTags=IGNORED_TAGS[,IGNORED_TAGS...]
                            the tags that should be ignored
  -n, --ignoreNullValues[=<ignoreNullValues>]
                            true if JSON output should ignore null values,
                              false otherwise
                              Default: true
  -o, --outputDirectory=OUTPUT_DIRECTORY
                            the output directory to store the result
  -p, --prettyPrint[=<prettyPrint>]
                            true if JSON output should be formatted, false
                              otherwise
                              Default: true
```

#### Input file does not exist:

```console
~$ java -jar ./target/offset-well-analysis-jar-with-dependencies.jar -f test_files/input/scenario999.xml -o output
```

Expected output:

```
The input file (XML format) does not exist: test_files/input/scenario999.xml
Usage: <main class> [-h] [-n[=<ignoreNullValues>]] [-p[=<prettyPrint>]]
                    [-e=ENCODING] -f=INPUT_FILE -o=OUTPUT_DIRECTORY
                    [-i=IGNORED_TAGS[,IGNORED_TAGS...]]...
  -e, --encoding=ENCODING   the encoding of the input file. The same will be
                              used for output
                              Default: UTF-8
  -f, --inputFile=INPUT_FILE
                            the input file name (XML format)
  -h, --help                display a help message
                              Default: false
  -i, --ignoredTags=IGNORED_TAGS[,IGNORED_TAGS...]
                            the tags that should be ignored
  -n, --ignoreNullValues[=<ignoreNullValues>]
                            true if JSON output should ignore null values,
                              false otherwise
                              Default: true
  -o, --outputDirectory=OUTPUT_DIRECTORY
                            the output directory to store the result
  -p, --prettyPrint[=<prettyPrint>]
                            true if JSON output should be formatted, false
                              otherwise
                              Default: true
```

#### Input file is a directory:

```console
~$ java -jar ./target/offset-well-analysis-jar-with-dependencies.jar -f output -o output
```

Expected output:

```
The input file (XML format) should not be a directory: output
Usage: <main class> [-h] [-n[=<ignoreNullValues>]] [-p[=<prettyPrint>]]
                    [-e=ENCODING] -f=INPUT_FILE -o=OUTPUT_DIRECTORY
                    [-i=IGNORED_TAGS[,IGNORED_TAGS...]]...
  -e, --encoding=ENCODING   the encoding of the input file. The same will be
                              used for output
                              Default: UTF-8
  -f, --inputFile=INPUT_FILE
                            the input file name (XML format)
  -h, --help                display a help message
                              Default: false
  -i, --ignoredTags=IGNORED_TAGS[,IGNORED_TAGS...]
                            the tags that should be ignored
  -n, --ignoreNullValues[=<ignoreNullValues>]
                            true if JSON output should ignore null values,
                              false otherwise
                              Default: true
  -o, --outputDirectory=OUTPUT_DIRECTORY
                            the output directory to store the result
  -p, --prettyPrint[=<prettyPrint>]
                            true if JSON output should be formatted, false
                              otherwise
                              Default: true
```

#### Output directory does not exist:

```console
~$ java -jar ./target/offset-well-analysis-jar-with-dependencies.jar -f test_files/input/scenario01.xml -o oouuttppuutt
```

Expected output:

```
The output directory does not exist or is not a directory: oouuttppuutt
Usage: <main class> [-h] [-n[=<ignoreNullValues>]] [-p[=<prettyPrint>]]
                    [-e=ENCODING] -f=INPUT_FILE -o=OUTPUT_DIRECTORY
                    [-i=IGNORED_TAGS[,IGNORED_TAGS...]]...
  -e, --encoding=ENCODING   the encoding of the input file. The same will be
                              used for output
                              Default: UTF-8
  -f, --inputFile=INPUT_FILE
                            the input file name (XML format)
  -h, --help                display a help message
                              Default: false
  -i, --ignoredTags=IGNORED_TAGS[,IGNORED_TAGS...]
                            the tags that should be ignored
  -n, --ignoreNullValues[=<ignoreNullValues>]
                            true if JSON output should ignore null values,
                              false otherwise
                              Default: true
  -o, --outputDirectory=OUTPUT_DIRECTORY
                            the output directory to store the result
  -p, --prettyPrint[=<prettyPrint>]
                            true if JSON output should be formatted, false
                              otherwise
                              Default: true
```

#### Output directory is a regular file:

```console
~$ java -jar ./target/offset-well-analysis-jar-with-dependencies.jar -f test_files/input/scenario01.xml -o test_files/input/scenario01.xml
```

Expected output:

```
The output directory does not exist or is not a directory: test_files/input/scenario01.xml
Usage: <main class> [-h] [-n[=<ignoreNullValues>]] [-p[=<prettyPrint>]]
                    [-e=ENCODING] -f=INPUT_FILE -o=OUTPUT_DIRECTORY
                    [-i=IGNORED_TAGS[,IGNORED_TAGS...]]...
  -e, --encoding=ENCODING   the encoding of the input file. The same will be
                              used for output
                              Default: UTF-8
  -f, --inputFile=INPUT_FILE
                            the input file name (XML format)
  -h, --help                display a help message
                              Default: false
  -i, --ignoredTags=IGNORED_TAGS[,IGNORED_TAGS...]
                            the tags that should be ignored
  -n, --ignoreNullValues[=<ignoreNullValues>]
                            true if JSON output should ignore null values,
                              false otherwise
                              Default: true
  -o, --outputDirectory=OUTPUT_DIRECTORY
                            the output directory to store the result
  -p, --prettyPrint[=<prettyPrint>]
                            true if JSON output should be formatted, false
                              otherwise
                              Default: true
```

#### Invalid encoding:

```console
~$ java -jar ./target/offset-well-analysis-jar-with-dependencies.jar -f test_files/input/scenario01.xml -o output -e UTF-88
```

Expected output:

```
The encoding is not valid or not supported: UTF-88
Usage: <main class> [-h] [-n[=<ignoreNullValues>]] [-p[=<prettyPrint>]]
                    [-e=ENCODING] -f=INPUT_FILE -o=OUTPUT_DIRECTORY
                    [-i=IGNORED_TAGS[,IGNORED_TAGS...]]...
  -e, --encoding=ENCODING   the encoding of the input file. The same will be
                              used for output
                              Default: UTF-8
  -f, --inputFile=INPUT_FILE
                            the input file name (XML format)
  -h, --help                display a help message
                              Default: false
  -i, --ignoredTags=IGNORED_TAGS[,IGNORED_TAGS...]
                            the tags that should be ignored
  -n, --ignoreNullValues[=<ignoreNullValues>]
                            true if JSON output should ignore null values,
                              false otherwise
                              Default: true
  -o, --outputDirectory=OUTPUT_DIRECTORY
                            the output directory to store the result
  -p, --prettyPrint[=<prettyPrint>]
                            true if JSON output should be formatted, false
                              otherwise
                              Default: true
```
