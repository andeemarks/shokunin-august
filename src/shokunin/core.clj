(ns shokunin.core (:gen-class)
  (:require [clojure.math.combinatorics :as combo]))

(defn permute
  [devs]
  (combo/permutations devs))

(defn find
  [devs dev position]
  (filter 
    (fn [solution]
      (if (or (<= position 0) (> position (count solution)))
        false
        (= dev (nth solution (+ position 1))))) 
    devs))
