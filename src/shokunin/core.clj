(ns shokunin.core (:gen-class)
  (:require [clojure.math.combinatorics :as combo]))

(defn permute
  [devs]
  (combo/permutations devs))

(defn- valid-position?
  [position solution]
  (and 
    (> position 0) 
    (<= position (count solution))))

(defn- dev-at-position
  [solution position]
  (nth solution (+ position 1)))

(defn find
  [devs dev position]
  (filter 
    #(if (valid-position? position %)
      (= dev (dev-at-position % position))
      false)
    devs))
