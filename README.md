
[![GitHub Release](https://img.shields.io/github/release/codemonstur/critic.svg)](https://github.com/codemonstur/critic/releases) 
[![Build Status](https://travis-ci.org/codemonstur/critic.svg?branch=master)](https://travis-ci.org/codemonstur/critic)
[![Maintainability](https://api.codeclimate.com/v1/badges/cf3bf5cb7c1ae4f9f359/maintainability)](https://codeclimate.com/github/codemonstur/critic/maintainability)
[![contributions welcome](https://img.shields.io/badge/contributions-welcome-brightgreen.svg?style=flat)](https://github.com/dwyl/esta/issues)
[![HitCount](http://hits.dwyl.com/codemonstur/critic.svg)](http://hits.dwyl.com/codemonstur/critic)
[![Coverage Status](https://coveralls.io/repos/github/codemonstur/critic/badge.svg?branch=master)](https://coveralls.io/github/codemonstur/critic?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/0f23dda61a2f4ec4909827462b6aa47e)](https://www.codacy.com/manual/codemonstur/critic)
[![Sputnik](https://sputnik.ci/conf/badge)](https://sputnik.ci/app#/builds/codemonstur/critic)
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
