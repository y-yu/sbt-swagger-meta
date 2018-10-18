sbt-swagger-meta
============================

[![Build Status](https://travis-ci.org/y-yu/sbt-swagger-meta.svg?branch=master)](https://travis-ci.org/y-yu/sbt-swagger-meta)
[ ![Download](https://api.bintray.com/packages/yyu/sbt-plugins/sbt-swagger-meta/images/download.svg) ](https://bintray.com/yyu/sbt-plugins/sbt-swagger-meta/_latestVersion)

This is a sbt plugin to generate a [Swagger](https://swagger.io/) YAML/JSON file based on @annotation.

## How to Use

1. `./project/plugins.sbt`

    ```
    resolvers += Resolver.bintrayRepo("yyu", "sbt-plugins")
    
    addSbtPlugin("com.github.y-yu" % "sbt-swagger-meta" % "0.1.4")
    ```
2. `build.sbt`

    ```
    enablePlugins(SbtSwaggerMeta)
    ```

See also `./example` and `./src/sbt-test` directories.

## License

MIT
