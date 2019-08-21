FROM clojure:openjdk-8-lein-2.9.1-alpine
COPY . /usr/src/shokunin-august
WORKDIR /usr/src/shokunin-august
CMD ["lein", "run"]