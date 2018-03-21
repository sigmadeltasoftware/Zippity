# Zippity
[![Download](https://api.bintray.com/packages/sigmadeltasoftware/Zippity/Zippity/images/download.svg) ](https://bintray.com/sigmadeltasoftware/Zippity/Zippity/_latestVersion)

Ultra-lightweight Kotlin-based Android library to zip or unzip. __Nothing less, nothing more__.


### Installation
Simply add the project to your build.gradle. Replace 'x.x.x' by latest version which can be found at the top of the document.

```
implementation 'be.sigmadelta:zippitylib:x.x.x'
```

### Usage
The API is all about keeping it stupid simple:

```
try {
    // Unzipping
    val archiveFile = File("/path/to/zip/file.zip")
    Zippity.unzip(archiveFile, File("/path/to/unzip/location"))
    
    // Zip a list of files
    val fileList = listOf(file0, file1, file2) // f.e. val file0 = File("/path/to/file0")
    Zippity.zip(fileList, File("/path/to/output.zip"))
    
    // Zip a directory
    val dirToZip = File("/path/to/directory/to/be/zipped")
    Zippity.zip(dirToZip, File("/path/to/other_output.zip"))
    } catch:
    // IOException, FileNotFoundException, IllegalArgumentException
```

### Contributing
Contributions are very welcome and can be made using pull requests.

### License
This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details