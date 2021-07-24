
[![GitHub Release](https://img.shields.io/github/release/codemonstur/critic.svg)](https://github.com/codemonstur/critic/releases) 
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.codemonstur/bobjooqcodegen/badge.svg)](http://mvnrepository.com/artifact/com.github.codemonstur/benthemanager)
[![MIT Licence](https://badges.frapsoft.com/os/mit/mit.svg?v=103)](https://opensource.org/licenses/mit-license.php)

## A tool for critiquing a text

A command line tool for analyzing large pieces of text.
Go into a directory and run the critic.
It will scan all files inside the `src` subdir and write analyses inside `target`.

The idea is to make writing large documents easier.

### Status

Just started

### Current features

none

### Future features

There are some listed in `src/main/docs/ideas.md`.
More ideas are welcome.
As are bugs, comments and anything else really.

### Installation

1. Check out the code `git clone https://github.com/codemonstur/critic.git`
2. Run `make install`
3. You can now run the code with `java -jar target/critic.jar`
4. \[Optional] Copy the code to `/usr/local/bin`
5. \[Optional] Create an alias `alias critic='java -jar /usr/local/bin/critic.jar'`

The code requires Java 11.
