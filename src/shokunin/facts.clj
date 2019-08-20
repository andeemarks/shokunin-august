(ns shokunin.facts
  (:require [clojure.math.combinatorics :as combo]))

(defn permute
  [devs]
  (combo/permutations devs))

(def all-facts
  (permute ["Evan" "Jessie" "John" "Matt" "Sarah"]))
