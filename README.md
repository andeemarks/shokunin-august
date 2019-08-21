# August Challenge: Find the 10x Developer
A solution to ThoughtWorks Australia shokunin coding challenge

## Background
Jessie, Evan, John, Sarah and Matt are all engineers in the same delivery team (note: any resemblance to actual TWers, living or dead, is purely coincidental)... and each of them has a different level of coding skill to the other.  This means it possible to order them from best to... "least best".  Importantly, the best of them is the mythical 10x Developer!!!

## Here's what we know
- Jessie is not the best developer
- Evan is not the worst developer
- John is not the best developer or the worst developer
- Sarah is a better developer than Evan
- Matt is not directly below or above John as a developer
- John is not directly below or above Evan as a developer

## Challenge
You need to write a solution to compute these answers:
1. Who is the 10x developer on the team?
1. What is the correct ordering of the members of the team according to their coding skills?

## Usage

### Option 1 - Local

#### Prerequisites

* [Clojure](https://clojure.org/guides/getting_started) - developed using v1.10.1
* [Leiningen](https://leiningen.org/#install) - developed using v2.8.1

#### Usage

- Run the application: `./go`
- Run the tests: `lein test`

### Option 2 - Docker

#### Prerequisites

* [Docker](http://docker.io)

#### Usage

- Run the application: `./go-docker`
- Run the tests: `docker run -it shokunin-august lein test`
