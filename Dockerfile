FROM clojure:alpine
COPY . /usr/src/shokunin-august
WORKDIR /usr/src/shokunin-august
CMD ["lein", "run"]